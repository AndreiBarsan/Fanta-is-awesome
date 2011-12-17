package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public class LulzyAINode extends Node implements IAIController 
{
	// public methods
	
	public Vector2 calculateMovement()
	{
		return null;
	}
	
	public boolean calculateJump()
	{
		return true;
	}
	
	public boolean calculateAttack()
	{
		return true;
	}
		
	public LulzyAINode()
	{
		super("lulzyAI");
	}
}
