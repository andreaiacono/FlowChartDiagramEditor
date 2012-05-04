package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.gui.FlowChartDrawing;

public class ControlBlock extends GraphicalBlock {

    public ControlBlock(Block block, FlowChartDrawing flowChartDrawing) {

        super(block, flowChartDrawing);
    }

    @Override
    public void paint(Graphics2D g) {

        // draws connecting lines
        super.paint(g);

        if (rect != null) {

            Polygon polygon = new Polygon(new int[]{rect.x, rect.x + rect.width / 2, rect.x + rect.width, rect.x + rect.width / 2}, new int[]{rect.y + rect.height / 2, rect.y, rect.y + rect.height / 2, rect.y + rect.height}, 4);
            g.setColor(Color.WHITE);
            g.fillPolygon(polygon);
            g.setColor(Color.DARK_GRAY);
            g.drawPolygon(polygon);

            drawText(g);
        }
    }
}
