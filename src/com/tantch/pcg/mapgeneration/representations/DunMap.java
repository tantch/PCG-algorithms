package com.tantch.pcg.mapgeneration.representations;

import java.util.HashMap;

import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class DunMap {

	private MpCell[][] map;
	private int size;

	public DunMap(int size) {
		this.size = size;
		init();

	}

	private void init() {

		map = new MpCell[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				map[i][j] = new MpCell(CellType.FILLED);

			}
		}

	}

	public CellType getCellType(int row, int col) {
		return map[col][row].getType();
	}

	public void setCellType(int row, int col, CellType type) {
		map[col][row].setType(type);
	}

	public void setCellDivisions(int row, int col, int i, boolean div) {
		map[col][row].setDivisions(i,div);

	}

	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}

	public MpCell[][] getMap() {
		// TODO Auto-generated method stub
		return map;
	}

	

}
