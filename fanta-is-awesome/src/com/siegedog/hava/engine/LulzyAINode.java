package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public class LulzyAINode extends Node implements IAIController 
{
	// fields
	
	
	
	// public methods
	
	public Vector2 calculateMovement()
	{
<<<<<<< HEAD
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
=======
		return null;
>>>>>>> 50c7e7d65e247d477738efd6372156e2d1101dfc
	}
	
	public boolean calculateJump()
	{
<<<<<<< HEAD
		return System.currentTimeMillis() % 2 == 0;
=======
		return true;
>>>>>>> 50c7e7d65e247d477738efd6372156e2d1101dfc
	}
	
	public boolean calculateAttack()
	{
<<<<<<< HEAD
		return false;
=======
		return true;
>>>>>>> 50c7e7d65e247d477738efd6372156e2d1101dfc
	}
		
	public LulzyAINode()
	{
		super("lulzyAI");
	}
}
