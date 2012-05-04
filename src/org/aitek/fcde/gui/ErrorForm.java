package org.aitek.fcde.gui;

/*
 * Created on Sep 3, 2004 by andrea
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import org.aitek.fcde.utils.IoUtils;
import org.aitek.fcde.utils.SwingUtils;

/**
 * @author andrea
 * 
 */
public class ErrorForm extends JEscapeDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	// the components
	private JTextArea jtaText;
	private JButton jbClose, jbDetails;
	private JLabel jlMessage;
	private JScrollPane pane;
	private String strMessage, strStack;

	public ErrorForm(Exception ex) {

		super();
		setWindowTitle("Error Message");
		setModal(true);
		// sets the size and the location of the window
		setSize(600, 100);
		setResizable(true);
		SwingUtils.centerFrame(this);
		this.strMessage = ex.getMessage();
		strStack = IoUtils.stackTraceToString(ex);
		init();
	}

	public ErrorForm(String strMessage, String strStack) {

		super();
		setWindowTitle("Error Message");
		setModal(true);
		// sets the size and the location of the window
		setSize(600, 100);
		setResizable(true);
		SwingUtils.centerFrame(this);
		this.strMessage = strMessage;
		this.strStack = strStack;
		init();
	}

	private void init() {

		if (strMessage == null) strMessage = "NullPointerException";

		jlMessage = new JLabel("An error has occurred: " + strMessage, JLabel.CENTER);
		jlMessage.setIconTextGap(10);
		Icon icon = (Icon) UIManager.getLookAndFeel().getDefaults().get("OptionPane.errorIcon");
		jlMessage.setIcon(icon);
		jlMessage.setPreferredSize(new Dimension(100, 40));

		jtaText = new JTextArea(10, 50);
		pane = new JScrollPane(jtaText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jtaText.setEditable(false);
		pane.setVisible(false);

		// prints the error on the textarea
		jtaText.append("\n\nERROR: " + strMessage);
		jtaText.append("\n\n" + strStack);
		jtaText.setCaretPosition(0);

		jbClose = new JButton("Close");
		jbClose.addActionListener(this);

		jbDetails = new JButton("Show Details");
		jbDetails.addActionListener(this);

		SpringLayout sl = new SpringLayout();
		setLayout(sl);

		sl.putConstraint(SpringLayout.WEST, jlMessage, 10, SpringLayout.WEST, this.getContentPane());
		sl.putConstraint(SpringLayout.NORTH, jlMessage, 5, SpringLayout.NORTH, this.getContentPane());
		sl.putConstraint(SpringLayout.EAST, jlMessage, -5, SpringLayout.EAST, this.getContentPane());

		sl.putConstraint(SpringLayout.WEST, pane, 5, SpringLayout.WEST, this.getContentPane());
		sl.putConstraint(SpringLayout.NORTH, pane, 5, SpringLayout.SOUTH, jlMessage);
		sl.putConstraint(SpringLayout.EAST, pane, -5, SpringLayout.EAST, this.getContentPane());
		sl.putConstraint(SpringLayout.SOUTH, pane, -5, SpringLayout.NORTH, jbClose);

		sl.putConstraint(SpringLayout.EAST, jbClose, -5, SpringLayout.EAST, this.getContentPane());
		sl.putConstraint(SpringLayout.SOUTH, jbClose, -5, SpringLayout.SOUTH, this.getContentPane());

		sl.putConstraint(SpringLayout.WEST, jbDetails, 5, SpringLayout.WEST, this.getContentPane());
		sl.putConstraint(SpringLayout.SOUTH, jbDetails, -5, SpringLayout.SOUTH, this.getContentPane());

		add(pane);
		add(jbClose);
		add(jbDetails);
		add(jlMessage);

	}

	protected void setWindowTitle(String strTitle) {

		super.setTitle(strTitle);
	}

	public void actionPerformed(ActionEvent e) {

		// the user pressed the cancel button
		if (e.getActionCommand().equals("Close")) {

			// just closes the window
			this.dispose();
		}
		// the user pressed the show button
		else if (e.getActionCommand().equals("Show Details")) {

			if (pane.isVisible()) {
				this.setSize(650, 100);
				pane.setVisible(false);
				jbDetails.setText("Show Details");
			}
			else {
				this.setSize(650, 500);
				pane.setVisible(true);
				jbDetails.setText("Hide Details");
			}

			repaint();
		}
		// the user pressed the hide button
		else if (e.getActionCommand().equals("Hide Details")) {

			if (pane.isVisible()) {
				this.setSize(650, 100);
				pane.setVisible(false);
				jbDetails.setText("Show Details");
			}
			else {
				this.setSize(650, 500);
				pane.setVisible(true);
				jbDetails.setText("Hide Details");
			}

			repaint();
		}
	}

}