package com.tantch.pcg.levelgeneration;

import java.util.ArrayList;

public class BNFRule {

	private String rule;

	public void setRuleByString(String string) {
		this.rule = string;
	}

	public String getString() {
		return rule;
	}
	
	
	public ArrayList<BNFNode> getResultingNodes(){
		
		ArrayList<BNFNode> val = new ArrayList<>();
		
		String[] parse = rule.split("/");
		for (String string : parse) {
			
			val.add(BNFGrammar.getNode(string));
			
		}
		
		return val;
		
	}
	

}
