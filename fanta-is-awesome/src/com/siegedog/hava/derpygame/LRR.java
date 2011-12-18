package com.siegedog.hava.derpygame;

import java.util.ArrayList;

import com.siegedog.hava.engine.InputNode;
import com.siegedog.hava.engine.RenderNode2D;
import com.siegedog.hava.engine.Unit;

public class LRR extends Unit {

	boolean oldLeft = false;
	boolean left;
	ArrayList<PickupNode> powerups = new ArrayList<PickupNode>();
	
	public LRR(float x, float y, InputNode _inputNode) {
		super("Little Red Riding Hewd", x, y, 12, 32, _inputNode, new RenderNode2D(
				"data/img/sprites/lrrsm.png", 34, 64));
		
		//physics.setBody(rrb, rrf);
	
		renderNode.setOffset(16f, 26f);
		renderNode.addAnimationByFrameCount("basic", 8, 0.05f);
		renderNode.play("basic");
	}
	
	@Override
	public void update(float delta) {
		
		float xs = physics.getBody().getLinearVelocity().x;
		if (xs > 0.1f) {
			renderNode.flip(false);
			renderNode.play("basic");
		} else if (xs < -0.1f) {
			renderNode.flip(true);
			renderNode.play("basic");
		} else
			renderNode.stop();
		
		super.update(delta);
	}

	public void applyPickup(PickupNode target)
	{
		System.out.println("Found pickup: " + target.getName());
		powerups.add(target);
		target.applyTo(this);
	}
}
