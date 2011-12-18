package com.siegedog.hava.derpygame;

import com.siegedog.hava.engine.InputNode;
import com.siegedog.hava.engine.RenderNode2D;
import com.siegedog.hava.engine.Unit;

public class PedoBear extends Unit
{
	boolean oldLeft = false;
	boolean left;
	
	public PedoBear(float x, float y, InputNode _inputNode) {
		super("PEDOBEAR", x, y, 32, 32, _inputNode, new RenderNode2D(
				"data/img/sprites/pedoBear.png", 128, 64));
		renderNode.setOffset(64f, 16f);
		renderNode.addAnimationByFrameCount("basic", 8, 0.1f);
		renderNode.addAnimationByFrameCount("idle", 1, 0.1f);
	}
	
	@Override
	public void update(float delta) {
		
		float xs = physics.getBody().getLinearVelocity().x;
		if (xs > 0.1f) {
			renderNode.flip(true);
			renderNode.play("basic");
		} else if (xs < -0.1f) {
			renderNode.flip(false);
			renderNode.play("basic");
		} else
			renderNode.play("idle");
		
		super.update(delta);
	}
}
