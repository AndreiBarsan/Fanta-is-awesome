package com.siegedog.hava.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Unit extends Node {

	PlatformerEntityNode physics;
	RenderNode2D renderNode;

	public Unit(String name) {
		super(name);

		addNode(physics = new PlatformerEntityNode(80, 30, 10, 10));

		// TODO: sprite from resource manager
		Sprite sprite = new Sprite(
				new Texture("data/img/sprites/attractor.png"));
		addNode(renderNode = new RenderNode2D(sprite));
	}

	public PlatformerEntityNode getPhysics() 
	{
		return physics;
	}


	@Override
	public void update(float delta) {
		// Tell the render node to put the sprite where it's supposed
		// to be based on the physics.
		physics.syncSprite(renderNode);
		super.update(delta);
	}

	public String getBoxDebug(){
		return physics.getUI();
	}
}
