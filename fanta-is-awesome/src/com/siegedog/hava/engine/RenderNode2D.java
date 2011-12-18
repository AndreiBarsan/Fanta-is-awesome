package com.siegedog.hava.engine;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RenderNode2D extends Node {

	public boolean visible;
	private Sprite sprite;

	private int w, h;
	private Texture texture;
	
	private float time = 0f;

	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private Animation activeAnimation;

	public RenderNode2D(Sprite sprite) {
		super("sprite");
		this.sprite = sprite;
		this.visible = true;
		renderNodes.add(this);
	}

	public RenderNode2D(String path, int w, int h) {
		super("sprite");
		texture = new Texture(Gdx.files.internal(path), false);
		this.w = w;
		this.h = h;
		this.sprite = new Sprite(texture, w, h);
		this.visible = true;
		renderNodes.add(this);
	}
	
	// most animations are just subsequent texture coords of the current one
	public void addAnimationByFrameCount(String name, int frames, float frameTime) {
		ArrayList<TextureRegion> keyFrames = new ArrayList<TextureRegion>();
		
		for(int i = 0; i<frames; i++) {
			keyFrames.add(new TextureRegion(texture, i * w, 0, w, h));
		}
		
		animations.put(name, new Animation(frameTime, keyFrames));
	}

	public void addAnimation(String name, Animation animation) {
		animations.put(name, animation);
	}

	public void play(String name) {
		if (animations.containsKey(name)) {
			
			if(activeAnimation != animations.get(name)) {
				time = 0f;
				activeAnimation = animations.get(name);
			}
		}
	}
	
	public void stop() {
		activeAnimation = null;
	}
	
	public boolean flipped = false; 
	public void flip(boolean flip) {
		if(flip && !flipped) {
			flipped = true;
		//	sprite.flip(true, false);
		}
		
		if(!flip && flipped){
			flipped = false;
			//sprite.flip(true, false);
		}
	}
	
	Vector2 offset = new Vector2(0, 0);
	public void setOffset(float x, float y) {
		offset.set(x, y);
	}
	public Vector2 getOffset() {
		return offset;
	}

	@Override
	public void update(float delta) {
		time += delta;
		if (activeAnimation != null) {
			TextureRegion tr = new TextureRegion(activeAnimation.getKeyFrame(time, true));
			if(flipped) tr.flip(true, false);
			sprite.setRegion(tr);
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void render(SpriteBatch sb, float alpha) {

		sprite.draw(sb, alpha);
	}
	
	@Override
	protected void dispose() {
		renderNodes.remove(this);
		super.dispose();
	}

}
