package com.tantch.pcg.evolutionarysearch;

import java.util.ArrayList;

import com.tantch.pcg.levelgeneration.BNFGrammar;
import com.tantch.pcg.levelgeneration.BNFNode;
import com.tantch.pcg.utils.Settings;

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

	public void calculateFitnessValue(float roomCnt) {

		res = BNFGrammar.getResult(seq);
		//variables count
		
		int agr = 0;
		int conf = 0;
		int fun = 0;
		int bambzl = 0;
		int calm = 0;
		int good=0;
		for(int i=0;i< res.size();i++){
			BNFNode node = res.get(i);
			
			switch (node.getId()) {
			case "Zoo":
				conf++;
				fun++;
				break;
				
			case "Temple":
				calm++;
				break;
			case "FakeTemple":
				bambzl++;
				calm++;
				break;
			case "Shop":
				good++;
				break;
			case "Bombs":
				agr++;
				fun++;
				break;
			case "Trap":
				agr++;
				conf++;
			default:
				break;
			}
			
		}
		
		float x = (res.size()) / ((roomCnt*3f)/4f);
		float sizeValue = (float) (-1 * Math.pow(x - 1, 2) + 1);
		
		float room10p = roomCnt *0.1f;
		float agrScore = (float) (-1 * Math.pow((agr/roomCnt) - 1, 2) + 1)*0.17f * Settings.LEVEL_AGR_VALUE;  
		float confScore = (float) (-1 * Math.pow((conf/roomCnt) - 1, 2) + 1)*0.17f * Settings.LEVEL_CONFUSE_VALUE;  
		float funScore = (float) (-1 * Math.pow((fun/roomCnt) - 1, 2) + 1)*0.17f * Settings.LEVEL_FUN_VALUE;  
		float bambScore = (float) (-1 * Math.pow((bambzl/roomCnt) - 1, 2) + 1)*0.17f * Settings.LEVEL_TRICK_VALUE;  
		float calmScore = (float) (-1 * Math.pow((calm/roomCnt) - 1, 2) + 1)*0.17f * Settings.LEVEL_CALM_VALUE;  
		float goodScore = (float) (-1 * Math.pow((good/roomCnt) - 1, 2) + 1)*0.17f * Settings.LEVEL_POSITIVE_VALUE;  

		fitnessValue = sizeValue + agrScore + confScore + funScore +  bambScore + calmScore + goodScore;
		
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

	public ArrayList<BNFNode> getResult() {
		return res;
	}
}
