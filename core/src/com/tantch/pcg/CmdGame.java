package com.tantch.pcg;

import java.io.IOException;

import com.tantch.pcg.mapgeneration.cmd.Draw;

public class CmdGame {

	public static void main(String[] args) throws IOException, InterruptedException {

		MyGame game = new MyGame();
		game.loadMusic("/home/pim/Music/dancing-music.mp3");
		game.generateMap();
		game.load();
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);

	}

}
