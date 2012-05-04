package org.aitek.fcde.gui;

public enum Side {
	UP, DOWN, LEFT, RIGHT;

	public Side getOpposite() {

		switch (this) {
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
		}

		return null;
	}
}