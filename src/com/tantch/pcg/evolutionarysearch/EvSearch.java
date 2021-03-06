package com.tantch.pcg.evolutionarysearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.tantch.pcg.utils.Settings;

public class EvSearch {

	// TODO add with premade individuals

	private int u = 50;
	private int v = 50;
	private EvRepresentation sample;
	private int[] args ;
	private ArrayList<GeneSequence> pop;

	public void run(int it) {
		generateRandomPopulations();
		sufflePop();
		calculateFitnessForPop();
		sortPop();
		for (int i = 1; i < it; i++) {
			deleteWorst();
			generateOffSprings();
			calculateFitnessForPop();
			sortPop();
		}
	

	}

	public void init(EvRepresentation rp) {
		pop = new ArrayList<GeneSequence>();
		sample = rp;
	}

	public void generateRandomPopulations() {
		for (int i = 0; i < u + v; i++) {
			boolean[] seq = sample.generateRandomGeneSeq();

			GeneSequence temp = new GeneSequence(seq);
			pop.add(temp);

		}
	}

	public void calculateFitnessForPop() {
		for (int i = 0; i < pop.size(); i++) {
			
			sample.loadFromGene(pop.get(i).getSeq());
			
			double result = sample.calculateFitness(args);

			pop.get(i).setFitnessValue(result);

		}
	}

	public void deleteWorst() {
		for (int i = 0; i < v; i++) {
			pop.remove(u);
		}
	}

	public void generateOffSprings() {
		for (int i = 0; i < v; i++) {
			boolean[] seq = pop.get(i % u).getSeq().clone();
			Random rd = new Random();

			for (int j = 0; j < seq.length; j++) {
				int vl = rd.nextInt(100);
				if (vl < Settings.EA_MUTATION_PROB) {
					seq[j] = !seq[j];
				}
			}
			pop.add(new GeneSequence(seq));
		}
	}

	public ArrayList<GeneSequence> getCurrentPopulation() {
		return pop;
	}

	public void sufflePop() {
		Collections.shuffle(pop);

		/*
		 * Random rnd = new Random();
		 * 
		 * for (int i = pop.length - 1; i > 0; i--) { int index = rnd.nextInt(i
		 * + 1); // Simple swap boolean[] a = pop[index]; pop[index] = pop[i];
		 * pop[i] = a; }
		 */
	}

	public void sortPop() {
		pop.sort((g1, g2) -> Double.valueOf(g2.getFitnessValue()).compareTo(Double.valueOf(g1.getFitnessValue())));

	}
	public void setArgs(int[] args){
		this.args=args;
		System.out.println("args:" + args);
	}

}
