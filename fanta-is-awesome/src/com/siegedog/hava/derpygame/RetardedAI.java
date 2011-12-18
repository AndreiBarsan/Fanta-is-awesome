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
		
		float move = player.getPhysics().getBody().getPosition().x - target.getPhysics().getBody().getPosition().x;
		if(move != 0)
		{
			move /= Math.abs(move);
		}
		
		result.x = move;
		
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
