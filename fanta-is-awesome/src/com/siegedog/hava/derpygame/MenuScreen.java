package com.siegedog.hava.derpygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.siegedog.hava.engine.FancyGame;

public class MenuScreen implements Screen {

	FancyGame game;
	Skin skin;
	Stage stage;
	Table layout;
	
	SpriteBatch batch;
	public MenuScreen(final FancyGame game) {
		this.game = game;
		batch = new SpriteBatch();
		FileHandle fh = new FileHandle("data/ui/uiskin.json");
		skin = new Skin(fh, new FileHandle("data/ui/uiskin.png"));
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		Gdx.input.setInputProcessor(stage);

		layout = new Table(skin);
		layout.width = 150f;
		layout.align(Align.CENTER);
		layout.pack();
		
		final Button begin = new TextButton("Start vidaygaym",
				skin.getStyle(TextButtonStyle.class), "button-begin");
		layout.add(begin);
		layout.row();
		
		begin.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				game.pushScreen(new GameplayScreen(game));
			}
		});
		
		final Button about = new TextButton("About", skin.getStyle(TextButtonStyle.class), "button-about");
		layout.add(about);
		layout.row();
		
		final Button quit = new TextButton("Quit game", skin.getStyle(TextButtonStyle.class), "button-quit");
		layout.add(quit);
		layout.row();
		
		final Label aboutText = new Label("Heya!\nWe are two awesome game developers who spent like half the contest time with pointless shit and made a 10%-working game that features penis images as placeholders.", skin.getStyle(LabelStyle.class), "about-box");
		layout.add(aboutText);
		aboutText.x = 100f;
		aboutText.y = 100f;
		
		aboutText.setWrap(true);
		aboutText.layout();
		aboutText.pack();
		aboutText.visible = false;
		
		about.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				aboutText.visible = true;			
			}
		});
		
		quit.setClickListener(new ClickListener() {
			
			@Override
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();				
			}
		});
		
		layout.x = Gdx.graphics.getWidth()/2;
		layout.y = Gdx.graphics.getHeight()/2 + 100f;
		
		stage.addActor(layout);
		}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// ((Label)stage.findActor("label")).setText("fps: " +
		// Gdx.graphics.getFramesPerSecond());

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
