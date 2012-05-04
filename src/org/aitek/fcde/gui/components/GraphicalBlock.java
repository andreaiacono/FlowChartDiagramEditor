package org.aitek.fcde.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.diagram.Block.Type;
import org.aitek.fcde.gui.Side;
import org.aitek.fcde.utils.Constants;
import org.aitek.fcde.utils.SwingUtils;

public class GraphicalBlock {

	protected Rectangle rect;
	protected int column;
	protected int row;
	protected ArrayList<String> textRows;
	protected ArrayList<GraphicalConnection> graphicalConnections;
	protected Block block;

	protected enum ConnectingDirection {
		FROM, TO;
	}

	public GraphicalBlock(Block block) {

		this.block = block;
		rect = new Rectangle();
		graphicalConnections = new ArrayList<GraphicalConnection>();
	}

	public void appendLine(String innerText) {

		if (block.getInnerText() == null || block.getInnerText().length() == 0) {
			block.setInnerText(innerText);
		}
		else {
			block.setInnerText(block.getInnerText() + "\n" + innerText);
		}
	}

	public void paint(Graphics2D g) {

		for (GraphicalConnection graphicalConnection : graphicalConnections) {
			graphicalConnection.paint(g);
		}
	}

	protected void drawCenteredText(Graphics2D g, int y, String text) {

		int rowWidth = SwingUtils.getStringWidth(g, Constants.DIAGRAM_FONT, text);
		g.drawString(text, rect.x + (rect.width - rowWidth) / 2, y);
	}

	protected void drawText(Graphics2D g) {

		g.setColor(Color.BLACK);

		int textHeight = rect.y + (Constants.FONT_SIZE * 2);
		if (block.getType() == Type.CONTROL) {
			textHeight += rect.height / 6;
		}
		if (textRows != null) {
			for (String row : textRows) {
				drawCenteredText(g, textHeight, row);
				textHeight += Constants.FONT_SIZE * 1.2;
			}
		}
	}

	public void setCoords(int x, int y) {

		rect.x = x;
		rect.y = y;
	}

	public void setSize(Graphics g) {

		textRows = SwingUtils.splitString(block.getInnerText(), g, Constants.BLOCK_WIDTH);
		textRows.add("[" + getId() + "] col=" + column + " - row= " + row);
		rect.width = SwingUtils.getStringsMaxWidth(textRows, g, Constants.DIAGRAM_FONT) + Constants.FONT_SIZE * 2;
		rect.height = ((textRows.size() + 2) * (Constants.FONT_SIZE + 2));

		// if the block is a control or a IO one, we need to enlarge the size
		// because of the particular shapes
		if (block.getType() == Type.CONTROL) {
			rect.width *= 1.5;
			rect.height *= 1.5;
		}
		else if (block.getType() == Type.IO) {
			rect.width *= (1 + IoBlock.DELTA);
		}
	}

	/**
	 * returns the connecting point for the specified edge
	 * 
	 * @return
	 */
	public Point getConnectingPoint(Side side, ConnectingDirection direction) {

		if (direction == ConnectingDirection.TO) {

			switch (side) {

				case RIGHT:
					return new Point(rect.x + rect.width, rect.y + rect.height / 2);
				case LEFT:
					return new Point(rect.x, rect.y + rect.height / 2);
				case UP:
					return new Point(rect.x + rect.width / 2, rect.y);
				case DOWN:
					return new Point(rect.x + rect.width / 2, rect.y + rect.height);
			}
		}
		else {
			switch (side) {

				case RIGHT:
					return new Point(rect.x, rect.y + rect.height / 2);
				case LEFT:
					return new Point(rect.x + rect.width, rect.y + rect.height / 2);
				case UP:
					return new Point(rect.x + rect.width / 2, rect.y + rect.height);
				case DOWN:
					return new Point(rect.x + rect.width / 2, rect.y);
			}
		}
		return null;
	}

	public void addGraphicalConnection(GraphicalConnection graphicalConnection) {

		this.graphicalConnections.add(graphicalConnection);
	}

	public int getHeight() {

		return rect.height;
	}

	public int getWidth() {

		return rect.width;

	}

	public int getColumn() {

		return column;
	}

	public int getRow() {

		return row;
	}

	public void setRow(int row) {

		this.row = row;
	}

	public void setColumn(int column) {

		this.column = column;
	}

	public Rectangle getRect() {

		return rect;
	}

	public int getX() {

		return rect.x;
	}

	public int getY() {

		return rect.y;
	}

	public String getId() {

		return block.getId();
	}

	public Block getBlock() {

		return block;
	}

}