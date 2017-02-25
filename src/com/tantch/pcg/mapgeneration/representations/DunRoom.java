package com.tantch.pcg.mapgeneration.representations;

import java.util.Random;

public class DunRoom {

	private int roomId;
	private int x1, y1, x2, y2;

	public DunRoom(int roomId, int x1, int y1, int x2, int y2) {
		this.roomId = roomId;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int[] getPositionInRoom() {
		Random rd = new Random();
		int[] ret = new int[2];
		ret[0] = rd.nextInt(x2 - x1) + x1;
		ret[1] = rd.nextInt(y2 - y1) + y1;
		return ret;
	}

}
