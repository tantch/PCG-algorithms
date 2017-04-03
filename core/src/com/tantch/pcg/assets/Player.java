package com.tantch.pcg.assets;

import java.util.Random;

import com.tantch.pcg.evolutionarysearch.EvRepresentation;
import com.tantch.pcg.utils.BitOperations;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class Player implements EvRepresentation {

	private static final int SEQSIZE = 18;

	private String name;

	private int maxLife;
	private int curLife;

	private int attack;
	private int armor;
	private int speed;
	private int luck;
	private int atkSpeed;

	// map
	private int mapX = 0;
	private int mapY = 0;
	private float timePassed = 0;

	public Player(String name) {
		this.name = name;
		setDefaultStats();
	}

	public void setPosition(int x, int y) {
		mapX = x;
		mapY = y;
		timePassed = 0;
	}

	public void setDefaultStats() {
		this.maxLife = 2;
		this.curLife = 2;
		this.attack = 1;
		this.armor = 1;
		this.speed = 1;
		this.luck = 1;
		this.atkSpeed = 1;
	}

	public int[] getPosition() {
		int[] res = new int[2];
		res[0] = mapX;
		res[1] = mapY;
		return res;
	}

	@Override
	public double calculateFitness() {
		double score = calculateStatsScore();
		double dif = 16;
		double xtemp = score / dif;
		double ytemp;

		ytemp = -1 * Math.pow((xtemp - 0.8) * 10, 2) + 1;

		ytemp += 0.1 * ((Settings.PLAYER_SPEED_MULTIPLIER - 1) / 2f) * (speed-1);
		ytemp += 0.1 * ((Settings.PLAYER_LIFE_MULTIPLIER - 1) / 2f) * (maxLife-1);
		ytemp += 0.1 * ((Settings.PLAYER_ATTACK_MULTIPLIER - 1) / 2f) * (attack-1);
		ytemp += 0.1 * ((Settings.PLAYER_ARMOR_MULTIPLIER - 1) / 2f) * armor;
		ytemp += 0.1 * ((Settings.PLAYER_LUCK_MULTIPLIER - 1) / 2f) * luck;
		ytemp += 0.1 * ((Settings.PLAYER_ATKSPEED_MULTIPLIER - 1) / 2f) * (atkSpeed -1);


		Debug.log(this.getClass(), "fitness result for statsum:" + score + " -> " + xtemp + " | " + ytemp);

		return ytemp;
	}

	private int calculateStatsScore() {
		int tScore = 0;

		int tmpScore = (int) Math.floor(Math.pow(maxLife -1, 2));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(attack - 1, 2));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(armor, 3));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(speed - 1, 2));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(luck + 1, 3));
		tScore += tmpScore;
		tmpScore = (int) Math.floor(Math.pow(atkSpeed - 1, 3));
		tScore += tmpScore;
		return tScore;
	}

	@Override
	public int getGeneSeqSize() {
		return SEQSIZE;
	}

	@Override
	public boolean[] getGeneSeq() {
		boolean[] seq = new boolean[SEQSIZE];

		boolean[] seqStr = BitOperations.intToBits(maxLife -1 , 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(attack - 1, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 3] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(armor, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 3 * 2] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(speed - 1, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 3 * 3] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(luck, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 3 * 4] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(atkSpeed - 1, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 3 * 5] = seqStr[i];
		}

		return seq;
	}

	public String getName() {
		return name;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public int getCurLife() {
		return curLife;
	}

	public int getAttack() {
		return attack;
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

	public int getAtkSpeed() {
		return atkSpeed;
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
	public void loadFromGene(boolean[] seq) {
		String temp = "";
		for (int i = 0; i < seq.length; i++) {

			temp += seq[i] ? "1" : "0";

		}

		maxLife = Integer.parseInt(temp.substring(0, 3), 2) +1;
		attack = Integer.parseInt(temp.substring(3, 6), 2) + 1;
		armor = Integer.parseInt(temp.substring(6, 9), 2);
		speed = Integer.parseInt(temp.substring(9, 12), 2) + 1;
		luck = Integer.parseInt(temp.substring(12, 15), 2);
		atkSpeed = Integer.parseInt(temp.substring(15, 18), 2) + 1;

	}

	public boolean canMove(float delta) {
		timePassed += delta;
		return timePassed > 1f / (speed);
		

	}

}
