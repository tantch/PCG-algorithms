package com.tantch.pcg.utils;

import com.tantch.pcg.mir.MIRTools;

public class ParametersMapping {

	public static void setMapSettings(MIRTools mir) {
		Settings.MAPSIZE = (int) mir.getLength() / 4;
		int beatsSpeed = (int) (7- mir.getBpmG()/60); 
		
		Settings.MINROOMSIZE = beatsSpeed;
		Settings.MINPARTITIONSIZE =(int) (beatsSpeed * 1.5);
		Settings.MAXPARTITIONSIZE = beatsSpeed * 3;
		
		
	}
	
	public static void setAgentSettings(MIRTools mir){
		
		int res=0;
		float party =mir.getParty();
		float relaxed =mir.getRelaxed();
		
		res = (int) (party*30 - relaxed*20);
		
		if(res < 1){
			res = 1;
		}
		
		
		Settings.AGENT_TURNPROB = res;
		
		
		
		
		Settings.CONNECT_ONLY_TO_MIDDLE_ROOM = (int) (mir.getSad()* 100);
	}

}
