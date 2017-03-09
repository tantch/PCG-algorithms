package com.tantch.pcg.utils;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;

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
		System.out.println("Strength: " + ms.getMaxHealth());
		System.out.println("Charisma: " + ms.getLuck());
		System.out.println("Constitution: " + ms.getAttackSpeed());
		System.out.println("Dexterity: " + ms.getAttack());
		System.out.println("Intelligence: " + ms.getArmor());
		System.out.println("Wisdom: " + ms.getSpeed());
		
	}

	public static void logPlayer(Player pl) {
		System.out.println("Player stats : ");
		System.out.println("MaxLife: " + pl.getMaxLife());
		System.out.println("Attack: " + pl.getAttack());
		System.out.println("Armor: " + pl.getArmor());
		System.out.println("Speed: " + pl.getSpeed());
		System.out.println("Luck: " + pl.getLuck());
		System.out.println("Attack Speed: " + pl.getAtkSpeed());
		
	}
	
}
