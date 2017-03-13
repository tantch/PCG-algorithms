package com.tantch.pcg.levelgeneration;

import java.util.ArrayList;

public class BNFNode {

	private ArrayList<BNFRule> rules;
	private boolean terminal=true;
	private String id;

	public BNFNode(String string) {
		this.id = string;
	}

	public void setAsNonTerminal() {
		terminal = false;
		rules = new ArrayList<>();
	}

	public ArrayList<BNFRule> getRules() {
		return rules;
	}

	public void setAsTerminal() {
		terminal = true;
	}
	public String getId(){
		return id;
	}
	
	public ArrayList<BNFNode> getResultingNodes(int i){
		
		int r = i % rules.size();
		
		return rules.get(r).getResultingNodes();
		
	}

	public boolean isTerminal() {
		return terminal;
	}

	@Override
	public String toString() {
		return "Node: " + id;
	}
}
