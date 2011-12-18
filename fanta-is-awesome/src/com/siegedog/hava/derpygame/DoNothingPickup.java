package com.siegedog.hava.derpygame;

import com.siegedog.hava.engine.RenderNode2D;

public class DoNothingPickup extends PickupNode 
{

	// ctors
	
	public DoNothingPickup(int posX, int posY, int sizeX, int sizeY)
	{
		super("imma do nothing", posX, posY, 10, 10);
		renderNode = new RenderNode2D("data/img/sprites/penis.png", 32, 32);
		renderNode.setOffset(16, 16);
		addNode(renderNode);
	}

}
