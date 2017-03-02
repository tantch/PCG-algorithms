package com.tantch.pcg.utils;

public class BitOperations {
	
	
	public static boolean[] intToBits(int input,int size){
		boolean[] seq = new boolean[size];
		
		for (int i = size -1; i >= 0; i--) {
	        seq[i] = (input & (1 << i)) != 0;
	    }
		
		return seq;
	}

}
