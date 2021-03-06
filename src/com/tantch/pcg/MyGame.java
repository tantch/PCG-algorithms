package com.tantch.pcg;

import java.io.IOException;
import java.util.Random;

import org.json.simple.parser.ParseException;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.levelgeneration.BNFGrammar;
import com.tantch.pcg.levelgeneration.LevelGenerator;
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
	private DunMap dmap;
	private MIRTools mir;
	private String selectedSong;

	public MyGame() {
		Debug.setVerbose(false);
		mir = new MIRTools();

	}

	public DunMap getDMap() {
		return dmap;
	}
	public MIRTools getMir(){
		return mir;
	}

	public void generateMap() {

		dmap = new DunMap(Settings.MAPSIZE);
		BSPTree tree = new BSPTree(dmap);
		BSPNode.setParameters(Settings.MINPARTITIONSIZE, Settings.MAXPARTITIONSIZE, Settings.MINROOMSIZE);
		tree.run();
		tree.createRooms();
		tree.buildMap();
		dmap.resetUnvisitedRooms();
		Random rd = new Random();
		DunRoom room = dmap.getMiddleRoom();
		if (room == null) {
			room = dmap.getRooms().get(0);
		}
		dmap.markAsVisited(room);
		while (room != null) {
			System.out.println("order:" + room.getRoomId());

			room = connectRooms(room, rd);

		}
		dmap.perfectMap();

	}

	private DunRoom connectRooms(DunRoom room1, Random rd) {

		ConnectorDAgent ag = new ConnectorDAgent();
		ag.init(dmap);
		ag.setMapSize(dmap.getSize());
		DunRoom room2 = dmap.getRandomUnvisitedRoom(room1);
		if (room2 == null) {
			return room2;
		}

		dmap.markAsVisited(room2);
		int[] ipos = room1.getPositionInRoom(false);
		ag.setInitialPosition(ipos[0], ipos[1]);
		ag.setCurrentDirection(rd.nextInt(4));
		ag.setParameters(Settings.AGENT_TURNPROB);
		ag.setTarget(room2);
		ag.start();
		if (Settings.ROOMSIMPLECONNECTIONS) {
			return room2;
		}

		if (rd.nextInt(100) < Settings.CONNECT_ONLY_TO_MIDDLE_ROOM) {
			return room1;
		}
		return room2;

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

		

	}

	public void loadMusic(String selectedSong) throws IOException, InterruptedException, ParseException {
		System.out.println("Loading...");
		this.selectedSong = selectedSong;
		boolean res = mir.loadDescriptors(selectedSong);
		System.out.println(mir);

		if (res) {
			ParametersMapping.setMapSettings(mir);
			ParametersMapping.setAgentSettings(mir);
			ParametersMapping.setAmbient(mir);
			ParametersMapping.setPlayerStatFocus(mir);
			ParametersMapping.setLevelParameters(mir);
			ParametersMapping.analiseGenreParameters(mir);
		}
	}

	public void generateLevel() {

		LevelGenerator lev = new LevelGenerator(dmap.getRooms().size());
		lev.run();
		BNFGrammar.loadToDunMap(dmap, lev.getBestCandidate());
		dmap.fillEmptyRooms();

	}

	public String getSelectedSong() {
		return selectedSong;
	}

}
