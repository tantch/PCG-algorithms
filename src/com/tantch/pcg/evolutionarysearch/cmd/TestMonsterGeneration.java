package com.tantch.pcg.evolutionarysearch.cmd;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class TestMonsterGeneration {
	public static void main(String[] args) {
		Debug.setVerbose(false);
		Monster ms = new Monster();
		ms.setStats(1,1,1,1,1);
		ms.calculateFitness(new int[]{12,0,0});
		System.out.print("bits: ");
		Debug.printBitsequence(ms.getGeneSeq());
		System.out.println(" |");

		EvSearch es = new EvSearch();

		es.init(ms);
		es.run(Settings.EA_ITERATIONS);
		Monster reslt = new Monster();
		reslt.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		Debug.logMonster(reslt);

	}
}
