package com.siegedog.hava.engine;

import com.badlogic.gdx.math.Vector2;
import com.siegedog.hava.derpygame.LRR;

public class AIInputNode extends InputNode 
{
	IAIController ai;
	LRR player;
	
	
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
		return ai.calculateMovement((Unit)parent, (Unit)player);
	}
	
	@Override
	public boolean getJump()
	{
		return ai.calculateJump((Unit)parent, (Unit)player);
	}
	
	@Override
	public boolean getAttack()
	{
		return ai.calculateAttack((Unit)parent, (Unit)player);
	}
	
	public AIInputNode(LRR _player)
	{
		player = _player;
	}
	
	public AIInputNode(LRR _player, IAIController ctrl)
	{
		player = _player;
		ai = ctrl;
	}
}
