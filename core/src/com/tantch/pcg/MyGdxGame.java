package com.tantch.pcg;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tantch.pcg.mapgeneration.agents.ConnectorDAgent;
import com.tantch.pcg.mapgeneration.cmd.Draw;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPNode;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPTree;
import com.tantch.pcg.screens.DMapScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	// Texture img;

	@Override
	public void create() {

		batch = new SpriteBatch();
		DunMap dmap = new DunMap(70);
		BSPTree tree = new BSPTree(dmap);
		BSPNode.setParameters(7, 15, 5);
		tree.run();
		tree.createRooms();
		tree.buildMap();

		ArrayList<DunRoom> rooms = dmap.getRooms();

		for (DunRoom dunRoom : rooms) {
			/*
			 * BlindDAgent ag = new BlindDAgent();
			 * 
			 * ag.init(dmap); ag.setMapSize(30); int[] pos =
			 * dunRoom.getPositionInRoom(); ag.setInitialPosition(pos[0],
			 * pos[1]); ag.setStamina(30); Random rd = new Random();
			 * ag.setCurrentDirection(rd.nextInt(4)); ag.setParameters(4, 0);
			 * ag.setStamina(10); ag.start();
			 */

			ConnectorDAgent ag = new ConnectorDAgent();
			ag.init(dmap);
			ag.setMapSize(dmap.getSize());
			DunRoom room1 = dunRoom;
			DunRoom room2 = dmap.getRandomUnvisitedRoom(room1);
			dmap.markAsVisited(room2);
			int[] ipos = room1.getPositionInRoom();
			ag.setInitialPosition(ipos[0], ipos[1]);
			Random rd = new Random();
			ag.setCurrentDirection(rd.nextInt(4));
			ag.setParameters(5);
			ag.setTarget(room2);
			ag.start();
		}
		Draw.drawMap(dmap, false, true);

		Draw.drawMap(dmap, false, false);
		DMapScreen screen = new DMapScreen(dmap, this);

		setScreen(screen);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
