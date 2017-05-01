package com.tantch.pcg.mapgeneration.agents;

import java.util.Random;

import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class ConnectorDAgent extends DAgent {

	private int xRem, yRem;
	private int turnProb;
	private int turnInc;
	
	//temp
	private int lastid;
	private int targetid;
	public void setTarget(DunRoom room) {
		this.targetid = room.getRoomId();
		int pos[] = room.getPositionInRoom(false);
		xRem = pos[0] - posx;
		yRem = pos[1] - posy;
		//temp
		
	}
	
	@Override
	public void setInitialPosition(int x, int y) {
		// TODO Auto-generated method stub
		super.setInitialPosition(x, y);
		
		lastid =map.getRoomId(x, y);
	}

	public void start() {

		Random rd = new Random();
		changeDirection();

		do {
			if ((curdir % 2) == 0 && yRem == 0) {// if walking vert
				changeDirection();
				turnProb = 0;
			} else if (xRem == 0) {
				changeDirection();
				turnProb = 0;
			}

			updatePosition();

			if (xRem == 0 && yRem == 0) {
				continue;
			}

			if (posx >= mapSize) {
				posx = mapSize - 1;
				changeDirection();
				turnProb = 0;
				continue;
			} else if (posx < 0) {
				posx = 0;
				changeDirection();
				turnProb = 0;
				continue;
			} else if (posy >= mapSize) {
				posy = mapSize - 1;
				changeDirection();
				turnProb = 0;
				continue;
			} else if (posy < 0) {
				posy = 0;
				changeDirection();
				turnProb = 0;
				continue;
			}
			
			int turn = rd.nextInt(100);
			if (turn < turnProb) {
				changeDirection();
				turnProb = 0;
			} else {
				turnProb += turnInc;
			}

			if (map.getCellType(posx, posy) == CellType.FILLED) {
				map.setCellType(posx, posy, CellType.EMPTY);
			}

		} while (xRem != 0 || yRem != 0);

	}

	private void updatePosition() {
		posx += getDirValue(false);
		posy += getDirValue(true);
		xRem -= getDirValue(false);
		yRem -= getDirValue(true);
		
		int newid= map.getRoomId(posx, posy);
		if(newid >=0 && lastid!=newid && targetid != newid){
			lastid=newid;
			map.markAsVisited(map.getRooms().get(newid));
		}
		
	}

	private void changeDirection() {

		if ((curdir % 2) == 0) {// if walking vert
			if (xRem > 0) {
				curdir = 1;
			} else if (xRem < 0) {
				curdir = 3;
			} else {
				curdir = 1;
				changeDirection();
			}

			// curdir = rd.nextInt(2) * 2 + 1;
		} else {
			if (yRem > 0) {
				curdir = 0;
			} else if (yRem < 0) {
				curdir = 2;
			} else {
				curdir = 0;
				changeDirection();
			}

			// curdir = rd.nextInt(2) * 2;
		}

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

	public void setParameters(int turnProb) {
		this.turnProb = turnProb;
		this.turnInc = turnProb;
	}

}
