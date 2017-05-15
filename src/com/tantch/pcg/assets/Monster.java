package com.tantch.pcg.assets;

import java.util.Random;

import com.tantch.pcg.evolutionarysearch.EvRepresentation;
import com.tantch.pcg.utils.BitOperations;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class Monster implements EvRepresentation {

	// evolutionary variables
	private static int SEQSIZE = 18;

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

	public Monster(Monster mos){
		
		this.name = mos.name;
		this.typeName = mos.typeName;
		this.maxHealth = mos.maxHealth;
		this.attack = mos.attack;
		this.attackSpeed = mos.attackSpeed;
		this.armor = mos.armor;
		this.speed = mos.speed;
		this.luck = mos.luck;
	}	
	

	public Monster() {
		// TODO Auto-generated constructor stub
	}


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
		double score = calculateStatsScore();
		double dif = 16;
		double xtemp = score / dif;
		double ytemp;

		ytemp = -1 * Math.pow((xtemp - 0.8) * 10, 2) + 1;

		Debug.log(this.getClass(), "fitness result for statsum:" + score + " -> " + xtemp + " | " + ytemp);

		return ytemp;
	}

	private int calculateStatsScore() {
		int tScore = 0;

		int tmpScore = (int) Math.floor(Math.pow(maxHealth - 1, 2));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(attack - 1, 2));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(armor, 3));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(speed - 1, 2));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(luck + 1, 3));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(attackSpeed - 1, 3));
		tScore += tmpScore;
		return tScore;

	}

	@Override
	public boolean[] getGeneSeq() {

		boolean[] seq = new boolean[SEQSIZE];

		boolean[] seqStr = BitOperations.intToBits(maxHealth, 3);

		for (int i = 0; i < seqStr.length; i++) {
			seq[i] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(attack, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(attackSpeed, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 2] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(armor, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 3] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(speed, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 5 * 4] = seqStr[i];
		}

		seqStr = BitOperations.intToBits(luck, 3);
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

		maxHealth = Integer.parseInt(temp.substring(0, 3), 2) + 1;
		attack = Integer.parseInt(temp.substring(3, 6), 2) + 1;
		armor = Integer.parseInt(temp.substring(6, 9), 2);
		speed = Integer.parseInt(temp.substring(9, 12), 2) + 1;
		luck = Integer.parseInt(temp.substring(12, 15), 2);
		attackSpeed = Integer.parseInt(temp.substring(15, 18), 2) + 1;

	}

	


}
