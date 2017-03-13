package com.tantch.pcg.levelgeneration.cmd;

import com.tantch.pcg.levelgeneration.BNFGrammar;
import com.tantch.pcg.utils.Debug;

public class TestGrammar {

	public static void main(String[] args) {

		BNFGrammar.initDefaultGrammar();
		
		Debug.logBNFGrammar();
		int[] seq = new int[]{1,3,4,3,8,7,4,4,4,4,4,4,32,4527,12};
		System.out.println(BNFGrammar.getResult(seq).toString());
		
		
	}

}
