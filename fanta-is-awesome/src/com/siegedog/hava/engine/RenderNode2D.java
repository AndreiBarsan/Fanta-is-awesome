package com.siegedog.hava.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RenderNode2D extends Node {

	public boolean visible; 
	private Sprite sprite;
	
	public RenderNode2D(Sprite sprite) {
		super("sprite");
		
		this.sprite = sprite;
		this.visible = true;
		renderNodes.add(this);
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void render(SpriteBatch sb, float alpha) {
		sprite.draw(sb, alpha);
	}

}
