package com.siegedog.hava.engine;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Node {
	protected ArrayList<Node> children = new ArrayList<Node>();
	protected ArrayList<Node> removeList = new ArrayList<Node>();
	protected Node parent = null;

	// LinkedList<Message> messages = new LinkedList<Message>();
	protected static final ArrayList<RenderNode2D> renderNodes = new ArrayList<RenderNode2D>();

	protected final float PIXELS_PER_METER = 32.0f;
	
	protected final int MSG_INIT = 0x0000;
	// protected final int MSG_QUERY = 0x0001;

	protected final int MSG_DERP = 0x0010;
	protected final int MSG_ATTACK = 0x0011;
	protected final int MOVE = 0x0012;
	protected final int JUMP = 0x0013;
	protected final int MSG_SHOT = 0x0020;
	protected final int MSG_SHOOT = 0x0021;

	protected String name;
	int iit, len;

	public Node(String name) {
		this.name = name;
		// messages.add(new Message(MSG_INIT, null));
	}
	
	public String getName() {
		return name;
	}

	public Node getParent()
	{
		return parent;
	}
	
	/*
	protected final Node qc(Class c) {
		for (Node node : children)
			if (node.getClass().equals(c))
				return node;

		return null;
	}*/
	
	public Object postMsg(Message message) {
		Object result = null;
		for (Node node : children)
			if ((result = node.handleMsg(message)) != null)
				break;

		return result;
	}
	
	public Object postMsg(int code, Object arg) {
		return postMsg(new Message(code, arg));
	}
	
	protected Object handleMsg(Message message) {
		return postMsg(message);
	}

	public Node addNode(Node node) {
		children.add(node);
		node.parent = this;
		return this;
	}

	public boolean removeNode(Node node)
	{
		boolean res = children.remove(node);
		
		if(res)
		{
			node.parent = null;
		}
		
		return res;
	}
	
	public void update(float delta) {
		for (Node node : children) 
		{
			node.update(delta);
		}
		
		for(Node node: removeList)
		{
			node.dispose();
		}
		
		removeList.clear();
	}
		
	public void markDispose()
	{
		if(parent != null)
		{
			parent.removeList.add(this);
		}
	}
	
	protected void dispose()
	{
		for(int i = children.size() - 1; i>=0; i--)
			children.get(i).dispose();
	}
	
	public static void renderAllNodes(SpriteBatch sb) {
		for (RenderNode2D node : renderNodes) {
			if(node.visible) node.render(sb, 1.0f);
		}
	}
}
