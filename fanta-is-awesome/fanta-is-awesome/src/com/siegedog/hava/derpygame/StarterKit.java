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

import java.util.ArrayList;
import java.util.Stack;

import org.omg.CORBA.portable.ApplicationException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.siegedog.hava.engine.FancyGame;

public class StarterKit extends FancyGame {

	@Override
	public void create () {
		// Ok, the game started. Load menu and shit here.		
		pushScreen(new GameplayScreen(this));
	}
	
	
	

}