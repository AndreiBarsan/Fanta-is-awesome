package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public interface IAIController 
{
	public Vector2 calculateMovement();
	public boolean calculateJump();
	public boolean calculateAttack();
}
