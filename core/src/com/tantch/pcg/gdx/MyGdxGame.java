package com.tantch.pcg.gdx;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tantch.pcg.MyGame;
import com.tantch.pcg.gdx.screens.DMapScreen;
import com.tantch.pcg.gdx.screens.SettingsMenuScreen;
import com.tantch.pcg.utils.Settings;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	// Texture img;
	public MyGame game;

	@Override
	public void create() {

		batch = new SpriteBatch();

		setScreen(new SettingsMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	public void loadMusic(String selectedSong) throws IOException, InterruptedException{
		game = new MyGame();
		game.loadMusic(selectedSong);
	}

	public void startGame()  {
		
		game.generateMap();
		game.load();
		game.generateLevel();
		DMapScreen screen = new DMapScreen(game.getDMap(), this);
		setScreen(screen);
	}
}
