package com.tantch.pcg.mapgeneration.representations;

import java.util.Random;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.utils.Settings;

public class DunRoom {

	
	public enum RoomType{
		NormalRoom,BossRoom,TreasureRoom,ExitRoom,StartingRoom
	}
	
	private int roomId;
	private int x1, y1, x2, y2;
	private RoomType roomType = RoomType.NormalRoom;
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

	public int getRoomId() {
		return roomId;
	}
	
	public RoomType getRoomType(){
		return roomType;
	}

	public void setAsBossRoom(DunMap dmap) {
		Monster mns = new Monster();
		mns.setStats(10, 10, 10, 10, 10, 10);
		EvSearch es = new EvSearch();

		es = new EvSearch();
		es.init(mns);
		es.run(Settings.EA_ITERATIONS);
		mns.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		dmap.loadMonster(mns,roomId);

		
		roomType = RoomType.BossRoom;
		
		
		
	}

	public void setAsTreasureRoom(DunMap dmap){
		int[] pos = getPositionInRoom();
		dmap.addTreasure(pos[0], pos[1]);
		roomType = RoomType.TreasureRoom;
		
		
	}

}
