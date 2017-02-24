package com.tantch.pcg.mapgeneration.agents;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.tantch.pcg.mapgeneration.representations.*;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class BlindAgent {

	private DunMap map;
	private int posx, posy;
	private int curdir;
	private int turnPb,turnInc;
	private int roomPb,roomInc;
	private int stamina;

	public void init(DunMap map) {

		this.map = map;
		//TODO make all random
		posx = 30;
		posy = 30;
		curdir = 2;
		turnPb = 5;
		roomPb = 0;
		stamina =300;
	}
	
	public void setInitialPosition(int x,int y){
		this.posx=x;
		this.posy=y;
	}
	public void setCurrentDirection(int dir){
		this.curdir= dir;
	}
	public void setParameters(int turnProb, int createRoomProb){
		this.turnPb = turnProb;
		this.turnInc = turnInc;
		this.roomPb = createRoomProb;
		this.roomInc = createRoomProb;
	}
	public void setStamina(int stamina){
		this.stamina=stamina;
	}
	
	public void calculateStamina(){
		//TODO implemente
	}
	

	public void start(){
		Random rd = new Random();
		while (stamina > 0) {

			posx += getDirValue(false);
			posy += getDirValue(true);
			if (posx >= 30) {
				posx = 29;
				curdir = rd.nextInt(4);
				turnPb = 0;
				continue;
			} else if (posx < 0) {
				posx = 0;
				curdir = rd.nextInt(4);
				turnPb = 0;
				continue;

			} else if (posy >= 30) {
				posy = 29;
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
			int room = rd.nextInt(100);
			if (room < roomPb) {
				// no rooms in borders comapre to chosen size
				if (posx >= 29 || posx < 1 || posy >= 29 || posy < 1) {
					// no room
				} else {

					map.setCellType(posx + 1, posy + 1, CellType.EMPTY);
					map.setCellType(posx + 1, posy, CellType.EMPTY);
					map.setCellType(posx + 1, posy - 1, CellType.EMPTY);
					map.setCellType(posx, posy + 1, CellType.EMPTY);
					map.setCellType(posx, posy - 1, CellType.EMPTY);
					map.setCellType(posx - 1, posy + 1, CellType.EMPTY);
					map.setCellType(posx - 1, posy, CellType.EMPTY);
					map.setCellType(posx - 1, posy - 1, CellType.EMPTY);

					roomPb = 0;
				}
			} else {
				roomPb += roomInc;
			}
			if (map.getCellType(posx, posy) == CellType.FILLED) {
				stamina--;
			}
			map.setCellType(posx, posy, CellType.EMPTY);

		}
		

	}

	private int getDirValue(boolean ver) {
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
