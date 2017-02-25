package com.tantch.pcg.mapgeneration.agents;

import java.util.Random;

import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class DAgent {
	DunMap map;
	int posx, posy;
	int curdir;
	int stamina;

	public void init(DunMap map) {

		this.map = map;
		// TODO make all random
		posx = 30;
		posy = 30;
		curdir = 2;
		stamina = 300;
	}

	public void setInitialPosition(int x, int y) {
		this.posx = x;
		this.posy = y;
	}

	public void setCurrentDirection(int dir) {
		this.curdir = dir;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public void calculateStamina() {
		// TODO implemente
	}

	public void start() {

	}

	int getDirValue(boolean ver) {
		switch (this.curdir) {
		case 0:
			if (ver) {
				return 1;
			}
			return 0;
		case 1:
			if (ver) {
				return 0;
			}
			return 1;
		case 2:
			if (ver) {
				return -1;
			}
			return 0;
		case 3:
			if (ver) {
				return 0;
			}
			return -1;
		default:
			return 0;

		}
	}
}
