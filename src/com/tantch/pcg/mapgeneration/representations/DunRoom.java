package com.tantch.pcg.mapgeneration.representations;

import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Temple;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.utils.Settings;

public class DunRoom {

	public enum RoomType {
		NormalRoom, BossRoom, TreasureRoom, ExitRoom, StartingRoom, ZooRoom, TempleRoom
	}

	private ArrayList<int[]> emptySpaces;
	private int roomId;
	private int x1, y1, x2, y2;
	private int freeSpace;
	private RoomType roomType = RoomType.NormalRoom;

	public DunRoom(int roomId, int x1, int y1, int x2, int y2) {
		this.roomId = roomId;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		freeSpace = (Math.abs(x2 - x1) + 1) * (Math.abs(y2 - y1) + 1);
		emptySpaces = new ArrayList<>();
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				emptySpaces.add(new int[] { i, j });
			}

		}

	}

	public int[] getPositionInRoom(boolean ocup) {
		Random rd = new Random();
		int[] ret = new int[2];
		int res = rd.nextInt(emptySpaces.size());
		ret = emptySpaces.get(res);
		if (ocup) {
			emptySpaces.remove(res);
		}
		return ret;
	}

	public int getRoomId() {
		return roomId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setAsBossRoom(DunMap dmap) {
		Monster mns = new Monster();
		mns.setStats(1, 1, 1, 1, 1);
		int dif = 18;

		int size = 1;
		int snared = 0;
		EvSearch es = new EvSearch();
		// TODO add dificulty level
		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared });
		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*mns.setDif(dif);
		mns.setSize(size);
		mns.setSnared(snared ==1);*/
		/*
		 * int max = (int) (freeSpace * 0.75); Random rd = new Random();
		 */

		dmap.loadMonsters(mns, roomId, 1);

		roomType = RoomType.BossRoom;

		dmap.setRoomAsFilled(roomId);

	}

	public void setAsTreasureRoom(DunMap dmap) {
		int[] pos = getPositionInRoom(true);
		dmap.addTreasure(pos[0], pos[1]);
		roomType = RoomType.TreasureRoom;
		dmap.setRoomAsFilled(roomId);

	}

	public void setAsZooRoom(DunMap dmap) {
		Monster mns = new Monster();
		int dif = 8;
		mns.setStats(1, 1, 1, 1, 1);
		EvSearch es = new EvSearch();
		int size = -1;
		int snared = 0;
		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared });
		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*mns.setDif(dif);
		mns.setSize(size);
		mns.setSnared(snared ==1);*/
		int max = (int) (freeSpace * 0.55);
		Random rd = new Random();

		dmap.loadMonsters(mns, roomId, rd.nextInt(max));
		roomType = RoomType.ZooRoom;

		dmap.setRoomAsFilled(roomId);

	}

	public void setAsTempleRoom(DunMap dmap, boolean trueTemple) {

		Temple temple = new Temple(trueTemple);
		Monster mns = new Monster();
		mns.setStats(1, 1, 1, 1, 1);
		int dif = 12;
		int size = 0;
		int snared = 1;
		EvSearch es = new EvSearch();

		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared });

		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*mns.setDif(dif);
		mns.setSize(size);
		mns.setSnared(snared ==1);*/
		Random rd = new Random();
		int monsnum = rd.nextInt(3) + 1;
		temple.setGuardian(mns, monsnum);
		roomType = RoomType.TempleRoom;
		dmap.setRoomAsFilled(roomId);
		dmap.loadMonsters(mns, roomId, monsnum);

	}

	public void setAsShopRoom(DunMap dmap) {
		// TODO Auto-generated method stub

	}

	public void setAsBombRoom(DunMap dmap) {
		// TODO Auto-generated method stub

	}

	public void setAsTrapRoom(DunMap dmap) {
		// TODO Auto-generated method stub

	}

}
