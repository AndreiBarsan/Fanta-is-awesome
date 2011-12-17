package com.siegedog.hava.engine;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

// This guy over here handles 
// sounds and sprites
public class Resources {

	private static Sound test;
	private static String extension = ".wav";
	private static String sfxRoot;

	private static AssetManager assetManager = new AssetManager();
	private static boolean done = false;

	public static void update(float delta) {
		boolean old = done;
		done = assetManager.update();

		if (!old && done)
			System.out.println("DONE LOADING");

		if (old && !done)
			System.out.println("STARTING LOAD");
	}

	public static void loadSfx(String sfxRoot) {
		Resources.sfxRoot = sfxRoot;
		FileHandle file = new FileHandle(sfxRoot + "Powerup21" + extension);
		assetManager.load(sfxRoot + "Powerup21" + extension, Sound.class);
	}

	public static void play(String sound) {
		if (assetManager.isLoaded(sfxRoot + sound+ extension))
			(assetManager.get(sfxRoot + sound+ extension, Sound.class)).play();
		else
			System.out.println("DUDE! " + sound + " isn't loaded yet. Jeez.");
	}

}
