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

				map[i][j] = new MpCell(CellType.FILLED, j, i);

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
		ArrayList<DunRoom> roomsClone = (ArrayList<DunRoom>) rooms.clone();
		ArrayList<Integer> unvisitedRoomsClone = (ArrayList<Integer>) unvisitedRooms.clone();
		roomsClone.remove(room1);
		unvisitedRoomsClone.remove(new Integer(room1.getRoomId()));
		if (unvisitedRoomsClone.size() != 0) {
			int id = rd.nextInt(unvisitedRoomsClone.size());
			return roomsClone.get(id);
		} else {
			int id = rd.nextInt(roomsClone.size());
			return roomsClone.get(id);
		}

	}

	public void markAsVisited(DunRoom room2) {
		unvisitedRooms.remove(room2);
	}

	public void perfectMap() {
		
		
		
		
		for (int i = 0; i < map.length; i++) {
			MpCell[] row = map[i];
			for (int j = 0; j < row.length; j++) {
				MpCell cell = row[j];

				cell.updateBelongtoRoom(this);
				
				
				
				
				//check if create new room
				if (cell.isSurroundedByRoomCells(this)) {
					System.out.println("Should create room in cell: " + j + "/" + i);
					for (int x = -1; x < 2; x++) {
						for (int y = -1; y < 2; y++) {
							map[i+y][j+x].setType(CellType.ROOM);
							map[i+y][j+x].setRoomId(rooms.size());
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

}
