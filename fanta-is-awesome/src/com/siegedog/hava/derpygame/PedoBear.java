package com.siegedog.hava.derpygame;

import java.util.ArrayList;

import com.siegedog.hava.engine.InputNode;
import com.siegedog.hava.engine.RenderNode2D;
import com.siegedog.hava.engine.Unit;

public class PedoBear extends Unit
{
	boolean oldLeft = false;
	boolean left;
	
	public PedoBear(float x, float y, InputNode _inputNode) {
		super("PEDOBEAR", x, y, 12, 32, _inputNode, new RenderNode2D(
				"data/img/sprites/penis.png", 32, 32));
		renderNode.setOffset(16f, 26f);
	}
	
	@Override
	public void update(float delta) {
		
		/*float xs = physics.getBody().getLinearVelocity().x;
		if (xs > 0.1f) {
			renderNode.flip(false);
			renderNode.play("basic");
		} else if (xs < -0.1f) {
			renderNode.flip(true);
			renderNode.play("basic");
		} else
			renderNode.stop();*/
		
		super.update(delta);
	}
}
