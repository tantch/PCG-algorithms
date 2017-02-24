package space_partioning;

import java.util.ArrayList;

import mapping.DunMap;

public class BSPTree {

	private BSPNode root;
	public DunMap map;//TODO change
	private int width, height;

	public void run() {
		width = 50;
		height = 50;
		root = new BSPNode(0, 0, width, height);

		root.divide();
		root.runChildren();

	}

	public DunMap buildMap() {

		map = new DunMap(50);

		root.fillMap(map);
		return map;

	}

	public void drawMap(boolean divs) {
		map.printMapConsole(divs);
	}

	public void createRooms(){
		root.createRoom();
	}
}
