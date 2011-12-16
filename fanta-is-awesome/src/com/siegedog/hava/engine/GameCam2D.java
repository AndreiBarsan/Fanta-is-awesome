package com.siegedog.hava.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Interpolator;

public class GameCam2D {

	Vector3 current = new Vector3();
	Vector3 last = new Vector3(-1, -1, -1);
	Vector3 delta = new Vector3();

	int width, height;

	final float ppm = 32.0f;

	PlatformerEntityNode following;

	float minZoom = 0.4f;
	float maxZoom = 40f;
	float targetZoom = 1f;
	float zoomStep = 0.01f;

	OrthographicCamera gameCam;
	OrthographicCamera UICam;

	Vector3 targetPosition;
	float panStep = 25f;

	long lastTap = System.currentTimeMillis();
	Vector2 lastTapPos;
	float maxPosDelta = 10f;

	Vector3 diff;

	Interpolator interpolator;

	public GameCam2D(int width, int height) {
		gameCam = new OrthographicCamera(width, height);
		UICam = new OrthographicCamera(width, height);
		UICam.zoom = 1f;

		this.width = width;
		this.height = height;
		lastTapPos = new Vector2(-1, -1);
		targetPosition = new Vector3(width / 2, height / 2, 0);

		diff = new Vector3();
	}

	public void drag(int x, int y) {
		gameCam.unproject(current.set(x, y, 0));

		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			// If we're in the middle of a drag (employ delta)

			gameCam.unproject(delta.set(last.x, last.y, 0)); // Convert delta
																// into world
			// space
			delta.sub(current); // And actually do the delta computation (in
								// world space???)
			gameCam.position.add(delta); // Oh we need world space for the cam
											// movement
			targetPosition.set(gameCam.position);
		}

		last.set(x, y, 0);
	}

	public void tap(int x, int y) {
		last.set(-1, -1, -1);
		Vector2 tapPos = new Vector2(x, y);

		long currentTap = System.currentTimeMillis();
		if (currentTap - lastTap < 300
				&& (tapPos.sub(lastTapPos).len() < maxPosDelta || (lastTapPos.x == -1f && lastTapPos.y == -1f))) {
			if (gameCam.zoom < 2f) {
				targetZoom = maxZoom * 0.75f;
				gameCam.unproject(targetPosition.set(width / 2, height / 2, 0));
			} else {
				// TODO: cubic interpolation
				// TODO: estimate which interpolation will take longer
				// and ellongate the other to match

				targetZoom = minZoom * 1.75f;
				gameCam.unproject(targetPosition.set(x, y, 0));
			}

		}
		lastTap = currentTap;
		lastTapPos = tapPos;
	}

	public void zoom(int amount) {
		gameCam.zoom += amount / 7f;
		gameCam.zoom = MU.clamp(gameCam.zoom, minZoom, maxZoom);

		targetZoom = gameCam.zoom;
	}

	public Matrix4 getUIMatrix() {
		return gameCam.combined;
	}

	public Vector2 get2DCoords() {
		return new Vector2(gameCam.position.x - width / 2 * gameCam.zoom, gameCam.position.y - height
				/ 2 * gameCam.zoom);
	}

	// TODO: read up on <Observer> pattern
	public void follow(PlatformerEntityNode target, boolean jump) {
		following = target;
		if (jump) {
			Vector2 pos = following.getBody().getPosition();
			gameCam.position.set(targetPosition.set(pos.x * ppm, pos.y * ppm, 0));
		}
	}
	
	public OrthographicCamera getGameCam(){
		return gameCam;
	}
	
	// TODO: implement
	public OrthographicCamera getUICam() {
		UICam.position.set(gameCam.position);
		UICam.update();
		return UICam;
	}
	
	public void resize(float w, float h) {
		Vector3 oldPos = gameCam.position;
		gameCam.setToOrtho(false, w, h);
		UICam.setToOrtho(false, w, h);
		gameCam.position.set(oldPos);
	}

	/*
	 * public void follow(Node node) throws Exception { Object res =
	 * node.qc(BoxNode.class); if(res != null) this.following = (BoxNode)res;
	 * else throw new Exception("Node " + node.name +
	 * " doesn't have a followable component."); }
	 */

	public void update(float delta) {

		// Update zoom
		if (Math.abs(gameCam.zoom - targetZoom) < zoomStep)
			gameCam.zoom = targetZoom;
		else {
			if (gameCam.zoom < targetZoom)
				gameCam.zoom += zoomStep;
			else if (gameCam.zoom > targetZoom)
				gameCam.zoom -= zoomStep;
		}

		// Update position
		if (following != null) {
			Body body = following.getBody();
			Vector2 tmp = new Vector2(body.getPosition());
			// unproject(position.set(tmp.x, tmp.y, 0));
			targetPosition.set(tmp.x * ppm, tmp.y * ppm, 1);

			// Dynamic zoom
			targetZoom = 0.8f + body.getLinearVelocity().len()
					/ following.getMaxHspeed() * 0.4f;
		}

		diff.set(targetPosition).sub(gameCam.position);
		float dist = diff.len();
		float realStep = (dist/30f) * (dist/30f);
		if (dist < realStep) {
			gameCam.position.set(targetPosition);
		} else {
			Vector3 pan = new Vector3(diff.nor().mul( /*(dist/ 30f) **/ realStep ));
			gameCam.position.add(pan);
		}

		gameCam.update();
		UICam.update();
	}
}
