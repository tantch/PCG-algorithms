package com.tantch.pcg.evolutionarysearch;

import java.util.ArrayList;

import com.tantch.pcg.levelgeneration.BNFGrammar;
import com.tantch.pcg.levelgeneration.BNFNode;

public class BNFSeq {

	private int[] seq;
	private float fitnessValue;
	private ArrayList<BNFNode> res;

	public BNFSeq(int[] seq) {
		this.seq = seq;
	}

	public void setSeq(int[] seq) {
		this.seq = seq;
	}

	public void calculateFitnessValue() {

		res = BNFGrammar.getResult(seq);

		/*
		 * for(int i=0;i< res.size();i++){
		 * 
		 * 
		 * 
		 * }
		 */
		float x = res.size() / 5f;
		fitnessValue = (float) (-1 * Math.pow(x - 1, 2) + 1);

	}

	public float getFitnessValue() {
		return fitnessValue;
	}

	@Override
	public String toString() {
		return "V:" + fitnessValue;
	}

	public int[] getSeq() {
		return seq;
	}
}
