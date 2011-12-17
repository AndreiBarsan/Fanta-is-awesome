package com.siegedog.hava.engine.test;

import java.util.Random;

import com.siegedog.hava.engine.Node;

public class ArmorPatch extends Node {

	float mitigation;
	
	public ArmorPatch() {
		super("ArmorPatch");
		Random r = new Random();
		mitigation = r.nextFloat() * 50;
	}

}
