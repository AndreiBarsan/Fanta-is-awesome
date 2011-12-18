package com.siegedog.hava.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PickupNode extends Node 
{
	// fields
	
	BoxNode physics;
	RenderNode2D renderNode;
	
	// ctors
	
	public PickupNode()
	{
		super("pickup");
		addNode(physics = new BoxNode(null, null));
		physics.setPosition(80, 30);
		physics.setDimensions(10, 10);
		
		// TODO: sprite from resource manager
		Sprite sprite = new Sprite(
				new Texture("data/img/sprites/penis.png"));
		addNode(renderNode = new RenderNode2D(sprite));
	}
}
