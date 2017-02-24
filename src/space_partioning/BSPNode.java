package space_partioning;

import java.util.ArrayList;
import java.util.Random;

import mapping.DunMap;
import mapping.MpCell.CellType;

public class BSPNode {
	private static int minPartitionSize = 4;

	private static int maxPartitionSize = 10;
	private static int MINROOMSIZE = 2;

	private int xLowerBound, yLowerBound;// inclusive
	private int xUpperBound, yUpperBound;// exclusive
	private ArrayList<BSPNode> children;
	private boolean leaf = false;

	private int roomx1, roomx2, roomy1, roomy2;// inclusive

	public BSPNode(int x1, int y1, int x2, int y2) {
		this.xLowerBound = x1;
		this.yLowerBound = y1;

		this.xUpperBound = x2;
		this.yUpperBound = y2;

		children = new ArrayList<>();
	}

	public void divide() {

		System.out
				.println("node bounds: " + xLowerBound + "/" + yLowerBound + " -> " + xUpperBound + "/" + yUpperBound);

		Random rd = new Random();
		int direction = rd.nextInt(2);
		if (direction == 0) {
			// if horizontal

			if (shouldDivideFurther(yUpperBound - yLowerBound)) {

				int border = 0;
				do {
					border = rd.nextInt(yUpperBound - yLowerBound) + yLowerBound;
				} while ((border - yLowerBound) < minPartitionSize || (yUpperBound - border) < minPartitionSize);
				System.out.println("hor Border:" + border);

				BSPNode child1 = new BSPNode(xLowerBound, yLowerBound, xUpperBound, border);
				BSPNode child2 = new BSPNode(xLowerBound, border, xUpperBound, yUpperBound);

				children.add(child1);
				children.add(child2);
			} else {
				leaf = true;
			}
		} else {

			if (shouldDivideFurther(xUpperBound - xLowerBound)) {

				int border = 0;
				do {
					border = rd.nextInt(xUpperBound - xLowerBound) + xLowerBound;
				} while ((border - xLowerBound) < minPartitionSize || (xUpperBound - border) < minPartitionSize);
				System.out.println("vertical Border:" + border);
				BSPNode child1 = new BSPNode(xLowerBound, yLowerBound, border, yUpperBound);
				BSPNode child2 = new BSPNode(border, yLowerBound, xUpperBound, yUpperBound);

				children.add(child1);
				children.add(child2);
			} else {
				leaf = true;
			}

		}

	}

	public void runChildren() {

		for (int i = 0; i < children.size(); i++) {
			children.get(i).divide();
		}

		for (int i = 0; i < children.size(); i++) {
			children.get(i).runChildren();
		}

	}

	public void fillMap(DunMap map) {

		if (leaf) {

			for (int i = roomx1; i <= roomx2; i++) {
				for (int j = roomy1; j <= roomy2; j++) {
					map.setCellType(i, j, CellType.EMPTY);
				}
			}

			for (int i = xLowerBound; i < xUpperBound; i++) {

				map.setCellDivisions(i, yUpperBound - 1, 1, true);

			}
			for (int j = yLowerBound; j < yUpperBound; j++) {

				map.setCellDivisions(xUpperBound - 1, j, 0, true);

			}

		} else {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).fillMap(map);
			}
		}

	}

	public static boolean shouldDivideFurther(int value) {

		float res = value / new Float(maxPartitionSize);

		if (value <= minPartitionSize * 2) {
			return false;
		}

		System.out.println("RES: " + res);
		if (res > 1) {
			return true;
		}
		Random rd = new Random();
		float pb = rd.nextFloat();

		if (pb > Math.pow(res, 3)) {
			return false;
		}

		return true;
	}

	public void createRoom() {
		if (leaf) {

			Random rd = new Random();
			int x1 = rd.nextInt(xUpperBound - xLowerBound) + xLowerBound;
			int x2 = rd.nextInt(xUpperBound - xLowerBound) + xLowerBound;
			int y1 = rd.nextInt(yUpperBound - yLowerBound) + yLowerBound;
			int y2 = rd.nextInt(yUpperBound - yLowerBound) + yLowerBound;

			if (x1 > x2) {
				roomx1 = x2;
				roomx2 = x1;
			} else {
				roomx1 = x1;
				roomx2 = x2;
			}

			if (y1 > y2) {
				roomy1 = y2;
				roomy2 = y1;
			} else {
				roomy1 = y1;
				roomy2 = y2;
			}

			int dif = roomx2 - roomx1;
			if (dif < MINROOMSIZE) {

				if (roomx1 - xLowerBound >= MINROOMSIZE - dif) {
					roomx1 -= MINROOMSIZE - dif;
				} else if (xUpperBound - roomx2 -1 >= MINROOMSIZE - dif) {
					roomx2 += MINROOMSIZE - dif-1;
				}else{
					roomx1-=roomx1 - xLowerBound;
					roomx2+=xUpperBound - roomx2-1;
				}

			}
			
			dif = roomy2 - roomy1;
			if (dif < MINROOMSIZE) {

				if (roomy1 - yLowerBound >= MINROOMSIZE - dif) {
					roomy1 -= MINROOMSIZE - dif;
				} else if (yUpperBound - roomy2 -1 >= MINROOMSIZE - dif) {
					roomy2 += MINROOMSIZE - dif-1;
				}else{
					roomy1-=roomy1 - yLowerBound;
					roomy2+=yUpperBound - roomy2-1;
				}

			}
			
			System.out.println("ROOM: " + roomx1 + "/" + roomy1 + "->" + roomx2 + "/" + roomy2 );

		} else {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).createRoom();
			}
		}
	}

}
