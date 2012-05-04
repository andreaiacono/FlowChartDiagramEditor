package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import org.aitek.fcde.diagram.Connection;
import org.aitek.fcde.gui.Side;
import org.aitek.fcde.gui.components.GraphicalBlock.ConnectingDirection;
import org.aitek.fcde.utils.Constants;
import org.aitek.fcde.utils.SwingUtils;

public class GraphicalConnection {

	private Arrow arrow;
	private final GraphicalBlock destinationBlock;
	private final GraphicalBlock sourceBlock;
	private final Connection connection;

	public GraphicalConnection(GraphicalBlock destinationlBlock, GraphicalBlock sourceBlock, Connection connection) {

		this.destinationBlock = destinationlBlock;
		this.sourceBlock = sourceBlock;
		this.connection = connection;
	}

	public void paint(Graphics2D g) {

		// the paint method is called before
		// the flowchart diagram init, so could arrive
		// here without having blocks defined
		if (destinationBlock != null && sourceBlock != null) {

			Point to = destinationBlock.getConnectingPoint(getDestinationSide().getOpposite(), ConnectingDirection.TO);
			Point from = sourceBlock.getConnectingPoint(getDestinationSide().getOpposite(), ConnectingDirection.FROM);

			// if the two block are next one to the other
			// just connects them directly
			if (isBlocksAdjacent()) {

				int labelHeight = SwingUtils.getStringHeight(g, Constants.DIAGRAM_FONT, connection.getLabel());

				g.setColor(Color.BLACK);
				g.drawLine(from.x, from.y, to.x, to.y);

				if (connection.getLabel() != null) {

					Rectangle sourceRect = sourceBlock.getRect();

					int labelPosY = (int) (sourceRect.y + sourceRect.height + Constants.ROW_DISTANCE * (Constants.FLOW_DESCRIPTION_DISTANCE / 100f));
					if (Constants.ROW_DISTANCE * (Constants.FLOW_DESCRIPTION_DISTANCE / 100f) < labelHeight) {
						labelPosY = sourceRect.y + sourceRect.height + labelHeight;
					}
					g.drawString(connection.getLabel(), sourceRect.x + sourceRect.width / 2 + Constants.FONT_SIZE, labelPosY - Constants.FONT_SIZE / 5);
				}
			}
			// if the two connected blocks are *not* next one to the other
			else {
                
                g.setColor(Color.BLACK);
				g.drawLine(from.x, from.y, to.x, to.y);


				// int middleX;
				// int middleY;
				//
				// // computes which direction give to the connecting line
				// // (could be left headed or right headed, depending on
				// // the column we're heading to)
				//
				// // left headed
				// if (destinationBlock.getColumn() < sourceBlock.getColumn()) {
				//
				// sourceX = sourceRect.x;
				// sourceY = sourceRect.y + sourceRect.height / 2;
				//
				// middleX = sourceRect.x - Constants.COLUMN_DISTANCE - sourceRect.width / 2;
				// middleY = sourceRect.y + sourceRect.height / 2;
				// }
				//
				// // right headed
				// else {
				//
				// sourceX = sourceRect.x + sourceRect.width;
				// sourceY = sourceRect.y + sourceRect.height / 2;
				//
				// middleX = destinationRect.x + destinationRect.width / 2;
				// middleY = sourceRect.y + sourceRect.height / 2;
				// }
				//
				// g.setColor(Color.BLACK);
				// g.drawLine(sourceX, sourceY, middleX, middleY);
				// g.drawLine(middleX, middleY, destinationX, destinationY);
				//
				// if (edge.getLabel() != null) {
				//
				// int labelPosY = (int) (sourceRect.y + sourceRect.height + Constants.ROW_DISTANCE
				// * (Constants.FLOW_DESCRIPTION_DISTANCE / 100f));
				// if (Constants.ROW_DISTANCE * (Constants.FLOW_DESCRIPTION_DISTANCE / 100f) <
				// labelHeight) {
				// labelPosY = sourceRect.y + sourceRect.height + labelHeight;
				// }
				// g.drawString(edge.getLabel(), destinationRect.x, labelPosY - Constants.FONT_SIZE
				// / 5);
				// }
			}

			if (arrow == null) {
				arrow = new Arrow(to.x, to.y, Constants.ARROW_SIZE);
			}
			arrow.paint(getDestinationSide(), g);
		}
	}

	private Side getDestinationSide() {

		int sourceColumn = sourceBlock.getColumn();
		int sourceRow = sourceBlock.getRow();
		int destinationColumn = destinationBlock.getColumn();
		int destinationRow = destinationBlock.getRow();

		if (sourceRow == destinationRow) {

			if (destinationColumn > sourceColumn) {
				return Side.RIGHT;
			}
			else {
				return Side.LEFT;
			}
		}
		else {

			if (destinationRow > sourceRow) {
				return Side.DOWN;
			}
			else {
				return Side.UP;
			}
		}

	}

	private boolean isBlocksAdjacent() {

		int sourceColumn = sourceBlock.getColumn();
		int sourceRow = sourceBlock.getRow();
		int destinationColumn = destinationBlock.getColumn();
		int destinationRow = destinationBlock.getRow();

		return Math.abs(sourceRow - destinationRow) <= 1 && Math.abs(sourceColumn - destinationColumn) <= 1;
	}
}
