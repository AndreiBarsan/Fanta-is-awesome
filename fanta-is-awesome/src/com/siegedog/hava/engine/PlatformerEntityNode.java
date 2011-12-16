package com.siegedog.hava.engine;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.scenes.scene2d.Interpolator;

public class PlatformerEntityNode extends BoxNode {

	protected boolean touchWall;
	ContactListener listener;

	InputNode inputNode;

	private float lastGroundTime = System.nanoTime();

	int numFootContacts = 0;
	private float jumpTimeout;
	protected Fixture footSensor;

	protected float jumpTimeLeft = 0f;
	protected float jumpTimeTotal = 0.080f;

	protected float airFriction = 0.1f;
	protected float groundFriction = 6f;

	protected float sideMoveStrength = 20f;
	protected float maxHspeed = 22f;

	float jumpEndStrength = 10f;
	float jumpStartStrength = 100f;
	float w, h;

	public float getMaxHspeed() {
		return maxHspeed;
	}

	public PlatformerEntityNode(float x, float y, float w, float h) {
		super(null, null);
		addNode(inputNode = new InputNode());
		setPosition(x, y);
		setDimensions(w, h);

		this.w = w;
		this.h = h;
		
		fDef.isSensor = true;
		polyShape.setAsBox(w / (2 * PIXELS_PER_METER) - 0.05f, 0.1f,
				new Vector2(0f, -0.15f), 0f);
		footSensor = body.createFixture(fDef);
/*
		world.setContactListener(new ContactListener() {

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}

			@Override
			public void endContact(Contact contact) {
				if (contact.getFixtureA() == footSensor
						|| contact.getFixtureB() == footSensor)
					numFootContacts--;
			}

			@Override
			public void beginContact(Contact contact) {
				if (contact.getFixtureA() == footSensor
						|| contact.getFixtureB() == footSensor)
					numFootContacts++;
			}
		});*/
	}

	boolean jumped = false;

	protected void jump() {
		// TODO: apply vertical momentum if
		// certain conditions are met
		// - on ground or in water OR multi-jump available
		float delta = Gdx.graphics.getDeltaTime();
		jumped = true;

		if (jumpTimeLeft > 0) {
			jumpTimeLeft -= delta;
			Vector2 pos = body.getPosition();
			float vi = MU.lerp(jumpEndStrength, jumpStartStrength, jumpTimeLeft
					/ jumpTimeTotal);

			body.applyLinearImpulse(new Vector2(0, vi), body.getLocalCenter());

			// Resources.play("Powerup21");

		}
	}

	protected void move(Vector2 impulse) {
		Vector2 center = new Vector2(body.getLocalCenter());
		float hspeed = body.getLinearVelocity().x;
		float vspeed = body.getLinearVelocity().y;

		/*
		Vector2 moti = new Vector2(impulse).mul(maxHspeed - hspeed);
		Vector2 fm = new Vector2(impulse).mul(sideMoveStrength);
		if (impulse.len2() > 0f) {
			if (Math.abs(hspeed + impulse.x * sideMoveStrength) < maxHspeed)
				body.applyLinearImpulse(fm, center);
			else if (Math.abs(hspeed) < maxHspeed)
				body.applyLinearImpulse(moti, center);
			else 
				System.out.println("NOPE");
		}*/
		
		body.applyLinearImpulse(impulse.mul(20), center);
		Vector2 ov = body.getLinearVelocity();
		if(ov.x > maxHspeed) {
			body.setLinearVelocity(maxHspeed, ov.y);			
		} else if(ov.x < -maxHspeed) {
			body.setLinearVelocity(-maxHspeed, ov.y);
		}
	}

	protected void handleInput(float delta) {
		move(inputNode.getMovement());

		if (inputNode.getJump())
			jump();
	}

	protected void respawn() {
		body.setTransform(new Vector2(20, 60), 0);
		body.setLinearVelocity(0.0f, 0.0f);
	}

	@Override
	public void update(float delta) {
		// Set various flags based on the input node
		handleInput(delta);

		touchFloor = touchesFloor();
		if (!touchFloor) {
			// in the air
			bodyFixture.setFriction(airFriction);

			if (!jumped)
				jumpTimeLeft = 0f;
		} else {
			// on the ground
			bodyFixture.setFriction(groundFriction);
			jumpTimeLeft = jumpTimeTotal;
		}
		jumped = false;

		if (body.getPosition().y < 0) {
			respawn();
		}

		jumpTimeout -= delta;
		if (jumpTimeout < 0f)
			jumpTimeout = 0f;

		touchWall = touchesWall();
		
		

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			respawn();
		}
	}
	boolean touchFloor;

	public String getUI() {
		// if(numFootContacts==0 &&

		return ((touchWall) ? "[tw]" : "")
				+ ((numFootContacts == 0) ? " [in air]" : ("[ground "
				+ numFootContacts + "]"))
				+ "[" + (bodyFixture.getFriction()) + "] "
				+ (body.getPosition());
	}

	protected boolean touchesWall() {

		if (world.getContactCount() == 0)
			return false;

		List<Contact> contacts = world.getContactList();
		for (Contact c : contacts) {
			if (c.getFixtureA() == bodyFixture
					|| c.getFixtureB() == bodyFixture) {
				return true;
			}
		}

		return false;
	}
	
	protected boolean touchesFloor() {

		if (world.getContactCount() == 0)
			return false;

		List<Contact> contacts = world.getContactList();
		for (Contact c : contacts) {
			if (c.getFixtureA() == bodyFixture
					|| c.getFixtureB() == bodyFixture) {
				/*Fixture otherFixture;
				if(c.getFixtureA() == bodyFixture) 
					otherFixture = c.getFixtureB();
				else
					otherFixture = c.getFixtureA();*/
				
				if(c.getWorldManifold().getNormal().y > 0)
					return true;
			}
		}

		return false;
	}

}
