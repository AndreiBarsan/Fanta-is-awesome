package com.siegedog.hava.engine;

import com.badlogic.gdx.math.MathUtils;

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

}
