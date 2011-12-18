package com.siegedog.hava.engine;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.siegedog.hava.derpygame.GameplayScreen;
import com.siegedog.hava.derpygame.LRR;

/*
 * Loads and handles a basic tilemap.
 * Overloads handle loading tilemaps from different formats (Ogmo, Wasabi etc.)
 */
public class TileMap {
	TileMapRenderer tileMapRenderer;
	TiledMap map;
	TileAtlas atlas;
	FileHandle packFileDirectory;
	

	int[] layersList = new int[] { 0, 1, 2 };
	int collisionLayer = 0;

	float pixelsPerMeter;

	public int getWidth() {
		return map.width;
	}

	public int getHeight() {
		return map.height;
	}

	
	// GameplayScreen ref needed for player init and object generation etc.
	public TileMap(String assetName, float pixelsPerMeter, GameplayScreen scr) {
		this.pixelsPerMeter = pixelsPerMeter;
		this.load(assetName, scr);
	}

	protected void load(String assetName, GameplayScreen scr) {
		int i;
		long startTime, endTime;

		// the root is mapped to the ASSETS folder on ANDROID !!!
		// TODO: look up how to map project data to assets folder
		FileHandle kek = Gdx.files.internal("data/tiledmap/");
		final String path = "data/tiledmap/";

		FileHandle yay = kek.child("level.tmx");
		if (yay == null) {
			System.out.println("LEVEL 404");
		}

		FileHandle mapHandle = Gdx.files.internal(path + assetName + ".tmx");
		FileHandle baseDir = Gdx.files.internal(path);

		startTime = System.currentTimeMillis();
		map = TiledLoader.createMap(mapHandle);
		endTime = System.currentTimeMillis();
		System.out.println("Loaded map in " + (endTime - startTime) + "mS");

		atlas = new TileAtlas(map, baseDir);

		int blockWidth = 16;
		int blockHeight = 16;

		startTime = System.currentTimeMillis();
		tileMapRenderer = new TileMapRenderer(map, atlas, 16, 16);
		endTime = System.currentTimeMillis();
		System.out.println("Created cache in " + (endTime - startTime) + "mS");

		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				
				System.out.println("Object " + object.name + " x,y = "
						+ object.x + "," + object.y + " width,height = "
						+ object.width + "," + object.height);
				
				if(object.name.equals("playerSpawn")) {
					LRR lrr = new LRR(object.x, map.height * blockHeight - object.y , scr.getPlayerInputNode());
					scr.initPlayer(lrr);					
				}
				
			}
		}

		float aspectRatio = (float) Gdx.graphics.getWidth()
				/ (float) Gdx.graphics.getHeight();
	}
	
	public void render(OrthographicCamera cam) {
		Matrix4 m = new Matrix4(cam.combined);
		// m.scale(pixelsPerMeter, pixelsPerMeter, pixelsPerMeter);
		tileMapRenderer.getProjectionMatrix().set(m);

		Vector3 tmp = new Vector3();
		tmp.set(0, 0, 0);
		cam.unproject(tmp);

		tileMapRenderer.render((int) tmp.x, (int) tmp.y,
				Gdx.graphics.getWidth() * cam.zoom, Gdx.graphics.getHeight()
						* cam.zoom, layersList);
	}

	/**
	 * Reads a file describing the collision boundaries that should be set
	 * per-tile and adds static bodies to the boxd world.
	 * 
	 * @param collisionsFile
	 * @param world
	 */
	public void loadCollisions(String collisionsFile, World world) {
		/**
		 * Detect the tiles and dynamically create a representation of the map
		 * layout, for collision detection. Each tile has its own collision
		 * rules stored in an associated file.
		 * 
		 * The file contains lines in this format (one line per type of tile):
		 * tileNumber XxY,XxY XxY,XxY
		 * 
		 * Ex:
		 * 
		 * 3 0x0,31x0 ... 4 0x0,29x0 29x0,29x31
		 * 
		 * For a 32x32 tileset, the above describes one line segment for tile #3
		 * and two for tile #4. Tile #3 has a line segment across the top. Tile
		 * #1 has a line segment across most of the top and a line segment from
		 * the top to the bottom, 30 pixels in.
		 */

		FileHandle fh = Gdx.files.internal(collisionsFile);
		String collisionFile = fh.readString();
		String lines[] = collisionFile.split("\\r?\\n");

		HashMap<Integer, ArrayList<LineSegment>> tileCollisionJoints = new HashMap<Integer, ArrayList<LineSegment>>();

		/**
		 * Some locations on the map (perhaps most locations) are "undefined",
		 * empty space, and will have the tile type 0. This code adds an empty
		 * list of line segments for this "default" tile.
		 */
		tileCollisionJoints.put(Integer.valueOf(0),
				new ArrayList<LineSegment>());

		for (int n = 0; n < lines.length; n++) {
			String cols[] = lines[n].split(" ");
			// if(cols[0].length() < 2) continue;
			
			int tileNo = Integer.parseInt(cols[0]);

			ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();

			for (int m = 1; m < cols.length; m++) {
				String coords[] = cols[m].split(",");

				String start[] = coords[0].split("x");
				String end[] = coords[1].split("x");

				tmp.add(new LineSegment(Integer.parseInt(start[0]), Integer
						.parseInt(start[1]), Integer.parseInt(end[0]), Integer
						.parseInt(end[1])));
			}

			tileCollisionJoints.put(Integer.valueOf(tileNo), tmp);
		}

		ArrayList<LineSegment> collisionLineSegments = new ArrayList<LineSegment>();

		for (int y = 0; y < map.height; y++) {
			for (int x = 0; x < map.width; x++) {
				int tileType = map.layers.get(collisionLayer).tiles[map.height - 1 - y][x];

//				if(tileType!=0) System.out.println(tileType);
//				 if(4==0x4) continue;
				
				for (int n = 0; n < tileCollisionJoints.get(
						Integer.valueOf(tileType)).size(); n++) {

					LineSegment lineSeg = tileCollisionJoints.get(
							Integer.valueOf(tileType)).get(n);

					addOrExtendCollisionLineSegment(
							x * map.tileWidth + lineSeg.start().x, y
									* map.tileHeight - lineSeg.start().y
									+ map.tileHeight, x * map.tileWidth
									+ lineSeg.end().x, y * map.tileHeight
									- lineSeg.end().y + map.tileHeight,
							collisionLineSegments);
				}
			}
		}

		// pixelsPerMeter = 1;
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		Body groundBody = world.createBody(groundBodyDef);
		for (LineSegment lineSegment : collisionLineSegments) {
			EdgeShape environmentShape = new EdgeShape();

			environmentShape.set(lineSegment.start().mul(1 / pixelsPerMeter),
					lineSegment.end().mul(1 / pixelsPerMeter));

			Fixture f = groundBody.createFixture(environmentShape, 0);
			// f.setFriction(1f);
			environmentShape.dispose();
		}

		/**
		 * Drawing a boundary around the entire map. We can't use a box because
		 * then the world objects would be inside and the physics engine would
		 * try to push them out.
		 */

		/*
		 * EdgeShape mapBounds = new EdgeShape(); mapBounds.set(new
		 * Vector2(0.0f, 0.0f), new Vector2(getWidth() / pixelsPerMeter, 0.0f));
		 * groundBody.createFixture(mapBounds, 0);
		 * 
		 * mapBounds.set(new Vector2(0.0f, getHeight() / pixelsPerMeter), new
		 * Vector2(getWidth() / pixelsPerMeter, getHeight() / pixelsPerMeter));
		 * groundBody.createFixture(mapBounds, 0);
		 * 
		 * mapBounds.set(new Vector2(0.0f, 0.0f), new Vector2(0.0f, getHeight()
		 * / pixelsPerMeter)); groundBody.createFixture(mapBounds, 0);
		 * 
		 * mapBounds.set(new Vector2(getWidth() / pixelsPerMeter, 0.0f), new
		 * Vector2(getWidth() / pixelsPerMeter, getHeight() / pixelsPerMeter));
		 * groundBody.createFixture(mapBounds, 0);
		 * 
		 * mapBounds.dispose();
		 */
	}

	/**
	 * This is a helper function that makes calls that will attempt to extend
	 * one of the line segments already tracked by TiledMapHelper, if possible.
	 * The goal is to have as few line segments as possible.
	 * 
	 * Ex: If you have a line segment in the system that is from 1x1 to 3x3 and
	 * this function is called for a line that is 4x4 to 9x9, rather than add a
	 * whole new line segment to the list, the 1x1,3x3 line will be extended to
	 * 1x1,9x9. See also: LineSegment.extendIfPossible.
	 * 
	 * @param lsx1
	 *            starting x of the new line segment
	 * @param lsy1
	 *            starting y of the new line segment
	 * @param lsx2
	 *            ending x of the new line segment
	 * @param lsy2
	 *            ending y of the new line segment
	 * @param collisionLineSegments
	 *            the current list of line segments
	 */
	private void addOrExtendCollisionLineSegment(float lsx1, float lsy1,
			float lsx2, float lsy2, ArrayList<LineSegment> collisionLineSegments) {
		LineSegment line = new LineSegment(lsx1, lsy1, lsx2, lsy2);

		boolean didextend = false;

		for (LineSegment test : collisionLineSegments) {
			if (test.extendIfPossible(line)) {
				didextend = true;
				break;
			}
		}

		if (!didextend) {
			collisionLineSegments.add(line);
		}
	}

	/**
	 * Describes the start and end points of a line segment and contains a
	 * helper method useful for extending line segments.
	 */
	private class LineSegment {
		private Vector2 start = new Vector2();
		private Vector2 end = new Vector2();

		/**
		 * Construct a new LineSegment with the specified coordinates.
		 * 
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 */
		public LineSegment(float x1, float y1, float x2, float y2) {
			start = new Vector2(x1, y1);
			end = new Vector2(x2, y2);
		}

		/**
		 * The "start" of the line. Start and end are misnomers, this is just
		 * one end of the line.
		 * 
		 * @return Vector2
		 */
		public Vector2 start() {
			return start;
		}

		/**
		 * The "end" of the line. Start and end are misnomers, this is just one
		 * end of the line.
		 * 
		 * @return Vector2
		 */
		public Vector2 end() {
			return end;
		}

		/**
		 * Determine if the requested line could be tacked on to the end of this
		 * line with no kinks or gaps. If it can, the current LineSegment will
		 * be extended by the length of the passed LineSegment.
		 * 
		 * @param lineSegment
		 * @return boolean true if line was extended, false if not.
		 */
		public boolean extendIfPossible(LineSegment lineSegment) {
			/**
			 * First, let's see if the slopes of the two segments are the same.
			 */
			double slope1 = Math.atan2(end.y - start.y, end.x - start.x);
			double slope2 = Math.atan2(lineSegment.end.y - lineSegment.start.y,
					lineSegment.end.x - lineSegment.start.x);

			if (Math.abs(slope1 - slope2) > 1e-9) {
				return false;
			}

			/**
			 * Second, check if either end of this line segment is adjacent to
			 * the requested line segment. So, 1 pixel away up through sqrt(2)
			 * away.
			 * 
			 * Whichever two points are within the right range will be "merged"
			 * so that the two outer points will describe the line segment.
			 */
			if (start.dst(lineSegment.start) <= Math.sqrt(2) + 1e-9) {
				start.set(lineSegment.end);
				return true;
			} else if (end.dst(lineSegment.start) <= Math.sqrt(2) + 1e-9) {
				end.set(lineSegment.end);
				return true;
			} else if (end.dst(lineSegment.end) <= Math.sqrt(2) + 1e-9) {
				end.set(lineSegment.start);
				return true;
			} else if (start.dst(lineSegment.end) <= Math.sqrt(2) + 1e-9) {
				start.set(lineSegment.start);
				return true;
			}

			return false;
		}

		/**
		 * Returns a pretty description of the LineSegment.
		 * 
		 * @return String
		 */
		@Override
		public String toString() {
			return "[" + start.x + "x" + start.y + "] -> [" + end.x + "x"
					+ end.y + "]";
		}
	}

}
