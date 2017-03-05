package com.tantch.pcg.mapgeneration.agents;

import java.util.Random;

import com.tantch.pcg.mapgeneration.representations.DunRoom;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;
import com.tantch.pcg.utils.Debug;

public class ConnectorDAgent extends DAgent {

	private int roomId;
	private int xRem, yRem;
	private int turnProb;
	private int turnInc;

	public void setTarget(DunRoom room) {

		roomId = room.getRoomId();
		int pos[] = room.getPositionInRoom();
		xRem = pos[0] - posx;
		yRem = pos[1] - posy;
	}

	public void start() {

		Random rd = new Random();
		do {
			Debug.log("ConcDAgent", "X:" + posx + " Y:" + posy);
			Debug.log("ConcDAgent", "Xrem:" + xRem + " Yrem:" + yRem);
			Debug.log("ConcDAgent", "curDir:" + curdir + "turnProb:" + turnProb);
			posx += getDirValue(false);
			posy += getDirValue(true);
			xRem -= getDirValue(false);
			yRem -= getDirValue(true);
			if (posx >= mapSize) {
				posx = mapSize - 1;
				changeDirection(rd);
				turnProb = 0;
				continue;
			} else if (posx < 0) {
				posx = 0;
				
				changeDirection(rd);
				turnProb = 0;
				continue;

			} else if (posy >= mapSize) {
				posy = mapSize - 1;
				changeDirection(rd);
				turnProb = 0;
				continue;
			} else if (posy < 0) {
				posy = 0;
				changeDirection(rd);
				turnProb = 0;
				continue;

			}
			int turn = rd.nextInt(100);
			if (turn < turnProb) {
				changeDirection(rd);
				turnProb = 0;
			} else {
				turnProb += turnInc;
			}
			
			if (map.getCellType(posx, posy) == CellType.FILLED) {
				map.setCellType(posx, posy, CellType.EMPTY);
			}

		} while (xRem !=0 || yRem !=0);

	}

	private void changeDirection(Random rd) {
		
		if((curdir % 2) == 0){
			//change to hor
			curdir = rd.nextInt(2) * 2 + 1;
		}else{
			curdir = rd.nextInt(2) * 2;
		}
		Debug.log("ConnectorDAgent","direction changed to :" + curdir);

	}

	int getDirValue(boolean ver) {

		int horV;
		int verV;
		if (xRem > 0) {
			horV = 1;
		} else {
			horV = -1;
		}
		if (yRem > 0) {
			verV = 1;
		} else {
			verV = -1;
		}

		switch (this.curdir) {
		case 0:
			if (ver) {
				return verV;
			}
			return 0;
		case 1:
			if (ver) {
				return 0;
			}
			return horV;
		case 2:
			if (ver) {
				return verV;
			}
			return 0;
		case 3:
			if (ver) {
				return 0;
			}
			return horV;
		default:
			return 0;

		}
	}

	public void setParameters(int turnProb) {
		this.turnProb = turnProb;
		this.turnInc = turnProb;
	}

}
