package com.tantch.pcg.mapgeneration.representations;

import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.assets.Item;
import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.utils.Settings;

public class DunRoom {

	public enum RoomType {
		NormalRoom, BossRoom, SimpleRoom, TreasureRoom, ExitRoom, StartingRoom, ZooRoom, TempleRoom, ShopRoom, TrapRoom, BombRoom
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
		int dif = 24;

		int size = 1;
		int snared = 0;
		int inactive = 0;

		EvSearch es = new EvSearch();
		// TODO add dificulty level
		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared, inactive });
		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*
		 * mns.setDif(dif); mns.setSize(size); mns.setSnared(snared ==1);
		 */
		/*
		 * int max = (int) (freeSpace * 0.75); Random rd = new Random();
		 */

		dmap.loadMonsters(mns, roomId, 1);

		roomType = RoomType.BossRoom;

		dmap.setRoomAsFilled(roomId);

	}

	public void setAsTreasureRoom(DunMap dmap) {
		Item it = new Item("Treasure");
		int trueItem = 1;
		int value = 2;

		EvSearch es = new EvSearch();

		es.init(it);
		es.setArgs(new int[] { trueItem, value });
		es.run(Settings.EA_ITERATIONS);

		it.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		System.out.println(it.getType());

		dmap.loadItem(it, roomId);

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
		int inactive = 0;

		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared, inactive });
		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*
		 * mns.setDif(dif); mns.setSize(size); mns.setSnared(snared ==1);
		 */
		int max = (int) (freeSpace * 0.55);
		Random rd = new Random();

		dmap.loadMonsters(mns, roomId, rd.nextInt(max));
		roomType = RoomType.ZooRoom;

		dmap.setRoomAsFilled(roomId);

	}

	public void setAsTempleRoom(DunMap dmap, boolean trueTemple) {

		Monster mns = new Monster();
		mns.setStats(1, 1, 1, 1, 1);
		int dif = 12;
		int size = 0;
		int snared = 0;
		int inactive = 1;
		EvSearch es = new EvSearch();

		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared, inactive });

		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*
		 * mns.setDif(dif); mns.setSize(size); mns.setSnared(snared ==1);
		 */
		Random rd = new Random();
		int monsnum = rd.nextInt(3) + 1;
		dmap.loadMonsters(mns, roomId, monsnum);

		Item it = new Item("temple treasure");
		int trueItem = 0;
		int value = 1;
		if (trueTemple) {
			trueItem = 1;
		}
		es = new EvSearch();

		es.init(it);
		es.setArgs(new int[] { trueItem, value });
		es.run(Settings.EA_ITERATIONS);

		it.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		System.out.println(it.getType());

		dmap.loadItem(it, roomId);

		roomType = RoomType.TempleRoom;

		dmap.setRoomAsFilled(roomId);

	}

	public void setAsShopRoom(DunMap dmap) {
		Monster mns = new Monster();
		mns.setStats(2, 2, 2, 2, 2);
		int dif = 30;
		int size = 0;
		int snared = 0;
		int inactive = 1;
		EvSearch es = new EvSearch();

		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared, inactive });

		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		roomType = RoomType.ShopRoom;
		dmap.setRoomAsFilled(roomId);
		dmap.loadMonsters(mns, roomId, 1);

		Item it = new Item("shop treasure 1");
		int trueItem = 1;
		int value = 1;

		es = new EvSearch();

		es.init(it);
		es.setArgs(new int[] { trueItem, value });
		es.run(Settings.EA_ITERATIONS);

		it.loadFromGene(es.getCurrentPopulation().get(0).getSeq());

		dmap.loadItem(it, roomId);

		Item it2 = new Item("shop treasure 2");
		trueItem = 1;
		value = 1;

		es = new EvSearch();

		es.init(it2);
		es.setArgs(new int[] { trueItem, value });
		es.run(Settings.EA_ITERATIONS);

		it2.loadFromGene(es.getCurrentPopulation().get(0).getSeq());

		dmap.loadItem(it2, roomId);

		Item it3 = new Item("shop treasure 3");
		trueItem = 1;
		value = 2;

		es = new EvSearch();

		es.init(it3);
		es.setArgs(new int[] { trueItem, value });
		es.run(Settings.EA_ITERATIONS);

		it3.loadFromGene(es.getCurrentPopulation().get(0).getSeq());

		dmap.loadItem(it3, roomId);

	}

	public void setAsBombRoom(DunMap dmap) {

		Monster mns = new Monster();
		mns.setStats(1, 1, 1, 1, 1);
		int dif = 12;
		int size = 0;
		int snared = 1;
		int inactive = 0;
		EvSearch es = new EvSearch();

		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared, inactive });

		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*
		 * mns.setDif(dif); mns.setSize(size); mns.setSnared(snared ==1);
		 */
		Random rd = new Random();

		int monsnum = rd.nextInt(Math.abs(x2 - x1)) + 1;
		roomType = RoomType.BombRoom;
		dmap.setRoomAsFilled(roomId);
		dmap.loadMonsters(mns, roomId, monsnum);

	}

	public void setAsTrapRoom(DunMap dmap) {
		Item it = new Item("Trap");
		int trueItem = 0;
		int value = 2;

		EvSearch es = new EvSearch();

		es.init(it);
		es.setArgs(new int[] { trueItem, value });
		es.run(Settings.EA_ITERATIONS);

		it.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		System.out.println(it.getType());

		dmap.loadItem(it, roomId);

		roomType = RoomType.TrapRoom;
		dmap.setRoomAsFilled(roomId);

	}

	public void setAsStartRoom(DunMap dmap) {
		// TODO Auto-generated method stub

		roomType = RoomType.StartingRoom;
		dmap.setStartRoom(roomId);
		dmap.setRoomAsFilled(roomId);

	}

	public void setAsSimpleRoom(DunMap dmap) {

		Monster mns = new Monster();
		mns.setStats(1, 1, 1, 1, 1);
		int dif = 10;
		int size = 0;
		int snared = 0;
		int inactive = 0;
		EvSearch es = new EvSearch();

		es = new EvSearch();
		es.init(mns);
		es.setArgs(new int[] { dif, size, snared, inactive });

		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		/*
		 * mns.setDif(dif); mns.setSize(size); mns.setSnared(snared ==1);
		 */
		Random rd = new Random();

		int assetsnum = (rd.nextInt(Math.abs(x2 - x1)) + 1) % 5;

		int itemsn = rd.nextInt(4) / 4;

		dmap.loadMonsters(mns, roomId, assetsnum - itemsn);

		if (itemsn == 1) {

			Item it = new Item("simple");
			int trueItem = rd.nextInt(2);
			int value = 1;

			es = new EvSearch();

			es.init(it);
			es.setArgs(new int[] { trueItem, value });
			es.run(Settings.EA_ITERATIONS);

			it.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
			System.out.println(it.getType());
			dmap.loadItem(it, roomId);
		}

		roomType = RoomType.SimpleRoom;
	}

}
