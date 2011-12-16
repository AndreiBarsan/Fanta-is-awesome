/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.siegedog.hava.derpygame;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.siegedog.hava.engine.Unit;
import com.siegedog.hava.engine.test.LolGun;

public class StarterKitDesktop {
	public static void main (String[] argv) {
		new JoglApplication(new StarterKit(), "DERP", 640, 480, false);		
	}
}
