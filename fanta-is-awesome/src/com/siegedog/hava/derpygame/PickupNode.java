package com.siegedog.hava.derpygame;

import com.siegedog.hava.engine.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PickupNode extends BoxNode 
{
	// fields
	
	ContactListener listener;
	
	
	// ctors

	public PickupNode(String name, int posX, int posY, int sizeX, int sizeY)
	{
		super(null, null);
		setPosition(posX, posY);
		setDimensions(sizeX, sizeY);

		world.setContactListener(listener = new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				Object o1 = contact.getFixtureA().getUserData();
				Object o2 = contact.getFixtureB().getUserData();

				LRR player = null;
				PickupNode pickup = null;
				if(o1 instanceof LRR && o2 instanceof PickupNode )
				{
					player = (LRR)o1;
					pickup = (PickupNode)o2;
				}
				else
				{
					if(o2 instanceof LRR && o1 instanceof PickupNode)
					{
						player = (LRR)o2;
						pickup = (PickupNode)o1;
					}
				}
				
				if(player != null && pickup != null)
				{
					player.applyPickup(pickup);
				}
			}

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
		});
	}
}
