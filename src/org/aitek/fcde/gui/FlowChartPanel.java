package org.aitek.fcde.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.event.MouseInputListener;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.utils.Constants;

public class FlowChartPanel extends JPanel implements MouseWheelListener, MouseInputListener {

	private static final long serialVersionUID = 1L;

	private FlowChartDrawing flowChartDrawing;
	private Main main;
	private float mf = 1.0f;

	public FlowChartPanel(FlowChartDrawing flowChartDrawing, Main main) {

		this.flowChartDrawing = flowChartDrawing;
		this.main = main;
		setSize(2000, 5000);
		setBackground(Constants.BACKGROUND_COLOR);
		setFocusable(true);

		addMouseWheelListener(this);
		addMouseListener(this);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		// gets the viewport of the website scrollpane
		JViewport jv = (JViewport) getParent();

		// gets the center of the page
		Point vp = jv.getViewPosition();

		// if it's zooming out
		if (e.getWheelRotation() < 0) {

			// if the ctrl key is pressed, changes the scale factor used to draw
			if (isCtrlPressed()) {

				// gets the coordinates of the mouse
				int msx = e.getX();
				int msy = e.getY();

				// sets the new multply factor of visualization
				mf -= mf / Constants.MOUSEWHEEL_ZOOM;

				if (mf == 0) {
					mf = 0.01F;
				}

				// centers the page
				vp.x -= msx / Constants.MOUSEWHEEL_ZOOM;
				vp.y -= msy / Constants.MOUSEWHEEL_ZOOM;
				if (vp.x < 0) {
					vp.x = 0;
				}
				if (vp.y < 0) {
					vp.y = 0;
				}
				jv.setViewPosition(vp);

			}
			else {
				// scrolls up
				vp.y -= Constants.MOUSEWHEEL_SCROLL_Y * mf;
				if (vp.y < 0) {
					vp.y = 0;
				}
				jv.setViewPosition(vp);
			}

		}
		// if it's zooming in
		else {

			// if the ctrl key is pressed, changes the scale factor used to
			// draw
			if (isCtrlPressed()) {
				mf += mf / Constants.MOUSEWHEEL_ZOOM;

				// gets the coordinates of the mouse
				int msx = e.getX();
				int msy = e.getY();

				// centers the selected page
				vp.x += msx / Constants.MOUSEWHEEL_ZOOM;
				vp.y += msy / Constants.MOUSEWHEEL_ZOOM;
				if (vp.x < 0) {
					vp.x = 0;
				}
				if (vp.y < 0) {
					vp.y = 0;
				}
				jv.setViewPosition(vp);

			}
			else {

				// scrolls down
				vp.y += Constants.MOUSEWHEEL_SCROLL_Y * mf;
				if (vp.y < 0) {
					vp.y = 0;
				}
				jv.setViewPosition(vp);
			}
		}

		// and repaints the canvas
		repaint();
	}

	public boolean isCtrlPressed() {

		return main.isCtrlPressed();
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.scale(mf, mf);
		try {
			flowChartDrawing.draw(g2d);
		}
		catch (Exception e) {
			e.printStackTrace();
			main.updateStatusBar(e.getMessage());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		Block block = flowChartDrawing.getBlockFromCoordinates(e.getX(), e.getY());
		if (block != null) {
			main.highlightBlock(block);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
