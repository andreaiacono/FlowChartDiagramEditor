package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import org.aitek.fcde.gui.Side;

public class Arrow {

	private int[] x = new int[3];
	private int[] y = new int[3];
	private final int xCoord;
	private final int yCoord;
	private int size;

	public Arrow(int xCoord, int yCoord, int size) {

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.size = size;
	}

	public void paint(Side pointsTowardsSide, Graphics2D g) {

		g.setColor(Color.BLACK);

		switch (pointsTowardsSide) {

			case UP:

				x[0] = xCoord;
				y[0] = yCoord;

				x[1] = xCoord - size / 2;
				y[1] = yCoord + size;

				x[2] = xCoord + size / 2;
				y[2] = yCoord + size;

			break;
			case DOWN:

				x[0] = xCoord;
				y[0] = yCoord;

				x[1] = xCoord - size / 2;
				y[1] = yCoord - size;

				x[2] = xCoord + size / 2;
				y[2] = yCoord - size;

			break;
			case LEFT:

				x[0] = xCoord;
				y[0] = yCoord;

				x[1] = xCoord + size;
				y[1] = yCoord - size / 2;

				x[2] = xCoord + size;
				y[2] = yCoord + size / 2;

			break;

			case RIGHT:

				x[0] = xCoord;
				y[0] = yCoord;

				x[1] = xCoord - size;
				y[1] = yCoord - size / 2;

				x[2] = xCoord - size;
				y[2] = yCoord + size / 2;

			break;
		}

		g.fillPolygon(x, y, 3);
	}
}
