package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import org.aitek.fcde.diagram.Block;

public class EndBlock extends GraphicalBlock {

	public EndBlock(Block block) {

		super(block);
	}

	@Override
	public void paint(Graphics2D g) {

		// draws connecting lines
		super.paint(g);

		if (rect != null) {

			g.setColor(Color.WHITE);
			g.fillRoundRect(rect.x, rect.y, rect.width, rect.height, rect.height, rect.height);

			g.setColor(Color.DARK_GRAY);
			g.drawRoundRect(rect.x, rect.y, rect.width, rect.height, rect.height, rect.height);

			drawText(g);
		}
	}
}
