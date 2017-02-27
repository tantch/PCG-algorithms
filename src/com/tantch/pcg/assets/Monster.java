package com.tantch.pcg.assets;

import com.tantch.pcg.evolutionarysearch.EvRepresentation;
import com.tantch.pcg.utils.Debug;

public class Monster implements EvRepresentation {

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
		double xtemp = statScore/ dificultyScore;
		double ytemp;

		if (xtemp < 0.65 || xtemp > 1.14) {
			ytemp = -1;
		} else {
			ytemp = 1 - Math.pow(Math.tan(((xtemp - 0.9) * 10) / (Math.PI )), 2);
		}
		Debug.log("Monster", "fitness result for statsum:" + statScore + " -> " + xtemp + " | " + ytemp);

		return res;
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

}
