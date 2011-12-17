package com.siegedog.hava.derpygame;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.siegedog.hava.engine.Unit;

public class StarterKitDesktop {
	public static void main (String[] argv) {
		new JoglApplication(new StarterKit(), "DERP", 640, 480, false);		
	}
}