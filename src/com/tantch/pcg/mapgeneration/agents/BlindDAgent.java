package com.tantch.pcg.mapgeneration.agents;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.tantch.pcg.mapgeneration.representations.*;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class BlindDAgent extends DAgent {

	private DunMap map;

	private int turnPb, turnInc;
	private int roomPb, roomInc;


	public void init(DunMap map) {

		this.map = map;
		posx = 30;
		posy = 30;
		curdir = 2;
		turnPb = 5;
		roomPb = 0;
		stamina = 300;
	}

	public void setParameters(int turnProb, int createRoomProb) {
		this.turnPb = turnProb;
		this.turnInc = turnInc;
		this.roomPb = createRoomProb;
		this.roomInc = createRoomProb;
	}

	public void start() {
		Random rd = new Random();
		while (stamina > 0) {

			posx += getDirValue(false);
			posy += getDirValue(true);
			if (posx >= mapSize) {
				posx = mapSize-1;
				curdir = rd.nextInt(4);
				turnPb = 0;
				continue;
			} else if (posx < 0) {
				posx = 0;
				curdir = rd.nextInt(4);
				turnPb = 0;
				continue;

			} else if (posy >= mapSize) {
				posy = mapSize-1;
				curdir = rd.nextInt(4);
				turnPb = 0;
				continue;
			} else if (posy < 0) {
				posy = 0;
				curdir = rd.nextInt(4);
				turnPb = 0;
				continue;

			}
			int turn = rd.nextInt(100);
			if (turn < turnPb) {
				curdir = rd.nextInt(4);
				turnPb = 0;
			} else {
				turnPb += turnInc;
			}
			// int room = rd.nextInt(100);
			// if (room < roomPb) {
			// // no rooms in borders comapre to chosen size
			//
			// roomPb = 0;
			//
			// } else {
			// roomPb += roomInc;
			// }
			if (map.getCellType(posx, posy) == CellType.FILLED) {
				stamina--;
				map.setCellType(posx, posy, CellType.EMPTY);

			}

		}

	}



}
