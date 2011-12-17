package com.siegedog.hava.engine;

import java.util.Stack;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Interpolator;

public class FancyGame implements ApplicationListener {

	private Stack<Screen> screens = new Stack<Screen>();
	protected float delta;
	
	public void pushScreen(Screen screen) {
		screens.push(screen);
	}
	
	public void popScreen() {
		screens.pop();
	}	
	
	@Override
	public void create() {

	}

	@Override
	public void render() {
		delta = Gdx.graphics.getDeltaTime();
		
		// TODO: only make the peek screen accept keyboard input
		for(Screen s : screens)
			s.render(delta);
	}
	

	@Override
	public void resize(int width, int height) {
		for(Screen s : screens) 
			s.resize(width, height);
	}

	@Override
	public void pause() {
		for(Screen s : screens) 
			s.pause();
	}

	@Override
	public void resume() {
		for(Screen s : screens) 
			s.resume();
	}

	@Override
	public void dispose() {
		for(Screen s : screens) 
			s.dispose();
	}

}
