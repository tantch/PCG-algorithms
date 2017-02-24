package com.tantch.pcg.mapgeneration.cmd;

import com.tantch.pcg.mapgeneration.representations.DunMap;
import com.tantch.pcg.mapgeneration.representations.MpCell;
import com.tantch.pcg.mapgeneration.representations.MpCell.CellType;

public class Draw {

	public static void drawMap(DunMap dmap,boolean divisions){
		
		int size = dmap.getSize();
		MpCell[][] map = dmap.getMap();
		
			for (int i = 0; i < size; i++) {
				System.out.print("|");

				for (int j = 0; j < size; j++) {

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

					for (int j = 0; j < size; j++) {

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
