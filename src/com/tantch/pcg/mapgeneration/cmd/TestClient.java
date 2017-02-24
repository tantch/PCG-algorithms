package com.tantch.pcg.mapgeneration.cmd;

import com.tantch.pcg.mapgeneration.agents.BlindAgent;
import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.spacepartitioning.BSPTree;
import com.tantch.pcg.utils.Debug;

public class TestClient {

	
	
	
	public static void main(String[] args) {

		Debug.setVerbose(true);
		DunMap dmap= new DunMap(30);
		BSPTree tree = new BSPTree(dmap);
		tree.run();
		tree.createRooms();
		tree.buildMap();

		
		BlindAgent ag = new BlindAgent();
		ag.init(dmap);
		ag.setInitialPosition(10, 10);
		ag.setStamina(30);
		ag.setCurrentDirection(2);
		ag.setParameters(2, 0);
		ag.setStamina(20);
		ag.start();
		Draw.drawMap(dmap,false);
		
		
	}
	
	
	
	

}
