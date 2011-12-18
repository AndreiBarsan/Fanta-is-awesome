package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public interface IAIController 
{
	public Vector2 calculateMovement(Unit target, Unit player);
	public boolean calculateJump(Unit target, Unit player);
	public boolean calculateAttack(Unit target, Unit player);
}
