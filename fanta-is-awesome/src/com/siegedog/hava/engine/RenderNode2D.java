package com.siegedog.hava.engine;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	public void addAnimationByFrameCount(String name, int frames) {
		ArrayList<TextureRegion> keyFrames = new ArrayList<TextureRegion>();
		
		for(int i = 0; i<frames; i++) {
			keyFrames.add(new TextureRegion(texture, i * w, 0, w, h));
		}
		
		animations.put(name, new Animation(0.1f, keyFrames));
	}

	public void addAnimation(String name, Animation animation) {
		animations.put(name, animation);
	}

	public void play(String name) {
		if (animations.containsKey(name)) {
			time = 0f;
			activeAnimation = animations.get(name);
		}
	}

	@Override
	public void update(float delta) {
		time += delta;
		if (activeAnimation != null) {
			TextureRegion tr = activeAnimation.getKeyFrame(time, true);
			sprite.setRegion(tr);
		}

		
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void render(SpriteBatch sb, float alpha) {

		sprite.draw(sb, alpha);
	}

}
