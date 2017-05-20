package com.tantch.pcg.evolutionarysearch.cmd;

import com.tantch.pcg.assets.Player;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class TestPlayer {
	public static void main(String[] args) {

		
		
		Debug.setVerbose(true);
		Player ps = new Player("pim");
		ps.setDefaultStats();
		System.out.println("Score:" + ps.calculateFitness(null));
		System.out.print("bits: ");
		Debug.printBitsequence(ps.getGeneSeq());
		System.out.println(" |");
		EvSearch es = new EvSearch();
	
		es.init(ps);
		es.run(Settings.EA_ITERATIONS);
		Player reslt = new Player("pim");
		reslt.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		Debug.logPlayer(reslt);
		Debug.printBitsequence(es.getCurrentPopulation().get(0).getSeq());
		System.out.println("Fitness:" + es.getCurrentPopulation().get(0).getFitnessValue());
	}
}
