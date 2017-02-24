package com.tantch.pcg.mapgeneration.spacepartitioning;

import java.util.ArrayList;

import com.tantch.pcg.mapgeneration.representations.DunMap;

public class BSPTree {

	private BSPNode root;
	private DunMap map;// TODO change
	private int width, height;

	public BSPTree(DunMap dmap) {
		map = dmap;
		width = map.getSize();
		height = map.getSize();

		root = new BSPNode(0, 0, width, height);

	}

	public void run() {

		root.divide();
		root.runChildren();

	}

	public DunMap buildMap() {

		root.fillMap(map);
		return map;

	}

	public void createRooms() {
		root.createRoom();
	}
}
