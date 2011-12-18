package com.siegedog.hava.derpygame;

import com.badlogic.gdx.math.Vector2;
import com.siegedog.hava.engine.IAIController;
import com.siegedog.hava.engine.Unit;

public class RetardedAI implements IAIController 
{
	// public methods
	
	public Vector2 calculateMovement(Unit target, Unit player)
	{
		Vector2 result = new Vector2(0,0);
		
		player.getPhysics().getBody().getPosition();
		
		return result;
	}
	
	public boolean calculateJump(Unit target, Unit player)
	{
		return false;
	}
	
	public boolean calculateAttack(Unit target, Unit player)
	{
		return false;
	}
	
	// ctors
	
	public RetardedAI()
	{
	}
}
