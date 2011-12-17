package com.siegedog.hava.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.siegedog.hava.engine.test.ArmorPatch;
import com.siegedog.hava.engine.test.BaseStats;
import com.siegedog.hava.engine.test.Weapon;

public class Unit extends Node {

	BaseStats baseStats;
	PlatformerEntityNode physics;
	RenderNode2D renderNode;

	public Unit(String name) {
		super(name);

		addNode(baseStats = new BaseStats(150.0f));
		addNode(physics = new PlatformerEntityNode(80, 30, 10, 10));

		// TODO: sprite from resource manager
		Sprite sprite = new Sprite(
				new Texture("data/img/sprites/attractor.png"));
		addNode(renderNode = new RenderNode2D(sprite));
	}

	public PlatformerEntityNode getPhysics() {
		return physics;
	}

	public Node equip(Weapon weapon) {
		if (baseStats.isDead())
			return this;

		/*
		 * if (qc(Weapon.class) != null) { System.out.println(name +
		 * " already has a weapon: " + weapon.name); return this; }
		 */

		System.out.println(name + " grabs a " + weapon.name);
		addNode(weapon);

		return this;
	}

	public Node equip(ArmorPatch armor) {
		if (baseStats.isDead())
			return this;

		addNode(armor);

		return this;
	}

	@Override
	public void update(float delta) {
		// Tell the render node to put the sprite where it's supposed
		// to be based on the physics.
		physics.syncSprite(renderNode);
		super.update(delta);
	}

	public Node attack(Node target) {
		System.out.println(name + " attacks " + target.name + "!");

		if (baseStats.isDead()) {
			System.out.println(target.name + " is aleady dead....");
			return this;
		}

		// tell any weapon to shoot
		Object result = postMsg(MSG_SHOOT, target);
		if (result == null) {
			System.out.println("SHIT, " + name + " doesn't have a gun!!!");
		}

		return this;
	}
	
	public String getBoxDebug(){
		return physics.getUI();
	}
}
