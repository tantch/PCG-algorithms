package com.tantch.pcg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tantch.pcg.MyGdxGame;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class DMapScreen implements Screen {

	DunMap dmap;
	MyGdxGame game;
	Texture floor1;
	Texture floor2;
	Texture player;
	OrthographicCamera camera;
	int px, py;

	public DMapScreen(DunMap dmap, MyGdxGame game) {

		this.dmap = dmap;
		this.game = game;

		floor1 = new Texture("Floor1.png");
		floor2 = new Texture("Floor2.png");
		player = new Texture("Player.png");

		px = 0;
		py = 0;
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Keys.RIGHT) {
					px+=2;
				}else if(keycode == Keys.LEFT){
					px-=2;
				}else if(keycode == Keys.UP){
					py+=2;
				}else if(keycode == Keys.DOWN){
					py-=2;
				}

				return true;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20, 20 * (h / w));
		camera.position.set(px, py, 0);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {

		// handleinput
		camera.position.set(px, py, 0);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		drawMap();
		game.batch.end();

	}

	private void drawMap() {

		int cellSize = 2;

		MpCell[][] map = dmap.getMap();
		for (int i = 0; i < map.length; i++) {
			MpCell[] row = map[i];

			for (int j = 0; j < row.length; j++) {

				if (row[j].getType() == CellType.FILLED) {
					game.batch.draw(floor1, 0 + j * cellSize, 0 + i * cellSize, cellSize, cellSize);

				} else {
					game.batch.draw(floor2, 0 + j * cellSize, 0 + i * cellSize, cellSize, cellSize);

				}

			}

		}
		game.batch.draw(player, px, py + 1, 2, 2);

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = 20f;
		camera.viewportHeight = 20f * height / width;

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

	}

}
