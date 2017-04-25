package com.tantch.pcg.mapgeneration.representations;

import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.assets.Treasure;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class DunMap {

	private MpCell[][] map;
	private ArrayList<DunRoom> rooms;
	private ArrayList<Integer> unvisitedRooms;
	private ArrayList<Integer> emptyRooms;

	private Player player;
	private ArrayList<Monster> mons;
	private Treasure treasure;
	private int size;
	private int middleRoom = -1;

	public DunMap(int size) {
		this.size = size;
		rooms = new ArrayList<DunRoom>();
		unvisitedRooms = new ArrayList<Integer>();
		emptyRooms = new ArrayList<Integer>();

		mons = new ArrayList<Monster>();
		init();

	}

	public void loadPlayer(Player player) {
		this.player = player;

		int[] pos = rooms.get(0).getPositionInRoom();
		player.setPosition(pos[0], pos[1]);

	}

	public void loadMonster(Monster mos, int roomId) {
		int[] pos = rooms.get(roomId).getPositionInRoom();
		mos.setPosition(pos[0], pos[1]);
		mons.add(mos);

	}

	private void init() {

		map = new MpCell[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				map[i][j] = new MpCell(CellType.FILLED, j, i);

			}
		}

	}

	public void resetUnvisitedRooms() {
		for (DunRoom droom : rooms) {
			unvisitedRooms.add(droom.getRoomId());
		}
	}

	public void resetEmptyRooms() {
		for (DunRoom droom : rooms) {
			emptyRooms.add(droom.getRoomId());
		}
	}

	public CellType getCellType(int row, int col) {
		return map[col][row].getType();
	}

	public void setCellType(int row, int col, CellType type) {
		map[col][row].setType(type);
	}

	public void setCellDivisions(int row, int col, int i, boolean div) {
		map[col][row].setDivisions(i, div);

	}

	public void setCellRoomId(int row, int col, int roomId) {
		map[col][row].setRoomId(roomId);

	}

	public int getSize() {
		return size;
	}

	public MpCell[][] getMap() {
		return map;
	}

	public int addRom(int x1, int y1, int x2, int y2) {
		int roomId = rooms.size();
		rooms.add(new DunRoom(roomId, x1, y1, x2, y2));
		return roomId;
	}

	public ArrayList<DunRoom> getRooms() {
		return rooms;
	}

	public DunRoom getRandomUnvisitedRoom(DunRoom room1) {

		Random rd = new Random();

		if (unvisitedRooms.size() != 0) {
			int id = rd.nextInt(unvisitedRooms.size());
			return rooms.get(unvisitedRooms.get(id));
		} else {
			return null;
		}

	}

	public void markAsVisited(DunRoom room2) {

		if (unvisitedRooms.contains(room2.getRoomId())) {
			unvisitedRooms.remove(new Integer(room2.getRoomId()));

		}
	}

	public void perfectMap() {

		for (int i = 0; i < map.length; i++) {
			MpCell[] row = map[i];
			for (int j = 0; j < row.length; j++) {
				MpCell cell = row[j];

				cell.updateBelongtoRoom(this);

				// check if create new room
				if (cell.isSurroundedByRoomCells(this)) {
					System.out.println("Should create room in cell: " + j + "/" + i);
					for (int x = -1; x < 2; x++) {
						for (int y = -1; y < 2; y++) {
							map[i + y][j + x].setType(CellType.ROOM);
							map[i + y][j + x].setRoomId(rooms.size());
						}
					}

					addRom(j - 1, i - 1, j + 1, i + 1);

				}

			}
		}

	}

	public int getRoomId(int x, int y) {
		return map[y][x].getRoomId();

	}

	public Player getPlayer() {
		return player;
	}

	public void movePlayerTo(int x, int y) {
		if (x >= size || x < 0 || y < 0 || y >= size) {
			return;
		}
		if (map[y][x].getType() != CellType.FILLED) {
			player.setPosition(x, y);
		}
	}

	public ArrayList<Monster> getMoster() {
		return mons;
	}

	public boolean isRoomUnvisited(int id) {
		return unvisitedRooms.contains(id);
	}

	public DunRoom getMiddleRoom() {
		if (middleRoom == -1) {
			for (int x = size / 2; x < size; x++) {
				for (int y = size / 2; y < size; y++) {
					int r = getRoomId(x, y);
					if (r >= 0) {
						System.out.println(" Middle room is: " + r);
						middleRoom = r;
						return rooms.get(r);
					}
				}
			}
			return rooms.get(0);
		} else {
			return rooms.get(middleRoom);
		}
	}

	public DunRoom getEmptyRoom() {

		if (emptyRooms == null || emptyRooms.size() == 0) {
			resetEmptyRooms();
		}
		Random rd = new Random();

		int id = rd.nextInt(emptyRooms.size());
		return rooms.get(emptyRooms.get(id));

	}

	/*
	 * public void addMonster(int x, int y) { System.out.println("MONSTER IN " +
	 * x + "|" + y); mons.setPosition(x, y); map[y][x].monsterInCell(mons); }
	 */

	public void addTreasure(int i, int j) {
		this.treasure = new Treasure(i, j);
	}

	public Treasure getTreasure() {
		return treasure;
	}

	public void setRoomAsFilled(int roomId) {
		emptyRooms.remove(new Integer(roomId));
	}

}
