package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;

public class AIInputNode extends InputNode 
{
	IAIController ai;
	
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
	
	public AIInputNode()
	{
		
	}
	
	public AIInputNode(IAIController ctrl)
	{
		ai = ctrl;
	}
}
