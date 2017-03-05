package com.tantch.pcg.mapgeneration.spacepartitioning;

import java.util.ArrayList;

import com.tantch.pcg.mapgeneration.representations.DunMap;

public class BSPTree {

	private BSPNode root;
	private DunMap dmap;// TODO change
	private int width, height;

	public BSPTree(DunMap dmap) {
		this.dmap = dmap;
		width = dmap.getSize();
		height = dmap.getSize();

		root = new BSPNode(0, 0, width, height);

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
