package com.tantch.pcg.assets;

public class Item {

	String description;
	int x, y;
	boolean trueItem;

	public Item(String description) {

		this.description = description;

	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setTrueItem(boolean b) {
		trueItem = b;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getDescription() {
		return description;
	}

	public int getTrueItem() {
		if (trueItem) {
			return 1;

		}
		return 0;
	}

}
