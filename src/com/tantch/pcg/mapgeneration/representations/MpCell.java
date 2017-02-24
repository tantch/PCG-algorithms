package com.tantch.pcg.mapgeneration.representations;

public class MpCell {

	public enum CellType {
		EMPTY, FILLED
	}

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
	
}
