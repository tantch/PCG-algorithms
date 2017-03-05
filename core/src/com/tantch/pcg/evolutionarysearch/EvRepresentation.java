package com.tantch.pcg.evolutionarysearch;

public interface EvRepresentation {
	
	
	
	public double calculateFitness();
	
	public int getGeneSeqSize();
	public boolean[] getGeneSeq();
	public boolean[] generateRandomGeneSeq();

}
