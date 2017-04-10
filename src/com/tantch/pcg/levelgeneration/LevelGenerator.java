package com.tantch.pcg.levelgeneration;

import java.util.ArrayList;
import java.util.Random;

import com.tantch.pcg.evolutionarysearch.BNFSeq;
import com.tantch.pcg.evolutionarysearch.GeneSequence;
import com.tantch.pcg.utils.Debug;
import com.tantch.pcg.utils.Settings;

public class LevelGenerator {

	private int v = 50;
	private int u = 50;
	private ArrayList<BNFSeq> seeds;

	public LevelGenerator() {
		seeds = new ArrayList<BNFSeq>();
	}

	public void run() {
		BNFGrammar.initDefaultGrammar();
		Debug.logBNFGrammar();

		generatePopulation();
		calculateFitness();
		sortPop();

		for (int i = 0; i < 0; i++) {
			deleteWorst();
			generateOffSprings();
			calculateFitness();
			sortPop();
		}

		System.out.println(seeds);

	}

	private void generateOffSprings() {
		for (int i = 0; i < v; i++) {
			int[] seq = seeds.get(i % u).getSeq().clone();
			Random rd = new Random();

			for (int j = 0; j < seq.length; j++) {
				int vl = rd.nextInt(100);
				if (vl < Settings.EA_MUTATION_PROB) {
					seq[j] = rd.nextInt(100);
				}
			}
			seeds.add(new BNFSeq(seq));
		}
	}

	public void deleteWorst() {
		for (int i = 0; i < v; i++) {
			seeds.remove(u);
		}
	}

	private void calculateFitness() {
		for (int i = 0; i < seeds.size(); i++) {
			seeds.get(i).calculateFitnessValue();
		}

	}

	public void generatePopulation() {

		for (int i = 0; i < u + v; i++) {

			BNFSeq seq = BNFGrammar.generateRandomSeed();
			seeds.add(seq);

		}

	}

	public void sortPop() {
		seeds.sort((g1, g2) -> Double.valueOf(g2.getFitnessValue()).compareTo(Double.valueOf(g1.getFitnessValue())));

	}

}
