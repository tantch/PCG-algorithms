package com.tantch.pcg.evolutionarysearch.cmd;

import com.tantch.pcg.assets.Monster;

public class TestMonsterGeneration {
	public static void main(String[] args) {

		
		Monster ms = new Monster();
		ms.setStats(14, 14, 14, 10, 10, 10);
		ms.calculateFitness();
		
	}
}
