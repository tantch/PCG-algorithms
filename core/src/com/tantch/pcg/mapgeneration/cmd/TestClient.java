package com.tantch.pcg.mapgeneration.cmd;

import java.util.ArrayList;
import java.util.Random;

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

		Debug.setVerbose(false);
		DunMap dmap = new DunMap(Settings.MAPSIZE);
		BSPTree tree = new BSPTree(dmap);
		BSPNode.setParameters(Settings.MINPARTITIONSIZE,Settings.MAXPARTITIONSIZE, Settings.MINROOMSIZE);
		tree.run();
		tree.createRooms();
		tree.buildMap();
		Draw.drawMap(dmap, false, false);

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
			ag.setParameters(Settings.AGENT_TURNPROB);
			ag.setTarget(room2);
			ag.start();
		}
		Draw.drawMap(dmap, false, true);

		//Draw.drawMap(dmap, false, false);
		dmap.perfectMap();
		Draw.drawMap(dmap, false, true);

	}

}
