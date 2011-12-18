package com.siegedog.hava.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.siegedog.hava.derpygame.BasicSaveData;

public class SaveGameHelper<T> {

	public void writeSaveData(String saveFileName, T data) {
		FileHandle file = getSaveFile(saveFileName);
		Json json = new Json();
		json.toJson(data, data.getClass(), file);
	}

	private FileHandle getSaveFile(String saveFileName) {
		FileHandle dir = Gdx.files.external("lgdxsave/save");
		if (!dir.exists()) {
			System.out.println("Writing the folder structure");
			dir.mkdirs();
		}

		return Gdx.files.external("lgdxsave/save/" + saveFileName);
	}

	public T readSaveData(String saveFileName) {
		FileHandle file = getSaveFile(saveFileName);

		Json json = new Json();
		Object result = json.fromJson(BasicSaveData.class, file);
		T save = (T) result;
		return save;			
	}
}
