package com.siegedog.hava.engine;

public class Message {
	public int code;
	public Object arg;
	
	public Message(int code, Object arg) {
		this.code = code;
		this.arg = arg;
	}
}
