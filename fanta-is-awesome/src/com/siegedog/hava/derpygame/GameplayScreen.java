package com.siegedog.hava.derpygame;

import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.siegedog.hava.engine.FancyGame;
import com.siegedog.hava.engine.GameCam2D;
import com.siegedog.hava.engine.GameInput;
import com.siegedog.hava.engine.InputNode;
import com.siegedog.hava.engine.KeyboardInputNode;
import com.siegedog.hava.engine.Node;
import com.siegedog.hava.engine.RenderNode2D;
import com.siegedog.hava.engine.Resources;
import com.siegedog.hava.engine.SaveGameHelper;
import com.siegedog.hava.engine.TileMap;
import com.siegedog.hava.engine.Unit;

public class GameplayScreen implements Screen {

	public final int PIXELS_PER_METER = 32;

	private static final int[] layersList = { 2, 3 };

	FancyGame game;
	SpriteBatch spriteBatch;
	BitmapFont font;

	Stage stage;
	Sprite TS;

	GameCam2D cam;
	OrthographicCamera UICam;

	GameInput gameInput;

	Node root = new Node("root");
	Unit dude1;

	long startTime = System.nanoTime();
	Vector3 tmp = new Vector3();

	TileMap map;
	float gravityStrength = 80f;
	
	

	World world;
	Box2DDebugRenderer boxDebugRenderer;
	
	public void initPlayer(Unit player) {
		dude1 = player;
		cam.follow(player.getPhysics(), false);
		root.addNode(player);
	}

	public GameplayScreen(FancyGame game) {
		this.game = game;

		GameplayScreen.instance = this;

		font = new BitmapFont();
		font.setColor(new Color(0.5f, 0.5f, 0.8f, 0.94f));
		cam = new GameCam2D(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		UICam = new OrthographicCamera();

		spriteBatch = new SpriteBatch();

		// World setup
		world = new World(new Vector2(0, -gravityStrength), true);

		map = new TileMap("level", PIXELS_PER_METER, this);
		map.loadCollisions("data/tiledmap/collisions.txt", world);
		boxDebugRenderer = new Box2DDebugRenderer(true, true, true);
		root.addNode(new DoNothingPickup(15,40,3,3));

		// So far, just a thingy to let us drag the world around
		//gameInput = new GameInput(game, this);
		//Gdx.input.setInputProcessor(gameInput);

		/*
		RenderNode2D aniTest = new RenderNode2D("data/img/sprites/penis.png",
				32, 32);
	
		aniTest.getSprite().setPosition(15 * PIXELS_PER_METER,
				40 * PIXELS_PER_METER);
		root.addNode(aniTest);*/
		 
		
		// BasicSaveData save = new
		// SaveGameHelper<BasicSaveData>().readSaveData("save.sav");
		// System.out.println("Loaded game...");
		// System.out.println(save.name);

		Resources.loadSfx("data/sound/sfx/");

	}

	@Override
	public void render(float delta) { // called at 60fps
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.3f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// Async loading
		Resources.update(delta);

		// Do the physics
		world.step(delta, 8, 3);

		// And then the logic
		root.update(delta);
		// stage.act(delta);

		// Cam should always be updated.
		// It's not expensive and prevents a bunch of bugs.
		cam.update(delta);

		// And render everything (if we can)
		renderGame(delta);
	}

	private void renderGame(float delta) {
		// Start rendering shit. map first.
		map.render(cam.getGameCam());

		spriteBatch.setProjectionMatrix(cam.getGameCam().combined);
		spriteBatch.begin();

		// TS.draw(spriteBatch);
		// stage.draw();
		Node.renderAllNodes(spriteBatch);
		spriteBatch.end();

		if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
			Matrix4 m = new Matrix4(cam.getGameCam().combined);
			m.scale(PIXELS_PER_METER, PIXELS_PER_METER, PIXELS_PER_METER);
			boxDebugRenderer.render(world, m);
		}

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		spriteBatch.setProjectionMatrix(cam.getUICam().combined);
		spriteBatch.begin();
		font.draw(
				spriteBatch,
				"FPS: " + Gdx.graphics.getFramesPerSecond() + " World: "
						+ world.getBodyCount() + " bodies." + "\n"
						+ dude1.getBoxDebug(), cam.getUICam().position.x - w
						/ 2 + 40, cam.getUICam().position.y - h / 2 + 40);

		tmp.set(0, 0, 0);
		cam.getGameCam().unproject(tmp);
		font.draw(spriteBatch, "DUDE location: " + dude1.getPhysics().getBody().getPosition(), 20, 80);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		cam.resize(width, height);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		instance = null;
	}

	public GameCam2D getCam() {
		return cam;
	}

	public World getWorld() {
		return world;
	}
	
	public InputNode getPlayerInputNode() {
		// pff hack lol
		return new KeyboardInputNode();
	}
	
	public Node getRoot() {
		return root;
	}

	private static GameplayScreen instance;

	public static GameplayScreen get() {
		return instance;
	}
}