package com.tantch.pcg;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.tantch.pcg.mapgeneration.cmd.Draw;

public class CmdGame {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		MyGame game = new MyGame();
		game.loadMusic("/home/pim/Music/Rush.mp3");
		game.generateMap();
		game.load();
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);

	}

}
