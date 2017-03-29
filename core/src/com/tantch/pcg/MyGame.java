package com.tantch.pcg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.mapgeneration.agents.ConnectorDAgent;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPNode;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPTree;
import com.tantch.pcg.mir.MIRTools;
import com.tantch.pcg.mir.ParametersMapping;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class MyGame {
	DunMap dmap;
	MIRTools mir;
	boolean musicPCG = false;
	private String selectedSong;

	public MyGame() {
		Debug.setVerbose(false);
		mir = new MIRTools();

	}

	public DunMap getDMap() {
		return dmap;
	}

	public void generateMap() {

		dmap = new DunMap(Settings.MAPSIZE);
		BSPTree tree = new BSPTree(dmap);
		BSPNode.setParameters(Settings.MINPARTITIONSIZE, Settings.MAXPARTITIONSIZE, Settings.MINROOMSIZE);
		tree.run();
		tree.createRooms();
		tree.buildMap();
		dmap.resetUnvisitedRooms();
		ArrayList<DunRoom> rooms = dmap.getRooms();
		Random rd = new Random();
		for (DunRoom dunRoom : rooms) {

			if (Settings.ROOMSIMPLECONNECTIONS && !dmap.isRoomUnvisited(dunRoom.getRoomId())) {
				System.out.println("skip room:" + dunRoom.getRoomId());
				continue;
			}

			ConnectorDAgent ag = new ConnectorDAgent();
			ag.init(dmap);
			ag.setMapSize(dmap.getSize());
			DunRoom room1 = dunRoom;
			DunRoom room2;
			if (rd.nextInt(100) < Settings.CONNECT_ONLY_TO_MIDDLE_ROOM) {
				room2 = dmap.getMiddleRoom();
			} else {
				room2 = dmap.getRandomUnvisitedRoom(room1);
			}
			dmap.markAsVisited(room2);
			int[] ipos = room1.getPositionInRoom();
			ag.setInitialPosition(ipos[0], ipos[1]);
			ag.setCurrentDirection(rd.nextInt(4));
			ag.setParameters(Settings.AGENT_TURNPROB);
			ag.setTarget(room2);
			ag.start();
		}
		dmap.perfectMap();

	}

	public void load() {
		Player player = new Player("Tantch");
		player.setDefaultStats();

		EvSearch es = new EvSearch();
		es.init(player);
		es.run(Settings.EA_ITERATIONS);
		player.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		dmap.loadPlayer(player);
		Debug.logPlayer(player);

		Monster mns = new Monster();
		mns.setStats(10, 10, 10, 10, 10, 10);

		es = new EvSearch();
		es.init(mns);
		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		dmap.loadMonster(mns);
	}

	public void loadMusic(String selectedSong) throws IOException, InterruptedException {
		System.out.println("Loading...");
		this.selectedSong = selectedSong;
		boolean res = mir.loadDescriptors(selectedSong);
		System.out.println(mir);

		if (res) {
			musicPCG = true;
			ParametersMapping.setMapSettings(mir);
			ParametersMapping.setAgentSettings(mir);
			ParametersMapping.setAmbient(mir);
		}
	}

	public String getSelectedSong() {
		return selectedSong;
	}

}
