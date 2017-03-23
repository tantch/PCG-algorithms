package com.tantch.pcg.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.assets.Player;
import com.tantch.pcg.levelgeneration.BNFGrammar;
import com.tantch.pcg.levelgeneration.BNFNode;
import com.tantch.pcg.levelgeneration.BNFRule;

public class Debug {

	private static boolean verbose = true;

	public static void log(Class<?> cs, String msg) {
		if (verbose) {
			System.out.println(cs.getName() + " | " + msg);
		}
	}

	public static void setVerbose(boolean b) {
		verbose = b;
	}

	public static void printBitsequence(boolean[] seq) {
		for (int i = 0; i < seq.length; i++) {
			int temp = 0;
			if (seq[i]) {
				temp = 1;
			}
			System.out.print(temp);
		}
	}

	public static void logError(String type, String msg) {
		System.out.println("Error:" + type + " | " + msg);

	}

	public static void logMonster(Monster ms) {

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

	public static void logBNFGrammar() {
		if (!BNFGrammar.isInit()) {
			System.out.println("BNFGrammar | not init");
		}
		System.out.println("BNFGrammar:");
		BNFNode root = BNFGrammar.getS();
		printBNFNode(root);

		HashMap<String, BNFNode> map = BNFGrammar.getNodes();
		map.forEach((k, v) -> printBNFNode(v));

	}

	private static void printBNFNode(BNFNode nd) {

		System.out.println("Node : " + nd.getId());
		ArrayList<BNFRule> rules = nd.getRules();
		if (rules != null) {
			for (BNFRule bnfRule : rules) {
				System.out.println(bnfRule.getString());

			}
		}

	}

}
