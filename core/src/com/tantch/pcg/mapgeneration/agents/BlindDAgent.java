package com.tantch.pcg.mapgeneration.agents;

import java.util.Random;

import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class BlindDAgent extends DAgent {

	private DunMap map;

	private int turnPb, turnInc;


	public void init(DunMap map) {

		this.map = map;
		posx = 30;
		posy = 30;
		curdir = 2;
		turnPb = 5;
		stamina = 300;
	}

	public void setParameters(int turnProb) {
		this.turnPb = turnProb;
		this.turnInc = turnProb;

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

			if (map.getCellType(posx, posy) == CellType.FILLED) {
				stamina--;
				map.setCellType(posx, posy, CellType.EMPTY);

			}

		}

	}



}
