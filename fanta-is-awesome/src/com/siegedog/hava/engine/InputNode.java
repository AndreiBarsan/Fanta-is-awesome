package com.siegedog.hava.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

// Provides an uniform, code-based output to any object
// allowing things like platformer entities to be controlled
// by the player, by an AI, or by a replay manager
public class InputNode extends Node {

	public InputNode() {
		super("input");
	}
	
	@Override
	public void update(float delta) {
		
		super.update(delta);
	}

	// TODO: make inherited node that actually gets
	// the keyboard input, also an AI Inputnode that
	// controls an enemy through the very same mode
	public Vector2 getMovement() {
		Vector2 ret = new Vector2();
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			ret.add(-1f, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			ret.add(1f, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			//parent.postMsg(JUMP, null);
			// parent.msg(MOVE, new Vector2(0, 0.1f));
		}
		
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			//parent.postMsg(MOVE, new Vector2(0, -1f));
		}
		
		return ret;
	}
	
	public boolean getJump() {
		return Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE);
	}
	
	// TODO: implement
	public boolean getAttack() {
		return false;
	}

}
