package com.tantch.pcg.assets;

import java.util.Random;

import com.tantch.pcg.evolutionarysearch.EvRepresentation;
import com.tantch.pcg.utils.BitOperations;
import com.tantch.pcg.utils.Debug;

public class Item implements EvRepresentation {

	public enum ItemType {
		Meat, Boots, Gun, Shield, Helmet, HourGlass, Potato, Carrot

	}

	String description;
	int x, y;
	boolean trueItem;
	ItemType type;
	private int SEQSIZE = 6;
	private int effect;
	private int value;// 1,2

	public Item(String description) {

		this.description = description;

	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setTrueItem(boolean b) {
		trueItem = b;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getDescription() {
		return description;
	}

	public int getTrueItem() {
		if (trueItem) {
			return 1;

		}
		return 0;
	}

	@Override
	public double calculateFitness(int[] args) {
		if (args != null && args.length == 2) {
			this.trueItem = args[0] == 1;
			this.value = args[1];
		}
		double xtemp;
		double ytemp = 0.0;
		if (trueItem) {
			xtemp = (effect / value) / 4.0;
			ytemp += -1 * Math.pow((xtemp - 0.8), 2) + 1;
		} else {
			xtemp = ((8 - effect) / value) /4.0;
			ytemp += -1 * Math.pow((xtemp - 0.8) , 2) + 1;

		}
		Debug.log(this.getClass(), "fitness result -> " + xtemp + " | " + ytemp);

		return ytemp;
	}

	@Override
	public int getGeneSeqSize() {
		return SEQSIZE;
	}

	@Override
	public boolean[] getGeneSeq() {
		boolean[] seq = new boolean[SEQSIZE];

		boolean[] seqStr = BitOperations.intToBits(type.ordinal(), 3);

		for (int i = 0; i < seqStr.length; i++) {
			seq[i] = seqStr[i];
		}
		seqStr = BitOperations.intToBits(effect, 3);
		for (int i = 0; i < seqStr.length; i++) {
			seq[i + 3] = seqStr[i];
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
	public void loadFromGene(boolean[] seq) {
		String temp = "";
		for (int i = 0; i < seq.length; i++) {

			temp += seq[i] ? "1" : "0";

		}

		int ordinal = Integer.parseInt(temp.substring(0, 3), 2);

		type = ItemType.values()[ordinal];
		this.effect = Integer.parseInt(temp.substring(3, 6), 2) + 1;

	}

	public ItemType getType() {
		return type;
	}

	public int getEffect() {
		return effect;
	}

	public int getValue() {
		return value;
	}

}
