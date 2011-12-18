package com.siegedog.hava.engine;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MU extends MathUtils {
	
	public static float clamp(float value, float min, float max) {
		if(value < min)
			return min;
		
		if(value > max)
			return max;
		
		return value;
	}
	
	public static float lerp(float start, float end, float alpha) {
		return start + (end - start) * alpha;
	}
	
	public static void clamp(Vector2 value, Vector2 min, Vector2 max) {
		value.x = clamp(value.x, min.x, max.x);
		value.y = clamp(value.y, min.y, max.y);
	}

}
