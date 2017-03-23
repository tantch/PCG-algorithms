package com.tantch.pcg.mapgeneration.cmd;

import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.MyGame;
import com.tantch.pcg.mapgeneration.agents.BlindDAgent;
import com.tantch.pcg.mapgeneration.agents.ConnectorDAgent;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPNode;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPTree;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class TestClient {

	public static void main(String[] args) {

		MyGame game = new MyGame();

		game.generateMap();
		
		Draw.drawMap(game.getDMap(), false, true);
		Draw.drawMap(game.getDMap(), false, false);


	}

}
