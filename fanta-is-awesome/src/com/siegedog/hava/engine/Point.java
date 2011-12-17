package com.siegedog.hava.engine;

public class Point {
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point point){
		this.x = point.x;
		this.y = point.y;
	}
}
