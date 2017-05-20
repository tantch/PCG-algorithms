package com.tantch.pcg.evolutionarysearch;

public interface EvRepresentation {
	
	
	
	public double calculateFitness(int[] args);
	
	public int getGeneSeqSize();
	public boolean[] getGeneSeq();
	public boolean[] generateRandomGeneSeq();
	public void loadFromGene(boolean[] seq);
}
