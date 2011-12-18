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

	protected Fixture footSensor;
	InputNode inputNode;

	boolean jumped = false;

	float jumpEndStrength = 20f;
	float jumpStartStrength = 80f;
	
	protected float jumpTimeLeft = 0f;
	protected float jumpTimeTotal = 0.1f;
	
	// private float lastGroundTime = System.nanoTime();

	ContactListener listener;

	protected float maxHspeed = 10f;
	int numFootContacts = 0;

	float pFriction = 2f;
	protected float sideMoveStrength = 3f;
	
	protected boolean touchFloor;
	protected boolean touchWall;
	
	protected Vector2 pendingPos;

	float w, h;
	
	Vector2 track;
	
	public PlatformerEntityNode(float x, float y, float w, float h) {
		super(null, null);
		
		//AIInputNode tmpINode = new AIInputNode();
		//tmpINode.setAI(new LulzyAINode());
		//addNode(inputNode = tmpINode);
		setPosition(x, y);
		track = body.getPosition();
		setDimensions(w, h);
		body.setLinearDamping(0f);
		bodyFixture.setFriction(0f);
		body.setGravityScale(0.8f);
		this.w = w;
		this.h = h;

		fDef.isSensor = true;
		polyShape.setAsBox(w / (2 * PIXELS_PER_METER) - 0.03f, 0.1f,
				new Vector2(0f, -0.15f), 0f);
		footSensor = body.createFixture(fDef);

		world.setContactListener(listener = new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				if (contact.getFixtureA() == footSensor
						|| contact.getFixtureB() == footSensor)
					numFootContacts++;
			}

			@Override
			public void endContact(Contact contact) {
				if (contact.getFixtureA() == footSensor
						|| contact.getFixtureB() == footSensor)
					numFootContacts--;
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
		});

	}


	public float getMaxHspeed() {
		return maxHspeed;
	}
	

	public String getUI() {
		
		return ((touchWall) ? "[tw]" : "")
				+ ((numFootContacts == 0) ? " [in air]" : ("[ground "
						+ numFootContacts + "]")) + "["
				+ (bodyFixture.getFriction()) + "] " + (body.getLinearVelocity().x * 10 / 10);
	}


	protected void respawn() {
		System.out.println("respawing" + parent.name);
		body.setTransform(new Vector2(20, 60), 0);
		body.setLinearVelocity(0.0f, 0.0f);
	}

	protected boolean touchesFloor() {

		return numFootContacts != 0;

		/*
		 * 
		 * if (world.getContactCount() == 0) return false;
		 * 
		 * List<Contact> contacts = world.getContactList(); for (Contact c :
		 * contacts) { if (c.getFixtureA() == bodyFixture || c.getFixtureB() ==
		 * bodyFixture) { /* Fixture otherFixture; if(c.getFixtureA() ==
		 * bodyFixture) otherFixture = c.getFixtureB(); else otherFixture =
		 * c.getFixtureA();
		 * 
		 * 
		 * if (c.getWorldManifold().getNormal().y > 0) return true; } }
		 * 
		 * return false;
		 */
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

	@Override
	public void update(float delta) {
		// Set various flags based on the input node
		if(bodyFixture.getFriction() > 140 && Math.abs(body.getLinearVelocity().x) > 0.2f && numFootContacts > 0){
			
			System.out.println("DERP");
		}
		
		touchFloor = touchesFloor();
		if (!touchFloor) {
			// in the air
			//bodyFixture.setFriction(airFriction);

			if (!jumped)
				jumpTimeLeft = 0f;
		} else {
			// on the ground
			// bodyFixture.setFriction(groundFriction);
			jumpTimeLeft = jumpTimeTotal;
		}
		jumped = false;
		
		if (body.getPosition().y < -10) {
			respawn();
		}

		touchWall = touchesWall();

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			respawn();
		}
		
		
		super.update(delta);
	}

	public void jump(float delta) {
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

	public void move(Vector2 impulse) {

		//Vector2 center = new Vector2(body.getLocalCenter());
		float hspeed = body.getLinearVelocity().x;
		float vspeed = body.getLinearVelocity().y;

		float result = 0;
		
		//Vector2 moti = new Vector2(impulse).mul(maxHspeed - Math.abs(hspeed));
		//Vector2 fm = new Vector2(impulse).mul(sideMoveStrength);
		if (impulse.len2() > 0f) {
			//pFriction = 0f;
			
			if (Math.abs(hspeed + impulse.x * sideMoveStrength) < maxHspeed) {
				result += sideMoveStrength;
			}
			else if(impulse.x != 0f)
				result += (maxHspeed - Math.abs(hspeed));
			
		} else {
			// float pFriction = 4f;
		}
		
		
		float out = hspeed + impulse.x * result;
		if(out > 0) 
			out = MU.clamp(out - pFriction, 0, out);
		else
			out = MU.clamp(out + pFriction, out, 0);
		body.setLinearVelocity(new Vector2(out, vspeed));
	
	}
}
