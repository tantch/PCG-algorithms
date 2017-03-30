package com.tantch.pcg.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tantch.pcg.gdx.MyGdxGame;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class DMapScreen implements Screen {

	public DunMap dmap;
	private MyGdxGame game;
	private Texture floor1;
	private Texture floor2;
	private Texture roomFloor;
	private Texture player;
	private Texture monster;
	private Music music;
	public OrthographicCamera camera;
	public boolean right = false, left = false, up = false, down = false;
	private int sprtn = 1;
	private float acm = 0;
	public float camerazoom = 20;

	public DMapScreen(DunMap dmap, MyGdxGame game) {

		this.dmap = dmap;
		this.game = game;

		floor1 = new Texture("Floor1.png");
		floor2 = new Texture("Floor2.png");
		roomFloor = new Texture("Floor_Room.png");
		player = new Texture("Player.png");
		monster = new Texture("Monster.png");

		Gdx.input.setInputProcessor(new GameInputProcessor(this));
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, camerazoom, camerazoom * (h / w));
		int[] pos = dmap.getPlayer().getPosition();
		camera.position.set(pos[0], pos[1], 0);
		
		music = Gdx.audio.newMusic(Gdx.files.absolute(game.game.getSelectedSong()));
		music.play();
		music.setLooping(true);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {

		acm += delta;
		if (acm > 1 / 12f) {
			acm = 0;
			sprtn++;
			if (sprtn > 2) {
				sprtn = 1;
			}
		}

		handleInput(delta);
		int[] pos = dmap.getPlayer().getPosition();

		camera.position.set(pos[0] * 2, pos[1] * 2, 0);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		drawMap();
		game.batch.end();

	}

	private void handleInput(float delta) {
		int[] pos = dmap.getPlayer().getPosition();
		if (dmap.getPlayer().canMove(delta)) {

			if (right) {
				dmap.movePlayerTo(pos[0] + 1, pos[1]);
			} else if (left) {
				dmap.movePlayerTo(pos[0] - 1, pos[1]);
			} else if (up) {
				dmap.movePlayerTo(pos[0], pos[1] + 1);
			} else if (down) {
				dmap.movePlayerTo(pos[0], pos[1] - 1);
			}
		}

	}

	private void drawMap() {

		int cellSize = 2;

		MpCell[][] map = dmap.getMap();
		for (int i = 0; i < map.length; i++) {
			MpCell[] row = map[i];

			for (int j = 0; j < row.length; j++) {

				if (row[j].getType() == CellType.FILLED) {
					game.batch.draw(floor1, 0 + j * cellSize, 0 + i * cellSize,
							cellSize, cellSize);
				} else if (row[j].getType() == CellType.ROOM) {
					game.batch.draw(roomFloor, 0 + j * cellSize, 0 + i * cellSize,
							cellSize, cellSize);
				} else {
					game.batch.draw(floor2, 0 + j * cellSize, 0 + i * cellSize,
							cellSize, cellSize);

				}
			}

		}
		int[] pos = dmap.getMoster().getPosition();
		game.batch.draw(new TextureRegion(monster, 0, 0, 320, 320), pos[0] * 2, pos[1] * 2 + 1, 2, 2);
		pos = dmap.getPlayer().getPosition();
		game.batch.draw(new TextureRegion(player, 0, 0, 320, 320), pos[0] * 2, pos[1] * 2 + 1, 2, 2);

	}


	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = camerazoom;
		camera.viewportHeight = camerazoom * height / width;

		camera.update();
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
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		music.dispose();
	}

}
