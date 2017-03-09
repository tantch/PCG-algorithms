package com.tantch.pcg.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {

	private DMapScreen screen;

	public GameInputProcessor(DMapScreen dMapScreen) {
		this.screen = dMapScreen;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.RIGHT) {
			screen.right=true;
		} else if (keycode == Keys.LEFT) {
			screen.left=true;
		} else if (keycode == Keys.UP) {
			screen.up=true;
		} else if (keycode == Keys.DOWN) {
			screen.down=true;
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.RIGHT) {
			screen.right=false;
		} else if (keycode == Keys.LEFT) {
			screen.left=false;
		} else if (keycode == Keys.UP) {
			screen.up=false;
		} else if (keycode == Keys.DOWN) {
			screen.down=false;
		}

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

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
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		screen.camerazoom += amount;
		screen.camera.viewportWidth = screen.camerazoom;
		screen.camera.viewportHeight = screen.camerazoom * h / w;
		screen.camera.update();
		return true;
	}

}
