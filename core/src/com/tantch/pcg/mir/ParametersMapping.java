package com.tantch.pcg.mir;

import com.badlogic.gdx.graphics.Color;
import com.tantch.pcg.utils.Settings;

public class ParametersMapping {
	private static int BPMLB = 80;
	private static int BPMUB = 200;

	private static int LENGTHLB = 60;
	private static int LENGTHUB = 300;

	public static void setMapSettings(MIRTools mir) {

		float sizex = (mir.getLength() - LENGTHLB) / (LENGTHUB - LENGTHLB);
		if (sizex < 0) {
			sizex = 0;
		} else if (sizex > 1) {
			sizex = 1;
		}
		Settings.MAPSIZE = (int) (Math.pow(sizex + 0.3, 2) * 25 + 30);
		float beatsx = 1 - ((mir.getBpmG() - BPMLB) / (BPMUB - BPMLB));

		Settings.MINROOMSIZE = (int) (Math.pow(beatsx, 2) * 2 + 3);
		Settings.MINPARTITIONSIZE = (int) (Math.pow(Settings.MINROOMSIZE, 2) / 2);
		Settings.MAXPARTITIONSIZE = Settings.MINPARTITIONSIZE * 2 + 1;
	}

	public static void setAgentSettings(MIRTools mir) {

		int res = 0;
		float aggressive = mir.getAggressive();
		float relaxed = mir.getRelaxed();

		res = (int) (aggressive * 25 - relaxed * 20);

		if (res < 1) {
			res = 1;
		}

		Settings.AGENT_TURNPROB = res;

		Settings.CONNECT_ONLY_TO_MIDDLE_ROOM = (int) (mir.getSad() * 100);
	}

	public static void setAmbient(MIRTools mir) {

		float light = ((1 - mir.getDark())*0.6f + 0.4f);
		
		Settings.RGB[0] = mir.getAggressive() / 2f * light + 0.5f;
		Settings.RGB[1] = mir.getDanceable() / 2f * light + 0.5f;
		Settings.RGB[2] = mir.getSad() / 2f * light + 0.5f;

	}

	public static void setPlayerStatFocus(MIRTools mir) {

		float loudnessValue = mir.getAvgLoudness();
		float aggressiveValue = mir.getAggressive();

		int bpm = (int) mir.getBpmG();
		if(bpm < 80){
			Settings.PLAYER_SPEED_MULTIPLIER = 0;
		}else if( bpm < 120){
			Settings.PLAYER_SPEED_MULTIPLIER = 1;

		}else if( bpm < 168){
			Settings.PLAYER_SPEED_MULTIPLIER = 2;

		}else if( bpm < 200){
			Settings.PLAYER_SPEED_MULTIPLIER = 3;

		}else{
			Settings.PLAYER_SPEED_MULTIPLIER = 4;

		}
		
		
		Settings.PLAYER_LIFE_MULTIPLIER = (int) (Math.pow(loudnessValue,4)*4);

	}

}
