package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.gui.Side;

public class IoBlock extends GraphicalBlock {

	public static final float DELTA = 0.20f;

	public IoBlock(Block block) {

		super(block);
	}

	@Override
	public void paint(Graphics2D g) {

		// draws connecting lines
		super.paint(g);

		if (rect != null) {

			g.setColor(Color.WHITE);
			Polygon shape = new Polygon(new int[] { rect.x, rect.x + rect.width - ((int) (rect.width * DELTA)), rect.x + rect.width, rect.x + ((int) (rect.width * DELTA)) }, new int[] { rect.y, rect.y, rect.y + rect.height, rect.y + rect.height }, 4);
			g.fillPolygon(shape);

			g.setColor(Color.DARK_GRAY);
			g.drawPolygon(shape);
		}

		drawText(g);
	}

	/**
	 * returns the connecting point for the specified edge
	 * 
	 * @return
	 */
	@Override
	public Point getConnectingPoint(Side side, ConnectingDirection direction) {

		switch (side) {

			case RIGHT:
				return new Point(rect.x + rect.width - ((int) (rect.width * DELTA / 2)), rect.y + rect.height / 2);
			case LEFT:
				return new Point(rect.x + ((int) (rect.width * DELTA / 2)), rect.y + rect.height / 2);
			case UP:
				return new Point(rect.x + rect.width / 2, rect.y);
			case DOWN:
				return new Point(rect.x + rect.width / 2, rect.y + rect.height);
		}
		return null;
	}
}
