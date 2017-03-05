package com.tantch.pcg.evolutionarysearch;

public class GeneSequence {

	
	private boolean[] seq;
	private double fitnessValue; 
	
	public GeneSequence(boolean[] seq){
		this.seq = seq;
		
	}

	public double getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public boolean[] getSeq() {
		return seq;
	}
	
	
	
	
	
}
