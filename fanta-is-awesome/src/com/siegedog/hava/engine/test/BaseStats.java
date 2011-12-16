package com.siegedog.hava.engine.test;

import com.siegedog.hava.engine.Message;
import com.siegedog.hava.engine.Node;


public class BaseStats extends Node {
	protected float healthTotal;
	protected float healthLeft;
	protected boolean dead;

	public BaseStats(float health) {
		super("baseStats");

		healthLeft = healthTotal = health;
		dead = false;
	}

	@Override
	protected Object handleMsg(Message message) {
		if (message.code == MSG_SHOT) {
			takeDamage((Float) message.arg);
		}
		return super.handleMsg(message);
	}

	private void takeDamage(float amount) {
		if (dead)
			return;

		System.out.println(parent.getName() + " takes " + amount + " damage!");
		healthLeft -= amount;
		if (healthLeft <= 0.0f) {
			dead = true;
			System.out.println(parent.getName() + " has died!");
		} else {
			System.out.println(parent.getName() + " has " + healthLeft
					+ " health left.");
		}
	}

	public boolean isDead() {
		return dead;
	}
}
