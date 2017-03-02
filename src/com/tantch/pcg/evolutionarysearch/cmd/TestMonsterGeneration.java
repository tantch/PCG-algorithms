package com.tantch.pcg.evolutionarysearch.cmd;

import java.beans.EventSetDescriptor;
import java.util.ArrayList;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.evolutionarysearch.EvSearch;
import com.tantch.pcg.evolutionarysearch.GeneSequence;
import com.tantch.pcg.utils.Debug;

public class TestMonsterGeneration {
	public static void main(String[] args) {

		Monster ms = new Monster();
		ms.setStats(10, 10, 10, 10, 10, 10);
		ms.calculateFitness();
		System.out.print("bits: ");
		Debug.printBitsequence(ms.getGeneSeq());
		System.out.println(" |");

		EvSearch es = new EvSearch();

		es.init(ms);
		es.generateRandomPopulations();
		// es.sufflePop();
		es.calculateFitnessForPop();
		es.sortPop();
		ArrayList<GeneSequence> pop = es.getCurrentPopulation();

		for (int i = 0; i < pop.size(); i++) {
			Debug.printBitsequence(pop.get(i).getSeq());
			System.out.println(" | " + pop.get(i).getFitnessValue());
		}
		System.out.println("============================================================");

		es.deleteWorst();

		pop = es.getCurrentPopulation();

		for (int i = 0; i < pop.size(); i++) {
			Debug.printBitsequence(pop.get(i).getSeq());
			System.out.println(" | " + pop.get(i).getFitnessValue());
		}
		System.out.println("============================================================");
		es.generateOffSprings();

		pop = es.getCurrentPopulation();

		for (int i = 0; i < pop.size(); i++) {
			Debug.printBitsequence(pop.get(i).getSeq());
			System.out.println(" | " + pop.get(i).getFitnessValue());
		}
		System.out.println("============================================================");

		// es.sufflePop();
		es.calculateFitnessForPop();
		es.sortPop();

		pop = es.getCurrentPopulation();

		for (int i = 0; i < pop.size(); i++) {
			Debug.printBitsequence(pop.get(i).getSeq());
			System.out.println(" | " + pop.get(i).getFitnessValue());
		}

		es.deleteWorst();
		es.generateOffSprings();
		es.sufflePop();
		es.calculateFitnessForPop();
		es.sortPop();

		pop = es.getCurrentPopulation();

		for (int i = 0; i < pop.size(); i++) {
			Debug.printBitsequence(pop.get(i).getSeq());
			System.out.println(" | " + pop.get(i).getFitnessValue());
		}

		Debug.logMonster(new Monster(pop.get(0).getSeq()));

	}
}
