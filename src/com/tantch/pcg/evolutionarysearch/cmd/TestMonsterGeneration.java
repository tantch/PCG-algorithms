package com.tantch.pcg.evolutionarysearch.cmd;

import java.beans.EventSetDescriptor;
import java.util.ArrayList;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.evolutionarysearch.GeneSequence;
import com.tantch.pcg.utils.Debug;

public class TestMonsterGeneration {
	public static void main(String[] args) {
		Debug.setVerbose(false);
		Monster ms = new Monster();
		ms.setStats(10, 10, 10, 10, 10, 10);
		ms.calculateFitness();
		System.out.print("bits: ");
		Debug.printBitsequence(ms.getGeneSeq());
		System.out.println(" |");

		EvSearch es = new EvSearch();

		es.init(ms);
		es.run(60);

	}
}
