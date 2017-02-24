package space_partioning;

import mapgenerator.BlindAgent;

public class SpacePartioning {

	public static void main(String[] args) {

		BSPTree tree = new BSPTree();
		tree.run();
		tree.createRooms();
		tree.buildMap();
		tree.drawMap(true);
		tree.drawMap(false);
		
		BlindAgent ag = new BlindAgent();
		ag.init(tree.map);
		ag.start();
		tree.drawMap(false);
		
		
	}

}
