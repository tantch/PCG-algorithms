package com.tantch.pcg.levelgeneration.cmd;

import com.tantch.pcg.levelgeneration.BNFGrammar;
import com.tantch.pcg.utils.Debug;

public class TestGrammar {

	public static void main(String[] args) {

		BNFGrammar.initDefaultGrammar();
		
		Debug.logBNFGrammar();
		int[] seq = BNFGrammar.generateRandomSeed();
		System.out.println(BNFGrammar.getResult(seq).toString());
		
		
		
		
	}

}
