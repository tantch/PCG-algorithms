package com.tantch.pcg.gdx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tantch.pcg.MyGame;
import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.gdx.screens.DMapScreen;
import com.tantch.pcg.gdx.screens.SettingsMenuScreen;
import com.tantch.pcg.mapgeneration.agents.ConnectorDAgent;
import com.tantch.pcg.mapgeneration.cmd.Draw;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPNode;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPTree;
import com.tantch.pcg.utils.Debug;
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

	public void startGame(String selectedSong) throws IOException, InterruptedException {
		game = new MyGame();
		game.loadMusic(selectedSong);
		game.generateMap();
		game.load();
		DMapScreen screen = new DMapScreen(game.getDMap(), this);
		setScreen(screen);
	}
}
