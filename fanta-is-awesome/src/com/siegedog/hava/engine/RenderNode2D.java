package com.siegedog.hava.engine;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class RenderNode2D extends Node {

	public boolean visible; 
	private Sprite sprite;
	
	private float time = 0f;
	
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private Animation activeAnimation;
	
	public RenderNode2D(Sprite sprite) {
		super("sprite");	
		this.sprite = sprite;
		this.visible = true;
		renderNodes.add(this);
	}
	
	public void addAnimation(String name, Animation animation) {
		animations.put(name, animation);
	}
	
	public void play(String name) {
		if(animations.containsKey(name)) {
			time = 0f;
			activeAnimation = animations.get(name);
		}
	}
	
	@Override
	public void update(float delta) {
		time+=delta;
		int originX = 0, originY = 0;
		if(activeAnimation != null) {
			TextureRegion tr = activeAnimation.getKeyFrame(time, true);
			originX = tr.getRegionX();
			originY = tr.getRegionY();
		}
		
		sprite.setOrigin(originX, originY);
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void render(SpriteBatch sb, float alpha) {
		
		sprite.draw(sb, alpha);
	}

}
