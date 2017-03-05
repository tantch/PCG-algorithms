package com.tantch.pcg.utils;

import com.tantch.pcg.assets.Monster;

public class Debug {

	private static boolean verbose = true;

	public static void log(String type, String msg) {
		if (verbose) {
			System.out.println(type + " | " + msg);
		}
	}

	public static void setVerbose(boolean b) {
		verbose = b;
	}

	public static void printBitsequence(boolean[] seq) {
		for (int i = 0; i < seq.length; i++) {
			int temp = 0;
			if(seq[i]){
				temp=1;
			}
			System.out.print(temp);
		}
	}

	public static void logError(String type, String msg) {
		System.out.println("Error:" + type + " | " + msg);

	}

	public static void logMonster(Monster ms){
		
		System.out.println("Monster stats : ");
		System.out.println("Strength: " + ms.getStrength());
		System.out.println("Charisma: " + ms.getCharisma());
		System.out.println("Constitution: " + ms.getConstitution());
		System.out.println("Dexterity: " + ms.getDexterity());
		System.out.println("Intelligence: " + ms.getIntelligence());
		System.out.println("Wisdom: " + ms.getWisdom());
		
	}
	
}
