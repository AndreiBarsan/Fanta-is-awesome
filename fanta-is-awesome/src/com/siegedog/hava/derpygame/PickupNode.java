package com.siegedog.hava.derpygame;

import java.util.List;

import com.siegedog.hava.engine.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.MassData;

public class PickupNode extends BoxNode 
{
	// fields
	
	ContactListener listener;
	RenderNode2D renderNode;
	
	// public methods
	
	@Override
	public void update(float delta)
	{
		syncSprite(renderNode);
		
		if (world.getContactCount() == 0)
		{
			return;
		}

		List<Contact> contacts = world.getContactList();
		for (Contact c : contacts) 
		{
			BoxNode o1 = (BoxNode)c.getFixtureA().getUserData();
			if(o1 == null)
				continue;
			BoxNode o2 = (BoxNode)c.getFixtureB().getUserData();
			if(o2 == null)
				continue;

			if(o1 == null)
			{
				System.out.println("o1 null");
			}
			
			if(o2 == null)
			{
				System.out.println("o2 null");
			}
			
			LRR player = null;
			PickupNode pickup = null;
			if(o1.getParent() != null && o1.getParent() instanceof LRR)
			{
				player = (LRR)o1.getParent();
			}
			if(o2.getParent() != null && o2.getParent() instanceof LRR)
			{
				player = (LRR)o2.getParent();
			}
			if(o1 instanceof PickupNode)
			{
				pickup = (PickupNode)o1;
				
			}
			if(o2 instanceof PickupNode)
			{
				pickup = (PickupNode)o2;
				
			}
			
			if(player != null && pickup != null)
			{
				player.applyPickup(pickup);
				dispose();
			}
		}

	}
	
	
	// ctors

	public PickupNode(String _name, int posX, int posY, int sizeX, int sizeY)
	{	
		super(null, null);
		name = _name;
		
		setPosition(posX, posY);
		setDimensions(sizeX, sizeY);

		this.body.setGravityScale(0);
	}
}
