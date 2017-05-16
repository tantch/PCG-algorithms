package com.tantch.pcg.mir;

import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;
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

		float light = ((1 - mir.getDark()) * 0.2f + 0.8f);

		Settings.RGB[0] = mir.getAggressive()  * light ;
		Settings.RGB[1] = mir.getDanceable()  * light ;
		Settings.RGB[2] = mir.getSad()* light;

	}

	public static void setPlayerStatFocus(MIRTools mir) {

		float loudnessValue = mir.getAvgLoudness();
		float aggressiveValue = mir.getAggressive();
		float relaxedValue = mir.getRelaxed();
		float sadValue = mir.getSad();
		float happyValue = mir.getHappy();
		float instrumentalValue = mir.getInstrumental();
		float partyValue = mir.getParty();

		int bpm = (int) mir.getBpmG();
		if (bpm < 80) {
			Settings.PLAYER_SPEED_MULTIPLIER = 0;
		} else if (bpm < 120) {
			Settings.PLAYER_SPEED_MULTIPLIER = 1;

		} else if (bpm < 168) {
			Settings.PLAYER_SPEED_MULTIPLIER = 2;

		} else if (bpm < 200) {
			Settings.PLAYER_SPEED_MULTIPLIER = 3;

		} else {
			Settings.PLAYER_SPEED_MULTIPLIER = 4;

		}

		float at = aggressiveValue - (relaxedValue*0.3f);

		if (at < 0) {
			at = 0;
		}

		float ar = (float) (sadValue - Math.pow(happyValue,2) + 0.2f);

		if (ar < 0) {
			ar = 0;
		}

		Settings.PLAYER_LIFE_MULTIPLIER = (int) (Math.pow(loudnessValue, 2) * 4);

		Settings.PLAYER_ATTACK_MULTIPLIER = (int) (Math.pow(at, 2) * 4);

		Settings.PLAYER_ARMOR_MULTIPLIER = (int) (Math.pow(ar, 2) * 4);

		Settings.PLAYER_LUCK_MULTIPLIER = (int) (Math.pow(instrumentalValue * 0.4f + partyValue * 0.4f + 0.2, 2) * 4);

		String rhythm = mir.getRhythmDance();

		switch (rhythm) {
		case "ChaChaCha":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 3;
			break;
		case "Jive":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 1;
			break;
		case "Quickstep":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 3;
			break;
		case "Rumba-American":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 2;
			break;
		case "Rumba-International":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 1;
			break;
		case "Rumba-Misc":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 1;
			break;
		case "Samba":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 2;
			break;
		case "Tango":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 1;
			break;
		case "VienneseWaltz":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 2;
			break;
		case "Waltz":
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 1;
			break;
		default:
			Settings.PLAYER_ATKSPEED_MULTIPLIER = 1;
			break;
		}

	}
	
	public static void setLevelParameters(MIRTools mir){
		
		float aggressiveValue = mir.getAggressive();
		float partyValue = mir.getParty();
		float happyValue = mir.getHappy();

		Settings.LEVEL_AGR_VALUE = aggressiveValue;
		Settings.LEVEL_FUN_VALUE = partyValue;
		Settings.LEVEL_POSITIVE_VALUE = happyValue;
		Settings.LEVEL_TRICK_VALUE = mir.getElectronic();
		Settings.LEVEL_CALM_VALUE = 1f- mir.getAggressive();
		Settings.LEVEL_CONFUSE_VALUE = mir.getDanceable();
		
	}

}
