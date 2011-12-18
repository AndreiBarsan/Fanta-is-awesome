package com.siegedog.hava.derpygame;

import java.util.List;

import com.badlogic.gdx.physics.box2d.Contact;
import com.siegedog.hava.engine.BoxNode;
import com.siegedog.hava.engine.RenderNode2D;

public class PickupNode extends BoxNode 
{
	// fields
	
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
			if(o1 == this)
			{
				pickup = (PickupNode)o1;
				
			}
			if(o2 == this)
			{
				pickup = (PickupNode)o2;
				
			}
			
			if(player != null && pickup != null)
			{
				player.applyPickup(pickup);
				markDispose();
			}
		}

	}
	
	public void applyTo(LRR player)
	{
		
	}
	
	public void removeFrom(LRR player)
	{
		
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
