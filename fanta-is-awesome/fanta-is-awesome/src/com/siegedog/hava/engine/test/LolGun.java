package com.siegedog.hava.engine.test;

import com.siegedog.hava.engine.Node;

public class LolGun extends Weapon {

	public LolGun() {
		super("Lolgun", 100f, 2, 10);
		addNode(new Scope());
	}
	
	@Override
	public Node shoot(Node target) {
		System.out.println("Holy fuck " + parent.getName() + " has a LoLgun!!11");
		return super.shoot(target);
	}
}
