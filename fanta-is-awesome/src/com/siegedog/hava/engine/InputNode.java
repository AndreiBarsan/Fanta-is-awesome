package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

// Provides an uniform, code-based output to any object
// allowing things like platformer entities to be controlled
// by the player, by an AI, or by a replay manager
abstract public class InputNode extends Node {

	public InputNode() {
		super("input");
	}

	abstract public Vector2 getMovement();
	abstract public boolean getJump();
	abstract public boolean getAttack();

}
