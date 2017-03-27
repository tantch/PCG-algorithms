package com.tantch.pcg.mir;

import com.tantch.pcg.utils.Settings;

public class ParametersMapping {
	private static int BASEBPM=60;

	public static void setMapSettings(MIRTools mir) {
		Settings.MAPSIZE = (int) mir.getLength() / 4;
		int beatsSpeed = (int) (6- mir.getBpmG()/BASEBPM); 
		
		Settings.MINROOMSIZE = beatsSpeed;
		Settings.MINPARTITIONSIZE =(int) (beatsSpeed * 1.5);
		Settings.MAXPARTITIONSIZE = beatsSpeed * 3;
		
		
	}
	
	public static void setAgentSettings(MIRTools mir){
		
		int res=0;
		float aggressive =mir.getAggressive();
		float relaxed =mir.getRelaxed();
		
		res = (int) (aggressive*30 - relaxed*20);
		
		if(res < 1){
			res = 1;
		}
		
		
		Settings.AGENT_TURNPROB = res;
		
		
		
		
		Settings.CONNECT_ONLY_TO_MIDDLE_ROOM = (int) (mir.getSad()* 100);
	}
	
	public static void setPlayerStatFocus(MIRTools mir){
		
		float loudnessValue= mir.getAvgLoudness();
		float aggressiveValue = mir.getAggressive();	
		
		float speedP = 0;  
	}

}
