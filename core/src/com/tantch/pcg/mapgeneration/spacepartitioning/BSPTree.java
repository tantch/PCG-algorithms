package com.tantch.pcg.mapgeneration.spacepartitioning;

import com.tantch.pcg.mapgeneration.representations.DunMap;

public class BSPTree {

	private BSPNode root;
	private DunMap dmap;// TODO change

	public BSPTree(DunMap dmap) {
		this.dmap = dmap;

		root = new BSPNode(0, 0, dmap.getSize(), dmap.getSize());

	}

	public void run() {

		root.divide();
		root.runChildren();

	}

	public DunMap buildMap() {

		root.fillMap(dmap);
		return dmap;

	}

	public void createRooms() {
		root.createRoom(dmap);
	}
}
