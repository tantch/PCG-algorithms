package com.tantch.pcg.mir;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.parser.ParseException;

import com.tantch.pcg.utils.Settings;

public class MIRTester {

	public static void main(String[] args) throws IOException, InterruptedException, ParseException {

		File folder = new File("/home/pim/Music");

		File[] listOfFiles = folder.listFiles();
		try {
			PrintWriter writer = new PrintWriter("/home/pim/Desktop/music.csv", "UTF-8");
			writer.println(
					"Name;Acoustic;Aggressive;AvgLoudness;BeatCount;BpmG;Danceable;Dark;Electronic;GenreDor;GenreEle;GenreRos;GenreTza"
							+ ";Happy;Instrumental;Length;Pitch;Party;Relaxed;RhythmDance;Sad;Tonal;KeyScale;KeyKey;RoomSimpleConnections;MapSize;MinPartitionSize;MaxPartitionSize;MinRoomSize;ConnectOnlyToMiddleRoom;AgentTurnProb;Ea_Iterations;RGB;PLifeM;PAttackM,PArmorM;PSpeedM;PLuckM;PAtkSpeedM");
			
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File " + listOfFiles[i].getName());
					MIRTools mir = new MIRTools();
					mir.loadDescriptors(listOfFiles[i].getPath());
					ParametersMapping.setMapSettings(mir);
					ParametersMapping.setAgentSettings(mir);
					ParametersMapping.setAmbient(mir);
					ParametersMapping.setPlayerStatFocus(mir);
					ParametersMapping.setLevelParameters(mir);
					ParametersMapping.analiseGenreParameters(mir);
					writer.println(mir.getName() + ";" + mir.getAcoustic() + ";" + mir.getAggressive() + ";"
							+ mir.getAvgLoudness() + ";" + mir.getBeatCount() + ";" + mir.getBpmG() + ";"
							+ mir.getDanceable() + ";" + mir.getDark() + ";" + mir.getElectronic() + ";"
							+ mir.getGenreDor() + ";" + mir.getGenreEle() + ";" + mir.getGenreRos() + ";"
							+ mir.getGenreTza() + ";" + mir.getHappy() + ";" + mir.getInstrumental() + ";"
							+ mir.getLength() +";" + mir.getPitch() + ";" + mir.getParty() + ";" + mir.getRelaxed() + ";"
							+ mir.getRhythmDance() + ";" + mir.getSad() + ";" + mir.getTonal() + ";" + mir.getKeyScale() + ";" + mir.getKeyKey() + ";"
							+ Settings.ROOMSIMPLECONNECTIONS + ";" + Settings.MAPSIZE + ";" + Settings.MINPARTITIONSIZE
							+ ";" + Settings.MAXPARTITIONSIZE + ";" + Settings.MINROOMSIZE + ";" + Settings.CONNECT_ONLY_TO_MIDDLE_ROOM + ";"
							+ Settings.AGENT_TURNPROB + ";" + Settings.EA_ITERATIONS + ";" + Math.round(Settings.RGB[0]*255)+ "|" + Math.round(Settings.RGB[1]*255) +"|" + Math.round(Settings.RGB[2]*255) + ";" 
							+ Settings.PLAYER_LIFE_MULTIPLIER + ";" + Settings.PLAYER_ATTACK_MULTIPLIER + ";" + Settings.PLAYER_ARMOR_MULTIPLIER + ";" + Settings.PLAYER_SPEED_MULTIPLIER + ";" + Settings.PLAYER_LUCK_MULTIPLIER + ";" + Settings.PLAYER_ATKSPEED_MULTIPLIER);
				}
			}
			writer.close();
		} catch (IOException e) {
			// do something
		}

	}

}
