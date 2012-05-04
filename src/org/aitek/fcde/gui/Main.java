package org.aitek.fcde.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import org.aitek.fcde.diagram.Block;
import org.aitek.fcde.utils.CollectionUtils;
import org.aitek.fcde.utils.Constants;
import org.aitek.fcde.utils.FileUtils;
import org.aitek.fcde.utils.GenericFileFilter;
import org.aitek.fcde.utils.SwingUtils;

public class Main extends JFrame implements ActionListener, KeyListener {

	static final long serialVersionUID = 0;

	private JLabel jlStatus;
	private FlowChartPanel diagramPanel;
	private JTextArea diagramText;

	private ArrayList<String> alFiles;
	private String currentFileName;
	private JMenuItem saveMenuItem;
	private boolean isCtrlPressed;

	private FlowChartDrawing flowChartDrawing;

	public Main() throws Exception {

		super("Flow Chart Diagram Editor");
		setSize(800, 600);
		SwingUtils.centerFrame(this);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		try {

			// creates the menu bar
			JMenuBar menuBar = new JMenuBar();

			// Create the file menu
			JMenu menu = new JMenu("File");
			menuBar.add(menu);
			menu.setMnemonic(KeyEvent.VK_F);

			// Create the file menu items
			JMenuItem item = new JMenuItem("New");
			item.setMnemonic(KeyEvent.VK_N);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (true) {

						// show the message dialog to confirm a new site
						int resp = JOptionPane.showConfirmDialog(null, "This site was been modified. Do you want to save it?", "Save Site", JOptionPane.YES_NO_CANCEL_OPTION);

						if (resp == JOptionPane.YES_OPTION) {

							try {

								// save the old site
								saveChart(currentFileName);
							}
							catch (Exception ex) {
								SwingUtils.showFormError(ex);
							}

							// and repaints the site
							repaint();
						}
						else if (resp == JOptionPane.CANCEL_OPTION) {
							return;
						}

					}

					try {
						// creates a new website
						// and repaints the screen
						repaint();
					}
					catch (Exception ex) {
						SwingUtils.showFormError(ex);
					}

					// and refresh the screen
					repaint();

				}
			});

			item.addActionListener(this);
			menu.add(item);

			item = new JMenuItem("Open");
			item.setMnemonic(KeyEvent.VK_O);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						// creates the filechooser
						JFileChooser fc = new JFileChooser();

						// sets the filter
						fc.setFileFilter(new GenericFileFilter("fcd", "Flow Chart Diagram"));

						// and gets the return value of the file chooser dialog
						int returnVal = fc.showOpenDialog(Main.this);

						// if the user pressed "ok"
						if (returnVal != JFileChooser.APPROVE_OPTION) {
							return;
						}

						try {

							loadChart(fc.getSelectedFile().getAbsolutePath());
						}
						catch (Exception ex) {
							SwingUtils.showFormError(ex);
						}
					}
					catch (Exception ex) {
						SwingUtils.showFormError(ex);
					}
				}
			});
			menu.add(item);

			JMenu jmSub = new JMenu("Recently Opened");
			jmSub.setMnemonic(KeyEvent.VK_R);

			// gets the recent files from the preferences
			Preferences p = Preferences.userRoot();
			alFiles = CollectionUtils.toArrayList(p.get(Constants.RECENT_FILES_KEY, ""), "|", false);
			JMenuItem subItem = null;

			// cycles on the recent files
			for (int j = 0; j < alFiles.size(); j++) {

				subItem = new JMenuItem(alFiles.get(j));
				subItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						try {
							loadChart(e.getActionCommand());
						}
						catch (Exception ex) {
							SwingUtils.showFormError(ex);
						}

					}
				});

				jmSub.add(subItem);
			}
			menu.add(jmSub);

			menu.addSeparator();

			saveMenuItem = new JMenuItem("Save");
			saveMenuItem.setMnemonic(KeyEvent.VK_S);
			saveMenuItem.setEnabled(false);
			saveMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						// saves the diagram
						saveChart(currentFileName);
					}
					catch (Exception ex) {
						SwingUtils.showFormError(ex);
					}

				}
			});
			menu.add(saveMenuItem);

			item = new JMenuItem("Save As");
			item.setMnemonic(KeyEvent.VK_V);

			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {

						// creates the filechooser
						JFileChooser fc = new JFileChooser();

						// and gets the return value of the file chooser dialog
						int returnVal = fc.showSaveDialog(Main.this);

						// if the user pressed "ok"
						if (returnVal == JFileChooser.APPROVE_OPTION) {

							// sets the filename
							currentFileName = fc.getSelectedFile().getPath();

							// checks the extension
							if (!currentFileName.toLowerCase().endsWith(".fcd")) {
								currentFileName += ".fcd";
							}

							// checks for file existance
							File f = new File(currentFileName);
							if (f.exists()) {

								// if already exists, asks the user to overwrite the file
								if (JOptionPane.showConfirmDialog(null, "The file " + currentFileName + " already exists. Do you want to overwrite it?", "Save Site", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
									return;
								}
							}
						}
						else {
							return;
						}

						// saves the site as a new one
						saveChart(currentFileName);

					}
					catch (Exception ex) {
						SwingUtils.showFormError(ex);
					}
				}
			});
			menu.add(item);

			menu.add(item);
			menu.addSeparator();
			item = new JMenuItem("Exit");
			item.setMnemonic(KeyEvent.VK_X);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					System.exit(0);

				}
			});
			menu.add(item);

			item.addActionListener(this);

			menu = new JMenu("Tools");
			menuBar.add(menu);
			menu.setMnemonic(KeyEvent.VK_T);

			// Create the file menu items
			item = new JMenuItem("Export as PNG");
			item.setMnemonic(KeyEvent.VK_X);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					exportAsPng();
				}
			});
			menu.add(item);

			menu.addSeparator();

			// Create the file menu items
			item = new JMenuItem("Option");
			item.setMnemonic(KeyEvent.VK_O);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					PreferencesForm preferencesForm = new PreferencesForm(Main.this);
					preferencesForm.setVisible(true);
				}
			});
			menu.add(item);

			// Install the menu bar in the frame
			setJMenuBar(menuBar);

			flowChartDrawing = new FlowChartDrawing();
			diagramPanel = new FlowChartPanel(flowChartDrawing, this);
			JScrollPane jspTop = new JScrollPane(diagramPanel);
			jspTop.getVerticalScrollBar().setUnitIncrement(16);
			diagramPanel.setPreferredSize(new Dimension(2000, 5000));

			diagramText = new JTextArea();
			diagramText.addKeyListener(this);

			JScrollPane jspBottom = new JScrollPane(diagramText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			jspBottom.setWheelScrollingEnabled(true);

			getContentPane().setLayout(new BorderLayout());

			// creates the divider for the two panes
			JSplitPane spDivider = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jspTop, jspBottom);
			spDivider.setDividerLocation(400);
			spDivider.setOneTouchExpandable(true);
			getContentPane().add(spDivider, BorderLayout.CENTER);

			// sets the status bar
			jlStatus = new JLabel(" Ready");
			getContentPane().add("South", jlStatus);

			// allow this panel to get focus
			this.setFocusable(true);

			loadOptions();

			// repaints everything and starts
			setVisible(true);

		}
		catch (Exception e) {
			SwingUtils.showFormError(e);
		}

	}

	/**
	 * 
	 */
	private void exportAsPng() {

		try {

			// gets the filename from user
			JFileChooser fc = new JFileChooser();

			// and gets the return value of the file chooser dialog
			int returnVal = fc.showSaveDialog(Main.this);

			// if the user pressed "cancel" skips all
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return;
			}

			String exportFilename = fc.getSelectedFile().getAbsolutePath();
			if (!exportFilename.toLowerCase().endsWith(".png")) {
				exportFilename += ".png";
			}

			// sets the cursor
			setCursor(Constants.CURSOR_WAIT);
			diagramPanel.repaint();
			Rectangle drawingRect = flowChartDrawing.getDrawingRectangle();

			// creates a buffered image
			BufferedImage bi = new BufferedImage(diagramPanel.getWidth(), diagramPanel.getHeight(), BufferedImage.TYPE_INT_RGB);

			// sets the bg color of the panel to white, for better printing
			diagramPanel.setBackground(Color.WHITE);

			// copies the content of the panel to the bufferedimage
			diagramPanel.paint(bi.getGraphics());

			// reset the original bg color
			diagramPanel.setBackground(Constants.BACKGROUND_COLOR);

			// gets only the part of image with tables
			BufferedImage bi2 = bi.getSubimage(drawingRect.x, drawingRect.y, drawingRect.width, drawingRect.height);

			// writes the file to disk
			ImageIO.write(bi2, "png", new File(exportFilename));

			setCursor(Constants.CURSOR_DEFAULT);
		}
		catch (Exception ex) {
			setCursor(Constants.CURSOR_DEFAULT);
			SwingUtils.showFormError(ex);
		}
	}

	private void loadOptions() {

		Preferences p = Preferences.userRoot();
		Constants.FONT_NAME = p.get(Constants.PREFERENCES_FONT_NAME, Constants.FONT_NAME);
		Constants.FONT_SIZE = Integer.parseInt(p.get(Constants.PREFERENCES_FONT_SIZE, "" + 12));
		Constants.COLUMN_DISTANCE = Integer.parseInt(p.get(Constants.PREFERENCES_COLUMN_DISTANCE, "" + 150));
		Constants.ROW_DISTANCE = Integer.parseInt(p.get(Constants.PREFERENCES_ROW_DISTANCE, "" + 75));
		Constants.BLOCK_WIDTH = Integer.parseInt(p.get(Constants.PREFERENCES_BLOCK_MAX_WIDTH, "" + 10));
		Constants.DIAGRAM_FONT = new Font(Constants.FONT_NAME, Font.PLAIN, Constants.FONT_SIZE);
		Constants.FLOW_DESCRIPTION_DISTANCE = Integer.parseInt(p.get(Constants.PREFERENCES_FLOW_DESCRIPTION_DISTANCE, "" + 60));
		Constants.ARROW_SIZE = Integer.parseInt(p.get(Constants.PREFERENCES_ARROW_SIZE, "" + 10));

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) throws Exception {

		// sets antialiasing
		System.setProperty("swing.aatext", "true");

		// Show tool tips after one quarter of a second
		ToolTipManager.sharedInstance().setInitialDelay(250);

		// tries to set the look and feel
		try {
			// set system look&feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

		}
		catch (Exception e) {
			// if there are problems (old JDKs, etc), it doesn't matter
		}

		try {

			// creates the main form of the application
			Main main = new Main();

			// if there are arguments, it assumes it's a config filename
			if (args.length > 0) {

				// and loads it
				main.loadChart(args[0]);
				main.setCurrentFileName(args[0]);
			}

			// show the main window
			main.setVisible(true);
		}
		catch (Exception e) {
			SwingUtils.showFormError(e);
		}
	}

	private void loadChart(String fileName) throws Exception {

		diagramText.setText(FileUtils.readTextFile(fileName, "UTF-8"));
		parseDiagram();

		// save as a recently opened file
		Preferences p = Preferences.userRoot();
		// String strValue = p.get(Constants.RECENT_FILES_KEY, "");
		p.put(Constants.RECENT_FILES_KEY, fileName);

		diagramText.setCaretPosition(0);

		// invalidate();
	}

	// updates the diagram and prints a status (ok or error) message on status bar
	public void parseDiagram() {

		updateStatusBar(flowChartDrawing.updateDrawing(diagramText.getText()));
	}

	public void updateStatusBar(String message) {

		if (message != null) {

			jlStatus.setText(message);
			invalidate();
			repaint();
		}
	}

	public void highlightToBlock(Block block) {

		int endPosition = diagramText.getText().indexOf(block.getId() + "]") + (block.getId() + "]").length();
		int startPosition = endPosition;
		while (diagramText.getText().charAt(startPosition) != '[') {
			startPosition--;
		}

		removeHighlights(diagramText);
		Highlighter hilite = diagramText.getHighlighter();

		try {
			hilite.addHighlight(startPosition, endPosition, new MyHighlightPainter(Color.LIGHT_GRAY));
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
		diagramText.setCaretPosition(startPosition);
	}

	// A private subclass of the default highlight painter
	class MyHighlightPainter extends DefaultHighlightPainter {

		public MyHighlightPainter(Color color) {

			super(color);
		}
	}

	// Removes only our private highlights
	public void removeHighlights(JTextComponent textComp) {

		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			hilite.removeHighlight(hilites[i]);
		}
	}

	private void saveChart(String fileName) throws Exception {

		FileUtils.writeTextFile(fileName, diagramText.getText(), "UTF-8");
		updateStatusBar("File [" + fileName + "] saved.");
	}

	public void setCurrentFileName(String currentFileName) {

		this.currentFileName = currentFileName;
	}

	public String getCurrentFileName() {

		return currentFileName;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent ke) {

		saveMenuItem.setEnabled(true);

		// checks if the CTRL key is pressed
		if (ke.isControlDown()) {
			isCtrlPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		isCtrlPressed = false;
		parseDiagram();
		diagramPanel.invalidate();
		invalidate();
	}

	public boolean isCtrlPressed() {

		return isCtrlPressed;
	}

}
