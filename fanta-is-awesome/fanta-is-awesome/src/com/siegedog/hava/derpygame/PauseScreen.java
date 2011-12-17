package com.siegedog.hava.derpygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen implements Screen {

	SpriteBatch spriteBatch;
	BitmapFont font;
	
	public PauseScreen(Game game) {
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
	}
	
	@Override
	public void render(float delta) {
		spriteBatch.begin();		
		font.draw(spriteBatch, "TESTING PAUSE SCREEN", 20, 80);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
