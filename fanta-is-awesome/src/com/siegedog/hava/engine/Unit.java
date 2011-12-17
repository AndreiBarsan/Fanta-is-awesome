package com.siegedog.hava.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Unit extends Node {

	protected PlatformerEntityNode physics;
	protected RenderNode2D renderNode;
	protected InputNode inputNode;

	protected void handleInput(float delta) {
		physics.move(inputNode.getMovement());

		if (inputNode.getJump())
			physics.jump(delta);
	}
	
	public Unit(String name, float x, float y, InputNode _inputNode, RenderNode2D _renderNode) {
		super(name);
		addNode(inputNode = _inputNode);
		
		physics = new PlatformerEntityNode(x / PIXELS_PER_METER, y / PIXELS_PER_METER, 10, 10);
		addNode(physics);
		addNode(renderNode = _renderNode);
	}

	public PlatformerEntityNode getPhysics() 
	{
		return physics;
	}


	@Override
	public void update(float delta) {
		// Tell the render node to put the sprite where it's supposed
		// to be based on the physics.
		handleInput(delta);
		physics.syncSprite(renderNode);
		super.update(delta);
	}

	public String getBoxDebug(){
		return physics.getUI();
	}
}
