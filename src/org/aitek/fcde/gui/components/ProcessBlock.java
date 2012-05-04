package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.gui.FlowChartDrawing;

public class ProcessBlock extends GraphicalBlock {

	public ProcessBlock(Block block, FlowChartDrawing flowChartDrawing) {

		super(block, flowChartDrawing);
	}

	@Override
	public void paint(Graphics2D g) {

		// draws connecting lines
		super.paint(g);

		if (rect != null) {

			g.setColor(Color.WHITE);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);

			g.setColor(Color.DARK_GRAY);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);

			drawText(g);
		}
	}
}
