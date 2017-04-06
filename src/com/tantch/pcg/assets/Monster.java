package com.tantch.pcg.assets;

import java.util.Random;

import com.tantch.pcg.evolutionarysearch.EvRepresentation;
import com.tantch.pcg.utils.BitOperations;
import com.tantch.pcg.utils.Debug;

public class Monster implements EvRepresentation {

	// evolutionary variables
	private static int SEQSIZE = 30;

	// Base Stats
	private String name;
	private String typeName;
	// 0|10-20|30

	private int maxHealth;
	private int attack;
	private int attackSpeed;
	private int armor;
	private int speed;
	private int luck;

	// map
	private int mapX, mapY;

	private boolean dead=false;

	public void setPosition(int x, int y) {
		
		mapX = x;
		mapY = y;
		
	}

	public int[] getPosition() {
		int[] res = new int[2];
		res[0] = mapX;
		res[1] = mapY;
		return res;
	}

	private void reset() {
		name = "";
		typeName = "";
	}

	public void setStats(int health, int atk, int ats, int arm, int spd, int lck) {
		this.maxHealth = health;
		this.attack = atk;
		this.attackSpeed = ats;
		this.armor = arm;
		this.speed = spd;
		this.luck = lck;
	}

	@Override
	public double calculateFitness() {
		double statScore = calculateScore();
		double dificultyScore = 27;
		double xtemp = statScore / dificultyScore;
		double ytemp;

		if (xtemp < 0.65 || xtemp > 1.14) {
			ytemp = -Math.pow(xtemp - 0.9, 2);
		} else {
			ytemp = 1 - Math.pow(Math.tan(((xtemp - 0.9) * 10) / (Math.PI)), 2);
		}
		ytemp = ytemp * 10;

		Debug.log(this.getClass(), "fitness result for statsum:" + statScore + " -> " + xtemp + " | " + ytemp);

		return ytemp;
	}

	private double calculateScore() {

		return valueToScore(luck) + valueToScore(attackSpeed) + valueToScore(attack) + valueToScore(armor)
				+ valueToScore(maxHealth) + valueToScore(speed);

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
			return (int) (18 + Math.pow(val - 18, 2));
		}
	}

	@Override
	public boolean[] getGeneSeq() {

		boolean[] seq = new boolean[SEQSIZE];

		boolean[] seqStr = BitOperations.intToBits(maxHealth, 5);

		for (int i = 0; i < seqStr.length; i++) {
			seq[i] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(attack, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(attackSpeed, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 2] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(armor, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 3] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(speed, 5);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 4] = seqStr[i];
		}

		seqStr = BitOperations.intToBits(luck, 5);
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

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getAttack() {
		return attack;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public int getArmor() {
		return armor;
	}

	public int getSpeed() {
		return speed;
	}

	public int getLuck() {
		return luck;
	}

	public void loadFromGene(boolean[] seq) {
		this.reset();
		String temp = "";
		for (int i = 0; i < seq.length; i++) {

			temp += seq[i] ? "1" : "0";

		}

		maxHealth = Integer.parseInt(temp.substring(0, 5), 2);
		attack = Integer.parseInt(temp.substring(5, 10), 2);
		attackSpeed = Integer.parseInt(temp.substring(10, 15), 2);
		armor = Integer.parseInt(temp.substring(15, 20), 2);
		speed = Integer.parseInt(temp.substring(20, 25), 2);
		luck = Integer.parseInt(temp.substring(25, 30), 2);

	}

	public void die() {
		this.dead = true;
	}
	public boolean isDead(){
		return dead;
	}

}
