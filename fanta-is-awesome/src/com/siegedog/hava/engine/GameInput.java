package com.siegedog.hava.engine;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.siegedog.hava.derpygame.GameplayScreen;

public class GameInput extends InputAdapter implements InputProcessor {

	GameCam2D cam;
	
	public GameInput(FancyGame game, GameplayScreen screen) {
		cam = screen.getCam();	
	}
	
	@Override
	public boolean keyUp(int keycode) {
		// center on actor or something
		//cam.center();
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// Changes the point "Current" to virtual world co-ordinates
		// from its 2d window coords.
		cam.drag(x, y);
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		cam.tap(x, y);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		cam.zoom(amount);
		return false;
	}

}
