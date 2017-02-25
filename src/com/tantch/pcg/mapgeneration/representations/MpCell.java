package com.tantch.pcg.mapgeneration.representations;

import com.tantch.pcg.utils.Debug;

public class MpCell {

	public enum CellType {
		EMPTY,ROOM, FILLED
	}
	
	private int roomId=-1;
	
	private CellType type;
	private boolean[] debugDivisions = {false,false};
	
	public MpCell(CellType type) {

		this.type = type;
		
	}
	
	public void setDivisions(int i,boolean div){
		debugDivisions[i] = div;
	}

	public boolean[] getDivisions(){
		return debugDivisions;
	}
	
	public CellType getType(){
		return this.type;
	}
	
	public void setType(CellType type){
		this.type = type;
	}
	
	public void setRoomId(int id){
		if(type != CellType.ROOM){
			Debug.logError("MpCell", "Can't set room id of cell type != ROOM");
		}else{
			roomId= id;
		}
	}
	
	public int getRoomId(){
		return roomId;
	}
	
}
