package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public class LulzyAINode extends Node implements IAIController 
{
	// fields
	
	
	
	// public methods
	
	public Vector2 calculateMovement()
	{
		Vector2 res;
		
		if(System.currentTimeMillis() % 2 == 0)
		{
			res = new Vector2(-1,0);
		}
		else
		{
			res = new Vector2(1,0);
		}
		
		return res;
	}
	
	public boolean calculateJump()
	{
		return System.currentTimeMillis() % 2 == 0;
	}
	
	public boolean calculateAttack()
	{
		return false;
	}
		
	public LulzyAINode()
	{
		super("lulzyAI");
	}
}
