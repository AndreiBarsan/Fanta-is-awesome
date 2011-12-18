package com.siegedog.hava.derpygame;

import com.siegedog.hava.engine.RenderNode2D;

public class DoNothingPickup extends PickupNode 
{
	// fields
	
	RenderNode2D renderNode;
	
	// ctors
	
	public DoNothingPickup(int posX, int posY, int sizeX, int sizeY)
	{
		super("imma do nothing", posX, posY, sizeX, sizeY);
		renderNode = new RenderNode2D("data/img/sprites/penis.png", 32, 32);
		addNode(renderNode);
	}
}
