package com.tantch.pcg.mapgeneration.cmd;

import com.tantch.pcg.MyGame;

public class TestClient {

	public static void main(String[] args) {

		MyGame game = new MyGame();

		game.generateMap();
		
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);


	}

}
