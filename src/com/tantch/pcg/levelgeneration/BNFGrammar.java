package com.tantch.pcg.levelgeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.tantch.pcg.mapgeneration.representations.DunMap;

public class BNFGrammar {

	private static HashMap<String, BNFNode> definedNodes;
	private static BNFNode S;

	public static void initDefaultGrammar() {

		S = new BNFNode("Start");
		S.setAsNonTerminal();
		ArrayList<BNFRule> rules = S.getRules();

		// S-> <X>? <Treasure Room> <Boss Room> <Locked Exit>

		BNFRule rl = new BNFRule();
		rl.setRuleByString("X/TreasureRoom/BossRoom/LockedExit");
		rules.add(rl);

		BNFNode tNode = new BNFNode("TreasureRoom");
		tNode.setAsTerminal();
		addDefinedNode("TreasureRoom", tNode);

		tNode = new BNFNode("BossRoom");
		tNode.setAsTerminal();
		addDefinedNode("BossRoom", tNode);

		tNode = new BNFNode("LockedExit");
		tNode.setAsTerminal();
		addDefinedNode("LockedExit", tNode);
		
		
		// X-> <X> <X>
		// x-> Special Room
		tNode = new BNFNode("X");
		tNode.setAsNonTerminal();
		rules = tNode.getRules();
		rl = new BNFRule();
		rl.setRuleByString("SpecialRoom");
		rules.add(rl);
		rl = new BNFRule();
		rl.setRuleByString("X/X");
		rules.add(rl);
		rl = new BNFRule();
		rl.setRuleByString("SpecialRoom");
		rules.add(rl);

		addDefinedNode("X", tNode);
		
		tNode = new BNFNode("SpecialRoom");
		tNode.setAsTerminal();
		addDefinedNode("SpecialRoom", tNode);


	}

	public static void addDefinedNode(String id, BNFNode node) {
		if (definedNodes == null) {
			definedNodes = new HashMap<>();
		}

		if (!definedNodes.containsKey(id)) {
			definedNodes.put(id, node);
		}

	}

	public static BNFNode getNode(String id) {

		if (definedNodes.containsKey(id)) {
			return definedNodes.get(id);
		}

		return null;
	}

	public static boolean isInit() {

		return S != null;
	}

	public static BNFNode getS() {
		return S;
	}

	public static HashMap<String, BNFNode> getNodes() {
		return definedNodes;
	}

	public static ArrayList<BNFNode> getResult(int[] seq) {
		ArrayList<BNFNode> list = new ArrayList<>();
		boolean finished = false;
		int i =0;
		ArrayList<BNFNode> temp1 = S.getResultingNodes(seq[i]);
		ArrayList<BNFNode> temp2 = new ArrayList<>();
		while (!finished) {
			finished=true;
			for (BNFNode node : temp1) {
				System.out.println("Processing " +  node.getId());
				if(node.isTerminal()){
					
					list.add(node);
				}else{
					i++;
					i = i% seq.length;
					finished=false;
					temp2.addAll(node.getResultingNodes(seq[i]));
					
					
					
				}
				
				
			}
			temp1 = temp2;
			temp2 = new ArrayList<>();
			
			
			
		}

		return list;

	}
	
	public static int[] generateRandomSeed(){
		
		Random rd= new Random();
		int[] ret = new int[10];
		for(int i=0;i<10;i++){
			ret[i] = rd.nextInt(100);
		}
		return ret;
	}

	
	public static void loadToDunMap(DunMap dmap, ArrayList<BNFNode> res){
		
		
		for (BNFNode node : res) {
			switch (node.getId()) {
			case "BossRoom":
				dmap.getEmptyRoom().setAsBossRoom(dmap);
				break;

			default:
				break;
			}
			
		}
		
	}
}
