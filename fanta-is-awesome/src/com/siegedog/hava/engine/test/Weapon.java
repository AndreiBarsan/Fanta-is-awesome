package com.siegedog.hava.engine.test;

import com.siegedog.hava.engine.Message;
import com.siegedog.hava.engine.Node;

public class Weapon extends Node {
	protected float damage;
	protected float cooldownTimeTotal;
	protected float cooldownTimeLeft;

	protected int ammoTotal;
	protected int ammoLeft;
	
	protected Scope scope;

	public Weapon(float damage, float cooldown, int ammoTotal) {
		this("weapon", damage, cooldown, ammoTotal);
	}
	
	public Weapon(String name, float damage, float cooldown, int ammoTotal) {
		super(name);
		this.damage = damage;
		this.cooldownTimeLeft = this.cooldownTimeTotal = cooldown;
		this.ammoLeft = this.ammoTotal = ammoTotal;
	}
	
	public Node mountScope(Scope scope) {
		this.scope = scope;
		return this;
	}
	
	@Override
	protected Object handleMsg(Message message) {
		if(message.code == MSG_SHOOT) {
			return shoot((Node)message.arg);
		}
		else {
			// no child of weapon can do anything more
			return null;
		}
	}

	public Node shoot(Node target) {
		// Normally this would spawn a bullet and it will
		// automatically only be able to his physics-enabled
		// things.
		
		// This is the code that would actually run on collision
		// in a realtime game.
		
		// PROBLEM: multiple weapons, how do we check if someone
		// just has a weapon, regardless of type?
		// query for Weapon.class
		
		// keke suggested by a dude who worked on THPS :D

		float dmg = damage;
		// if (qc(Scope.class) != null) dmg *= 2;
		
		System.out.println(parent.getName() + " shoots at " + target.getName() + " with his " + name + "!");
		target.postMsg(new Message(MSG_SHOT, dmg));
		
		return this;
	}
}
