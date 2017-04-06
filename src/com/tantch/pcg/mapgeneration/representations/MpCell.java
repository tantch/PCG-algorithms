package com.tantch.pcg.mapgeneration.representations;

import java.util.HashMap;

import com.tantch.pcg.assets.Monster;
import com.tantch.pcg.utils.Debug;

public class MpCell {

	public enum CellType {
		EMPTY, ROOM, FILLED
	}

	private int roomId = -1;

	private CellType type;
	private boolean[] debugDivisions = { false, false };
	private int x, y;

	private Monster mns;

	public MpCell(CellType type, int x, int y) {

		this.type = type;
		this.x = x;
		this.y = y;
	}

	public void setDivisions(int i, boolean div) {
		debugDivisions[i] = div;
	}

	public boolean[] getDivisions() {
		return debugDivisions;
	}

	public CellType getType() {
		return this.type;
	}

	public void setType(CellType type) {
		this.type = type;
	}

	public void setRoomId(int id) {
		if (type != CellType.ROOM) {
			Debug.logError("MpCell", "Can't set room id of cell type != ROOM");
		} else {
			roomId = id;
		}
	}

	public int getRoomId() {
		return roomId;
	}

	public boolean isSurroundedByRoomCells(DunMap dmap) {

		boolean ret = true;

		if (x == 0 || x == dmap.getSize() || y == 0 || y == dmap.getSize()) {
			ret = false;
		} else if (dmap.getCellType(x, y) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x - 1, y) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x + 1, y) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x - 1, y - 1) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x, y - 1) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x + 1, y - 1) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x - 1, y + 1) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x, y + 1) != CellType.EMPTY) {
			ret = false;
		} else if (dmap.getCellType(x + 1, y + 1) != CellType.EMPTY) {
			ret = false;
		}

		return ret;
	}

	public void updateBelongtoRoom(DunMap dmap) {

		if (dmap.getCellType(x, y) != CellType.EMPTY) {
			return;
		}

		HashMap<Integer, Integer> rmcnts = new HashMap<Integer, Integer>();

		if (x > 0) {
			if (dmap.getCellType(x - 1, y) == CellType.ROOM) {
				updateCounterForId(dmap, rmcnts, x - 1, y);
			}
			if (y > 0 && dmap.getCellType(x - 1, y - 1) == CellType.ROOM) {

				updateCounterForId(dmap, rmcnts, x - 1, y - 1);

			}
			if (y < dmap.getSize() && dmap.getCellType(x - 1, y + 1) == CellType.ROOM) {

				updateCounterForId(dmap, rmcnts, x - 1, y + 1);

			}

		}

		if (x < dmap.getSize() && dmap.getCellType(x + 1, y) == CellType.ROOM) {

			updateCounterForId(dmap, rmcnts, x + 1, y);

			if (y > 0 && dmap.getCellType(x + 1, y - 1) == CellType.ROOM) {

				updateCounterForId(dmap, rmcnts, x + 1, y - 1);

			}
			if (y < dmap.getSize() && dmap.getCellType(x + 1, y + 1) == CellType.ROOM) {

				updateCounterForId(dmap, rmcnts, x + 1, y + 1);

			}
		}

		if (y > 0 && dmap.getCellType(x, y - 1) == CellType.ROOM) {

			updateCounterForId(dmap, rmcnts, x, y - 1);

		}
		if (y < dmap.getSize() && dmap.getCellType(x, y + 1) == CellType.ROOM) {

			updateCounterForId(dmap, rmcnts, x, y + 1);

		}

		rmcnts.forEach((k, v) -> {
			if (v >= 3)
				type = CellType.ROOM;
			roomId = k;
		});

	}

	private void updateCounterForId(DunMap dmap, HashMap<Integer, Integer> rmcnts, int x2, int y2) {
		int id = dmap.getRoomId(x2, y2);
		if (rmcnts.containsKey(id)) {
			rmcnts.replace(id, rmcnts.get(id) + 1);
		} else {
			rmcnts.put(id, 1);
		}
	}

	public void monsterInCell(Monster ms) {
		this.mns = ms;
	}

	public void receiveAttack() {
		if (mns != null) {
			mns.die();
		}
	}

}
