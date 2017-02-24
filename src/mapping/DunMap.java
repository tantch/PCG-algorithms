package mapping;

import java.util.HashMap;

import mapping.MpCell.CellType;

public class DunMap {

	private MpCell[][] map;
	private int SIZE;

	public DunMap(int size) {
		SIZE = size;
		init();

	}

	private void init() {

		map = new MpCell[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {

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

	public void printMapConsole(boolean divisions) {
		for (int i = 0; i < SIZE; i++) {
			System.out.print("|");

			for (int j = 0; j < SIZE; j++) {

				if (map[i][j].getType() == CellType.EMPTY) {

					System.out.print("  ");

				} else {
					System.out.print("**");

				}
				if (divisions) {
					if (map[i][j].getDivisions()[0]) {

						System.out.print("|");
					} else {
						System.out.print(" ");

					}
				}

			}
			System.out.println("|");
			if (divisions) {
				System.out.print("|");

				for (int j = 0; j < SIZE; j++) {

					if (map[i][j].getDivisions()[1]) {

						System.out.print("---");
					} else {
						System.out.print("   ");

					}

				}
				System.out.println("|");
			}
		}

	}

}
