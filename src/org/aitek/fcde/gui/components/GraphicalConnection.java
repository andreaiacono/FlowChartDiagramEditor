package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.Logger;
import org.aitek.fcde.diagram.Block;

import org.aitek.fcde.diagram.Connection;
import org.aitek.fcde.gui.Side;
import org.aitek.fcde.gui.components.GraphicalBlock.ConnectingDirection;
import org.aitek.fcde.utils.Constants;
import org.aitek.fcde.utils.LoggerManager;
import org.aitek.fcde.utils.SwingUtils;

public class GraphicalConnection {

    private Logger logger = LoggerManager.getLogger(this.getClass());
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
//        logger.severe("dest=" + destinationBlock.getId() + " soruce= " + sourceBlock.getId());

            Point to = destinationBlock.getConnectingPoint(getDestinationBlockArrivalSide().getOpposite(), ConnectingDirection.TO);
            Point from = sourceBlock.getConnectingPoint(getSourceBlockStartingSide(), ConnectingDirection.FROM);
            g.setColor(Color.BLACK);

            // if the two block are next one to the other
            // just connects them directly
            if (isBlocksAdjacent()) {


                g.drawLine(from.x, from.y, to.x, to.y);

                if (connection.getLabel() != null) {

                    Rectangle sourceRect = sourceBlock.getRect();

                    int labelHeight = SwingUtils.getStringHeight(g, Constants.DIAGRAM_FONT, connection.getLabel());
                    int labelPosY = (int) (sourceRect.y + sourceRect.height + Constants.ROW_DISTANCE * (Constants.FLOW_DESCRIPTION_DISTANCE / 100f));
                    if (Constants.ROW_DISTANCE * (Constants.FLOW_DESCRIPTION_DISTANCE / 100f) < labelHeight) {
                        labelPosY = sourceRect.y + sourceRect.height + labelHeight;
                    }
                    g.drawString(connection.getLabel(), sourceRect.x + sourceRect.width / 2 + Constants.FONT_SIZE, labelPosY - Constants.FONT_SIZE / 5);
                }
            }
            // if the two connected blocks are *not* next one to the other
            else {

                // max number of vertex in spotted line between two blocks
                int connectionVertexes = 6;
                Point[] points = new Point[connectionVertexes];

                points[0] = from;

                if (getSourceBlockStartingSide() == Side.LEFT) {
                    points[1] = new Point(sourceBlock.getGridRectangle().x, from.y);
                }
                else {
                    points[1] = new Point(sourceBlock.getGridRectangle().x + sourceBlock.getGridRectangle().width, from.y);
                }

                points[2] = new Point(points[1].x, destinationBlock.getGridRectangle().y);
                points[3] = new Point(destinationBlock.getGridRectangle().x + destinationBlock.getGridRectangle().width, points[2].y);
                points[4] = new Point(points[3].x, to.y);
                points[5] = to;
                g.drawPolyline(SwingUtils.getXPoints(points), SwingUtils.getYPoints(points), 6);
            }

            if (arrow == null) {
                arrow = new Arrow(to.x, to.y, Constants.ARROW_SIZE);
            }
            arrow.paint(getDestinationBlockArrivalSide(), g);
        }
    }

    private Side getDestinationBlockArrivalSide() {

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

    private Side getSourceBlockStartingSide() {

        if (sourceBlock.getBlock().getType() != Block.Type.CONTROL) {

            return Side.DOWN;
        }
        else {

            if (connection.getIndex() == 0) {
                return Side.DOWN;
            }
            else {

                if (sourceBlock.getColumn() < destinationBlock.getColumn()) {
                    return Side.RIGHT;
                }
                else {
                    return Side.LEFT;
                }
            }
        }
    }

    private boolean isBlocksAdjacent() {

        int sourceColumn = sourceBlock.getColumn();
        int sourceRow = sourceBlock.getRow();
        int destinationColumn = destinationBlock.getColumn();
        int destinationRow = destinationBlock.getRow();

        boolean result = Math.abs(sourceRow - destinationRow) + Math.abs(sourceColumn - destinationColumn) <= 1;

        return result;
    }
}
