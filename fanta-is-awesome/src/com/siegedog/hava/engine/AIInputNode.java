package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public abstract class AIInputNode extends InputNode 
{
	IAIController ai;
	
	@Override
	public Vector2 getMovement()
	{
		return ai.calculateMovement();
	}
	
	@Override
	public boolean getJump()
	{
		return ai.calculateJump();
	}
	
	@Override
	public boolean getAttack()
	{
		return ai.calculateAttack();
	}
}
