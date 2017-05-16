package com.tantch.pcg.assets;

public class Temple {
	
	private boolean trueTemple;
	private Monster guardian;
	private int numberGuardian;
	
	public Temple(boolean trueTemple){
		
		this.trueTemple = trueTemple;
	
	}
	public void setGuardian(Monster guardian,int num){
		this.guardian=guardian;
		this.numberGuardian = num;
	}

}
