package com.siegedog.hava.derpygame;

import com.siegedog.hava.engine.PlatformerEntityNode;
import com.siegedog.hava.engine.RenderNode2D;

public class UberSpeedNode extends PickupNode
{
	// public methods
	
		@Override
		public void applyTo(LRR player)
		{
			PlatformerEntityNode pTarget = player.getPhysics();
			float uberSpeed = pTarget.getMaxHspeed() * 2.0f;
			pTarget.setMaxHspeed(uberSpeed);
		}
		
		@Override
		public void removeFrom(LRR player)
		{
			PlatformerEntityNode pTarget = player.getPhysics();
			float lameSpeed = pTarget.getMaxHspeed() / 2.0f;
			pTarget.setMaxHspeed(lameSpeed);
		}
		
		
		// ctors
		
		public UberSpeedNode(int posX, int posY, int sizeX, int sizeY)
		{
			super("uber speed", posX, posY, 10, 10);
			renderNode = new RenderNode2D("data/img/sprites/penisGreen.png", 32, 32);
			renderNode.setOffset(16, 16);
			addNode(renderNode);
		}
}
