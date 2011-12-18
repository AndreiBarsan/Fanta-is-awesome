package com.siegedog.hava.derpygame;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class StarterKitDesktop 
{
	public static void main (String[] argv) {
		new JoglApplication(new StarterKit(), "DERP", 1024, 768, false);		
	}
}