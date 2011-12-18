package com.siegedog.hava.derpygame;

import com.siegedog.hava.engine.PlatformerEntityNode;
import com.siegedog.hava.engine.RenderNode2D;

public class UberJumpNode extends PickupNode 
{
	// public methods
	
	@Override
	public void applyTo(LRR player)
	{
		PlatformerEntityNode pTarget = player.getPhysics();
		float uberJump = pTarget.getJumpTime() * 2.0f;
		pTarget.setJumpTime(uberJump);
	}
	
	@Override
	public void removeFrom(LRR player)
	{
		PlatformerEntityNode pTarget = player.getPhysics();
		float lameJump = pTarget.getJumpTime() / 2.0f;
		pTarget.setJumpTime(lameJump);
	}
	
	
	// ctors
	
	public UberJumpNode(int posX, int posY, int sizeX, int sizeY)
	{
		super("uber jump", posX, posY, 10, 10);
		renderNode = new RenderNode2D("data/img/sprites/penisBlue.png", 32, 32);
		renderNode.setOffset(16, 16);
		addNode(renderNode);
	}
}
