package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public class AIInputNode extends InputNode 
{
	IAIController ai;
	Unit ctrlUnit;
	
	
	public IAIController getAI()
	{
		return ai;
	}
	
	public void setAI(IAIController value)
	{
		ai = value;
	}
	
	@Override
	public Vector2 getMovement()
	{
		return new Vector2();
		//return ai.calculateMovement();
	}
	
	@Override
	public boolean getJump()
	{
		return false;
		//return ai.calculateJump();
	}
	
	@Override
	public boolean getAttack()
	{
		return false;
		//return ai.calculateAttack();
	}
	
	public AIInputNode()
	{
		
	}
	
	public AIInputNode(IAIController ctrl)
	{
		ai = ctrl;
	}
}
