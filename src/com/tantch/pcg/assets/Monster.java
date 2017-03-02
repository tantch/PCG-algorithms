package com.tantch.pcg.assets;

import java.util.Random;

import com.tantch.pcg.evolutionarysearch.EvRepresentation;
import com.tantch.pcg.utils.BitOperations;
import com.tantch.pcg.utils.Debug;

public class Monster implements EvRepresentation {

	// evolutionary variables
	private static int SEQSIZE = 30;

	// Base Stats
	String name;
	String typeName;
	// 0|10-20|30

	int strength;
	int dexterity;
	int constitution;
	int intelligence;
	int wisdom;
	int charisma;

	public Monster() {

	}

	public Monster(boolean[] seq) {

		String temp = "";
		for (int i = 0; i < seq.length; i++) {

			temp += seq[i] ? "1" : "0";

		}
		
		
		strength = Integer.parseInt(temp.substring(0, 5), 2);
		dexterity = Integer.parseInt(temp.substring(5, 10), 2);
		constitution = Integer.parseInt(temp.substring(10, 15), 2);
		intelligence = Integer.parseInt(temp.substring(15, 20), 2);
		wisdom = Integer.parseInt(temp.substring(20, 25), 2);
		charisma = Integer.parseInt(temp.substring(25, 30), 2);


	}

	public void setStats(int str, int dex, int con, int ite, int wis, int cha) {
		this.strength = str;
		this.dexterity = dex;
		this.constitution = con;
		this.intelligence = ite;
		this.wisdom = wis;
		this.charisma = cha;
	}

	@Override
	public double calculateFitness() {
		int res = 0;
		double statScore = calculateScore();
		double dificultyScore = 27;
		double xtemp = statScore / dificultyScore;
		double ytemp;

		if (xtemp < 0.65 || xtemp > 1.14) {
			ytemp = - Math.pow(xtemp-0.9, 2);
		} else {
			ytemp = 1 - Math.pow(Math.tan(((xtemp - 0.9) * 10) / (Math.PI)), 2);
			ytemp = ytemp*10;
		}
		Debug.log("Monster", "fitness result for statsum:" + statScore + " -> " + xtemp + " | " + ytemp);

		return ytemp;
	}

	private double calculateScore() {

		return valueToScore(charisma) + valueToScore(constitution) + valueToScore(dexterity)
				+ valueToScore(intelligence) + valueToScore(strength) + valueToScore(wisdom);

	}

	private int valueToScore(int val) {

		if (val < 14) {
			return (val - 8);
		} else if (val < 17) {
			return ((val - 13) * 2) + 5;
		} else if (val == 17) {
			return 14;
		} else if (val == 18) {
			return 18;
		} else {
			return 50;
		}
	}

	@Override
	public boolean[] getGeneSeq() {

		boolean[] seq = new boolean[SEQSIZE];

		boolean[] seqStr = BitOperations.intToBits(strength, 5);

		for (int i = 0; i < seqStr.length; i++) {
			seq[i] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(dexterity, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(constitution, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 2] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(intelligence, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 3] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(wisdom, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 4] = seqStr[i];
		}

		seqStr = BitOperations.intToBits(charisma, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 5] = seqStr[i];
		}

		return seq;
	}

	@Override
	public boolean[] generateRandomGeneSeq() {
		boolean[] seq = new boolean[SEQSIZE];
		Random rd = new Random();
		for (int i = 0; i < SEQSIZE; i++) {
			seq[i] = rd.nextInt(2) != 0;
		}

		return seq;
	}

	@Override
	public int getGeneSeqSize() {
		return SEQSIZE;
	}

	public String getName() {
		return name;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getStrength() {
		return strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getWisdom() {
		return wisdom;
	}

	public int getCharisma() {
		return charisma;
	}

}
