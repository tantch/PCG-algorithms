package com.tantch.pcg.evolutionarysearch.cmd;

import com.tantch.pcg.assets.Item;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class TestItemGeneration {
	public static void main(String[] args) {
		Debug.setVerbose(true);
		Item it = new Item("test");
		int trueItem = 0;
		int value=1; 

		EvSearch es = new EvSearch();

		es.init(it);
		es.setArgs(new int[] {trueItem,value});
		es.run(Settings.EA_ITERATIONS);

		Item reslt = new Item("result");
		reslt.loadFromGene(es.getCurrentPopulation().get(0).getSeq());
		System.out.println("Final Fitness: " + reslt.calculateFitness(new int[]{trueItem,value}));

		System.out.println("item: " + reslt.getDescription());
		if(reslt.getTrueItem()==1){ 
			System.out.println("True items");
		}else{
			System.out.println("Fake items");
		}
		System.out.println("type: " + reslt.getType());
		System.out.println("effect: " + reslt.getEffect());
		System.out.println("value: " + reslt.getValue());
	}
}
