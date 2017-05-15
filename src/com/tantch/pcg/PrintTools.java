package com.tantch.pcg;

import com.tantch.pcg.mir.MIRTools;
import com.tantch.pcg.utils.Settings;

public class PrintTools {

	public static String prettyPrintGeneratedContent(MyGame game) {
		
		String result = "";
		MIRTools mir = game.getMir();
 
		result += "Song name: " + mir.getName() + "\n";
		result += "\t Length : " + mir.getLength() + "\n";
		result += "Music descriptors extracted:" + "\n";
		result += "\t Acoustic: " + mir.getAcoustic() + "\n";
		result += "\t Aggressiveness: " + mir.getAggressive() + "\n";
		result += "\t Loudness: " + mir.getAvgLoudness() + "\n";
		result += "\t Beats per minute: " + mir.getBpmG() + "\n";
		result += "\t Danceable: " + mir.getDanceable() + "\n";
		result += "\t Dark: " + mir.getDark() + "\n";
		result += "\t Electronic: " + mir.getElectronic() + "\n";
		result += "\t Dor Genre: " + mir.getGenreDor() + "\n";
		result += "\t Ele Genre: " + mir.getGenreEle() + "\n";
		result += "\t Ros Genre: " + mir.getGenreRos()+ "\n";
		result += "\t Tza Genre: " + mir.getGenreTza() + "\n";
		result += "\t Happy: " + mir.getHappy() + "\n";
		result += "\t Instrumental: " + mir.getInstrumental() + "\n";
		result += "\t Key : " + mir.getKeyKey() + "\n";
		result += "\t Key Scale: " + mir.getKeyScale() + "\n";
		result += "\t Male : " + mir.getMale() + "\n";
		result += "\t Party : " + mir.getParty() + "\n";
		result += "\t Pitch : " + mir.getPitch() + "\n";
		result += "\t Relaxed : " + mir.getRelaxed() + "\n";
		result += "\t Dance Rhythm : " + mir.getRhythmDance() + "\n";
		result += "\t Sad : " + mir.getSad() + "\n";
		result += "\t Tonal : " + mir.getTonal() + "\n";

		result += "\n\n";
		result += "Created " + game.getDMap().getSize() + "x" + game.getDMap().getSize() + " map with " + game.getDMap().getRooms().size() + " rooms\n";
		result += "\t space was partitioned with dimensions between " + Settings.MINPARTITIONSIZE + " and " + Settings.MAXPARTITIONSIZE + " and the minimum room size was established as " + Settings.MINROOMSIZE + "\n";
		
		if(Settings.ROOMSIMPLECONNECTIONS){
			result += "\t rooms only connect to unconnected rooms\n";
		}else{
			result += "\t middle room was selected to be room " + game.getDMap().getMiddleRoom().getRoomId() + ". Agents connecting the rooms start at the middle room and have a " + Settings.CONNECT_ONLY_TO_MIDDLE_ROOM + "% chance of after connecting the room to an empty one starting again from the same instead of continuing in the new connected room\n ";
			
		}
		result += "\t Agents always walk the right amount of x and y  but have a " + Settings.AGENT_TURNPROB + "% chance of changing direction (from vertical to horizontal\n";
		
		
		result +="The Player was created with " + Settings.EA_ITERATIONS + " iterations and resulted in the following stats: \n";
		result +="\t Life Points " + game.getDMap().getPlayer().getCurLife() + " ,due to a life multiplier of " + Settings.PLAYER_LIFE_MULTIPLIER + "\n";
		result +="\t Attack: " + game.getDMap().getPlayer().getAttack()+ " ,due to an attack multiplier of " + Settings.PLAYER_ATTACK_MULTIPLIER + "\n";
		result +="\t Speed: " + game.getDMap().getPlayer().getSpeed()+ " ,due to a speed multiplier of " + Settings.PLAYER_SPEED_MULTIPLIER + "\n";
		result +="\t Armor: " + game.getDMap().getPlayer().getArmor()+ " ,due to an armor multiplier of " + Settings.PLAYER_ARMOR_MULTIPLIER + "\n";
		result +="\t Attack Speed: " + game.getDMap().getPlayer().getAtkSpeed()+ " ,due to an attack speed multiplier of " + Settings.PLAYER_ATKSPEED_MULTIPLIER + "\n";
		result +="\t Luck: " + game.getDMap().getPlayer().getLuck()+ " ,due to a luck multiplier of " + Settings.PLAYER_LUCK_MULTIPLIER + "\n";

		

		return result;
	}

}
