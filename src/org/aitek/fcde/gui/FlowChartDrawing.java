package org.aitek.fcde.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.diagram.Block.Type;
import org.aitek.fcde.diagram.Connection;
import org.aitek.fcde.diagram.FlowChart;
import org.aitek.fcde.gui.components.ControlBlock;
import org.aitek.fcde.gui.components.EndBlock;
import org.aitek.fcde.gui.components.GraphicalBlock;
import org.aitek.fcde.gui.components.GraphicalConnection;
import org.aitek.fcde.gui.components.IoBlock;
import org.aitek.fcde.gui.components.ProcessBlock;
import org.aitek.fcde.gui.components.StartBlock;
import org.aitek.fcde.utils.Constants;

public class FlowChartDrawing {

    private FlowChart flowChart = null;
    private GraphicalBlock[][] grid = null;
    private Map<String, GraphicalBlock> graphicalBlocks = new HashMap<String, GraphicalBlock>();
    private GraphicalBlock startingGraphicalBlock;
    private int maxSize;
    private int cellHeight;
    private int cellWidth;
    private Color gridColor = new Color(160, 160, 160);

    public String updateDrawing(String diagram) {

        flowChart = new FlowChart();
        String result = null;

        try {
            result = flowChart.parseDiagram(diagram);
            this.maxSize = flowChart.getBlocksNumber();
            grid = new GraphicalBlock[maxSize][maxSize];
            createGraphicalBlocks();
        }
        catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    private void createGraphicalBlocks() {

        // for every blocks, creates its graphical
        // counterpart for drawing the chart
        for (String id : flowChart.getBlockIds()) {

            Block block = flowChart.getBlock(id);
            GraphicalBlock graphicalBlock = null;

            switch (block.getType()) {
                case START:
                    graphicalBlock = new StartBlock(block, this);
                    break;
                case PROCESS:
                    graphicalBlock = new ProcessBlock(block, this);
                    break;
                case CONTROL:
                    graphicalBlock = new ControlBlock(block, this);
                    break;
                case IO:
                    graphicalBlock = new IoBlock(block, this);
                    break;
                case END:
                    graphicalBlock = new EndBlock(block, this);
                    break;
            }

            graphicalBlocks.put(id, graphicalBlock);
            if (block.getType() == Type.START) {
                startingGraphicalBlock = graphicalBlock;
            }

            // creates the graphical connections
            for (Connection connection : block.getConnections()) {

                GraphicalBlock destinationGraphicalBlock = graphicalBlocks.get(connection.getDestinationBlockId());
                GraphicalConnection graphicalConnection = new GraphicalConnection(destinationGraphicalBlock, graphicalBlock, connection);
                graphicalBlock.addGraphicalConnection(graphicalConnection);
            }
        }
    }

    private void setGridSize() {

        // computes the max size of a cell getting the max width
        // and height of the blocks
        cellHeight = 0;
        cellWidth = 0;
        for (String key : graphicalBlocks.keySet()) {

            GraphicalBlock block = getGraphicalBlock(key);
            if (block.getHeight() > cellHeight) {
                cellHeight = block.getHeight();
            }
            if (block.getWidth() > cellWidth) {
                cellWidth = block.getWidth();
            }
        }

        cellHeight += Constants.ROW_DISTANCE;
        cellWidth += Constants.COLUMN_DISTANCE;

        // sets the rectangle position in the center of the cell in which the
        // rectangle is contained
        for (String key : graphicalBlocks.keySet()) {

            GraphicalBlock block = getGraphicalBlock(key);
            int x = block.getColumn() * cellWidth + (cellWidth - block.getWidth()) / 2;
            int y = block.getRow() * cellHeight + (cellHeight - block.getHeight()) / 2;
            block.setCoords(x, y);
        }
    }

    /**
     * puts the blocks inside the grid calling a recursive method
     */
    private void putBlocksInGrid(Graphics2D g) {

        if (startingGraphicalBlock != null) {

            // puts the blocks in the grid
            putBlockInGrid(startingGraphicalBlock, 0, 0, g);
        }
    }

    /**
     * recursive method for putting a block (and its following blocks) inside
     * the grid.
     *
     * @param currentBlock
     * @param currentRow
     * @param currentColumn
     * @param g
     */
    private void putBlockInGrid(GraphicalBlock currentBlock, int currentRow, int currentColumn, Graphics2D g) {

        grid[currentRow][currentColumn] = currentBlock;
        currentBlock.setRow(currentRow);
        currentBlock.setColumn(currentColumn);
        currentBlock.setSize(g);

        for (Connection Connection : currentBlock.getBlock().getConnections()) {

            GraphicalBlock destinationBlock = getGraphicalBlock(Connection.getDestinationBlockId());
            // System.err.println("current=" + currentBlock.getId() + " dest=" +
            // destinationBlock.getId());

            if (destinationBlock != null) {

                int row = currentRow;
                int column = currentColumn;

                // if the has been already set into the grid, just skips
                if (isBlockInGrid(destinationBlock)) {
                    continue;
                }

                // puts next block into the grid
                while (!isGridCellEmpty(row, column)) {

                    // first tries to put it in the next row
                    if (isGridCellEmpty(row + 1, column)) {
                        row++;
                    }
                    // if the next row is not empty
                    else {

                        // tries to put it in the next column
                        column++;
                    }
                }

                putBlockInGrid(destinationBlock, row, column, g);
            }
        }
    }

    public void draw(Graphics2D g2d) {

        g2d.setFont(Constants.DIAGRAM_FONT);
        putBlocksInGrid(g2d);
        setGridSize();

        for (String key : graphicalBlocks.keySet()) {

            GraphicalBlock block = getGraphicalBlock(key);
            block.paint(g2d);
        }


        if (Constants.SHOW_GRID) {

            g2d.setColor(gridColor);
            for (int x = 0; x < maxSize; x++) {
                for (int y = 0; y < maxSize; y++) {

                    g2d.drawLine(0, y * cellHeight, 1500, y * cellHeight);
                    g2d.drawLine(x * cellWidth, 0, x * cellWidth, 1500);
                }
            }
        }
    }

    private boolean isBlockInGrid(GraphicalBlock block) {

        for (GraphicalBlock[] rows : grid) {
            for (GraphicalBlock item : rows) {
                if (item == block) {
                    return true;
                }
            }
        }
        return false;
    }

    public Block getBlockFromCoordinates(int x, int y) {

        for (String id : graphicalBlocks.keySet()) {

            GraphicalBlock graphicalBlock = graphicalBlocks.get(id);
            if (graphicalBlock.getRect().contains(x, y)) {
                return graphicalBlock.getBlock();
            }
        }

        return null;
    }

    // /**
    // * two blocks are on different columns if the preceding block is a control block and the
    // current
    // * block is the second edge of the preceding block.
    // *
    // * @param currentBlock
    // * @param precedingBlock
    // * @return
    // */
    // private boolean isOnDifferentColumn(GraphicalBlock currentBlock, GraphicalBlock
    // precedingBlock) {
    //
    // return precedingBlock.getBlock().getConnections().size() > 1 &&
    // precedingBlock.getBlock().getConnections().get(1).getDestinationBlockId().equals(currentBlock.getId());
    // }
    private GraphicalBlock getGraphicalBlock(String destinationBlockId) {

        return graphicalBlocks.get(destinationBlockId);
    }

    private boolean isGridCellEmpty(int row, int column) {

        return grid[row][column] == null;
    }

    public void setFlowChart(FlowChart flowChart) {

        this.flowChart = flowChart;
    }

    public int getRowsNumber() {

        int rows = 0;
        for (String id : graphicalBlocks.keySet()) {

            GraphicalBlock graphicalBlock = graphicalBlocks.get(id);
            if (graphicalBlock.getRow() > rows) {
                rows = graphicalBlock.getRow();
            }
        }

        return rows;
    }

    public int getColumnsNumber() {

        int columns = 0;
        for (String id : graphicalBlocks.keySet()) {

            GraphicalBlock graphicalBlock = graphicalBlocks.get(id);
            if (graphicalBlock.getColumn() > columns) {
                columns = graphicalBlock.getColumn();
            }
        }

        return columns;
    }

    public Rectangle getDrawingRectangle() {

        Rectangle rectangle = new Rectangle(0, 0, cellWidth * getColumnsNumber(), cellHeight * getRowsNumber());

        return rectangle;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }
}
