package com.tantch.pcg.mapgeneration.representations;

import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class DunMap {

	private MpCell[][] map;
	private ArrayList<DunRoom> rooms;
	private ArrayList<Integer> unvisitedRooms;
	private int size;

	public DunMap(int size) {
		this.size = size;
		rooms = new ArrayList<DunRoom>();
		unvisitedRooms = new ArrayList<Integer>();
		resetUnvisitedRooms();
		init();

	}

	private void init() {

		map = new MpCell[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				map[i][j] = new MpCell(CellType.FILLED);

			}
		}

	}

	public void resetUnvisitedRooms() {
		for (DunRoom droom : rooms) {
			unvisitedRooms.add(droom.getRoomId());
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
		// TODO Auto-generated method stub
		return size;
	}

	public MpCell[][] getMap() {
		// TODO Auto-generated method stub
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

	public DunRoom getRandomUnvisitedRoom() {

		Random rd = new Random();
		if (unvisitedRooms.size() != 0) {
			int id = rd.nextInt(unvisitedRooms.size());
			return rooms.get(id);
		}else{
			int id = rd.nextInt(rooms.size());
			return rooms.get(id);
		}

	}

	public void markAsVisited(DunRoom room2) {
		unvisitedRooms.remove(room2);
	}

}
