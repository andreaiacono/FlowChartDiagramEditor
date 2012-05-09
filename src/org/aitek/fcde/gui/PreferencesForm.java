package org.aitek.fcde.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.aitek.fcde.utils.Constants;
import org.aitek.fcde.utils.SwingUtils;

public class PreferencesForm extends JDialog implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 1L;
    // the components
    private JComboBox<String> jcFont;
    private Main main;
    private JSlider fontSizeSlider;
    private JSlider columnDistanceSlider;
    private JSlider blockDistanceSlider;
    private JSlider blockMaxWidthSlider;
    private JSlider arrowSizeSlider;
    private JSlider flowDescriptionDistanceSlider;
    private JCheckBox showGridCheckBox;

    public PreferencesForm(Main main) {

        super.setTitle("Preferences");
        this.setModal(false);
        this.main = main;

        init();
    }

    protected void setWindowTitle(String strTitle) {

        super.setTitle(strTitle);
    }

    public void init() {

        // sets the size and the location of the window
        setSize(340, 300);
        SwingUtils.centerFrame(this);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        int ww = 80, hh = 25;

        // creates the ok and cancel button
        JButton jbOk = new JButton("Close");
        jbOk.addActionListener(this);
        this.getRootPane().setDefaultButton(jbOk);
        add(jbOk);

        sl.putConstraint(SpringLayout.SOUTH, jbOk, -5, SpringLayout.SOUTH, this.getContentPane());
        sl.putConstraint(SpringLayout.EAST, jbOk, -5, SpringLayout.EAST, this.getContentPane());

        // fills the values
        Preferences p = Preferences.userRoot();

        JLabel jlName = new JLabel("Font Name: ");
        jlName.setPreferredSize(new Dimension(ww, hh));
        add(jlName);
        sl.putConstraint(SpringLayout.NORTH, jlName, 5, SpringLayout.NORTH, this.getContentPane());
        sl.putConstraint(SpringLayout.WEST, jlName, 5, SpringLayout.WEST, this.getContentPane());

        jcFont = new JComboBox<String>();
        SwingUtils.fillComboBox(jcFont, GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(), true,"");
        jcFont.setSelectedItem(p.get(Constants.PREFERENCES_FONT_NAME, "Arial"));
        jcFont.addActionListener(this);
        add(jcFont);
        sl.putConstraint(SpringLayout.NORTH, jcFont, 5, SpringLayout.NORTH, this.getContentPane());
        sl.putConstraint(SpringLayout.WEST, jcFont, 35, SpringLayout.EAST, jlName);
        sl.putConstraint(SpringLayout.EAST, jcFont, -5, SpringLayout.EAST, this.getContentPane());

        JLabel fontSizeLabel = new JLabel("Font size: ");
        add(fontSizeLabel);
        sl.putConstraint(SpringLayout.NORTH, fontSizeLabel, 18, SpringLayout.SOUTH, jcFont);
        sl.putConstraint(SpringLayout.WEST, fontSizeLabel, 5, SpringLayout.WEST, getContentPane());

        fontSizeSlider = new JSlider(5, 50);
        fontSizeSlider.setName(Constants.PREFERENCES_FONT_SIZE);
        fontSizeSlider.addChangeListener(this);
        fontSizeSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_FONT_SIZE, "11")));
        add(fontSizeSlider);

        sl.putConstraint(SpringLayout.NORTH, fontSizeSlider, 0, SpringLayout.SOUTH, jcFont);
        sl.putConstraint(SpringLayout.WEST, fontSizeSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, fontSizeSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel columnDistanceLabel = new JLabel("Columns distance: ");
        add(columnDistanceLabel);
        sl.putConstraint(SpringLayout.NORTH, columnDistanceLabel, 18, SpringLayout.SOUTH, fontSizeLabel);
        sl.putConstraint(SpringLayout.WEST, columnDistanceLabel, 5, SpringLayout.WEST, getContentPane());

        columnDistanceSlider = new JSlider(0, 500);
        columnDistanceSlider.setName(Constants.PREFERENCES_COLUMN_DISTANCE);
        columnDistanceSlider.addChangeListener(this);
        columnDistanceSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_COLUMN_DISTANCE, "80")));

        add(columnDistanceSlider);

        sl.putConstraint(SpringLayout.NORTH, columnDistanceSlider, 0, SpringLayout.SOUTH, fontSizeLabel);
        sl.putConstraint(SpringLayout.WEST, columnDistanceSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, columnDistanceSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel blockDistanceLabel = new JLabel("Row distance: ");
        add(blockDistanceLabel);
        sl.putConstraint(SpringLayout.NORTH, blockDistanceLabel, 18, SpringLayout.SOUTH, columnDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, blockDistanceLabel, 5, SpringLayout.WEST, getContentPane());

        blockDistanceSlider = new JSlider(0, 250);
        blockDistanceSlider.setName(Constants.PREFERENCES_ROW_DISTANCE);
        blockDistanceSlider.addChangeListener(this);
        blockDistanceSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_ROW_DISTANCE, "80")));
        add(blockDistanceSlider);

        sl.putConstraint(SpringLayout.NORTH, blockDistanceSlider, 0, SpringLayout.SOUTH, columnDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, blockDistanceSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, blockDistanceSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel blockMaxWidthLabel = new JLabel("Block max width: ");
        add(blockMaxWidthLabel);
        sl.putConstraint(SpringLayout.NORTH, blockMaxWidthLabel, 18, SpringLayout.SOUTH, blockDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, blockMaxWidthLabel, 5, SpringLayout.WEST, getContentPane());

        blockMaxWidthSlider = new JSlider(50, 250);
        blockMaxWidthSlider.setName(Constants.PREFERENCES_BLOCK_MAX_WIDTH);
        blockMaxWidthSlider.addChangeListener(this);
        blockMaxWidthSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_BLOCK_MAX_WIDTH, "80")));
        add(blockMaxWidthSlider);

        sl.putConstraint(SpringLayout.NORTH, blockMaxWidthSlider, 0, SpringLayout.SOUTH, blockDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, blockMaxWidthSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, blockMaxWidthSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel flowDescriptionDistanceLabel = new JLabel("Flow Label Distance: ");
        add(flowDescriptionDistanceLabel);
        sl.putConstraint(SpringLayout.NORTH, flowDescriptionDistanceLabel, 18, SpringLayout.SOUTH, blockMaxWidthLabel);
        sl.putConstraint(SpringLayout.WEST, flowDescriptionDistanceLabel, 5, SpringLayout.WEST, getContentPane());

        flowDescriptionDistanceSlider = new JSlider(0, 100);
        flowDescriptionDistanceSlider.setName(Constants.PREFERENCES_BLOCK_MAX_WIDTH);
        flowDescriptionDistanceSlider.addChangeListener(this);
        flowDescriptionDistanceSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_BLOCK_MAX_WIDTH, "80")));
        add(flowDescriptionDistanceSlider);

        sl.putConstraint(SpringLayout.NORTH, flowDescriptionDistanceSlider, 0, SpringLayout.SOUTH, blockMaxWidthLabel);
        sl.putConstraint(SpringLayout.WEST, flowDescriptionDistanceSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, flowDescriptionDistanceSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel arrowSizeLabel = new JLabel("Arrow Size: ");
        add(arrowSizeLabel);
        sl.putConstraint(SpringLayout.NORTH, arrowSizeLabel, 18, SpringLayout.SOUTH, flowDescriptionDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, arrowSizeLabel, 5, SpringLayout.WEST, getContentPane());

        arrowSizeSlider = new JSlider(3, 40);
        arrowSizeSlider.setName(Constants.PREFERENCES_ARROW_SIZE);
        arrowSizeSlider.addChangeListener(this);
        arrowSizeSlider.setValue(Integer.parseInt(p.get(Constants.PREFERENCES_ARROW_SIZE, "10")));
        add(arrowSizeSlider);

        sl.putConstraint(SpringLayout.NORTH, arrowSizeSlider, 0, SpringLayout.SOUTH, flowDescriptionDistanceLabel);
        sl.putConstraint(SpringLayout.WEST, arrowSizeSlider, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, arrowSizeSlider, -5, SpringLayout.EAST, this.getContentPane());

        JLabel showGridLabel = new JLabel("Show Grid: ");
        add(showGridLabel);
        sl.putConstraint(SpringLayout.NORTH, showGridLabel, 18, SpringLayout.SOUTH, arrowSizeLabel);
        sl.putConstraint(SpringLayout.WEST, showGridLabel, 5, SpringLayout.WEST, getContentPane());

        showGridCheckBox = new JCheckBox();
        showGridCheckBox.setName(Constants.PREFERENCES_SHOW_GRID);
        showGridCheckBox.addChangeListener(this);
        showGridCheckBox.setSelected(Boolean.parseBoolean(p.get(Constants.PREFERENCES_SHOW_GRID, "false")));
        add(showGridCheckBox);

        sl.putConstraint(SpringLayout.NORTH, showGridCheckBox, 14, SpringLayout.SOUTH, arrowSizeLabel);
        sl.putConstraint(SpringLayout.WEST, showGridCheckBox, 0, SpringLayout.WEST, jcFont);
        sl.putConstraint(SpringLayout.EAST, showGridCheckBox, -5, SpringLayout.EAST, this.getContentPane());
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        if (((Component) (e.getSource())).getName().equals(Constants.PREFERENCES_SHOW_GRID)) {

            Constants.SHOW_GRID = showGridCheckBox.isSelected();
            main.parseDiagram();
        }
        else {
            JSlider slider = (JSlider) e.getSource();
            if (slider.getName().equals(Constants.PREFERENCES_COLUMN_DISTANCE)) {
                Constants.COLUMN_DISTANCE = slider.getValue();
                main.parseDiagram();
            }
            else if (slider.getName().equals(Constants.PREFERENCES_ROW_DISTANCE)) {
                Constants.ROW_DISTANCE = slider.getValue();
                main.parseDiagram();
            }
            else if (slider.getName().equals(Constants.PREFERENCES_BLOCK_MAX_WIDTH)) {
                Constants.BLOCK_WIDTH = slider.getValue();
                main.parseDiagram();
            }
            else if (slider.getName().equals(Constants.PREFERENCES_FLOW_DESCRIPTION_DISTANCE)) {
                Constants.FLOW_DESCRIPTION_DISTANCE = slider.getValue();
                main.parseDiagram();
            }
            else if (slider.getName().equals(Constants.PREFERENCES_FONT_SIZE)) {
                Constants.DIAGRAM_FONT = new Font((String) jcFont.getSelectedItem(), Font.PLAIN, fontSizeSlider.getValue());
                main.parseDiagram();
            }
            else if (slider.getName().equals(Constants.PREFERENCES_ARROW_SIZE)) {
                Constants.ARROW_SIZE = slider.getValue();
                main.parseDiagram();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.err.println("e=" + e);
        // the user pressed the OK button
        if (e.getActionCommand().equals("Close")) {

            // saves into preferences the values
            Preferences p = Preferences.userRoot();
            p.put(Constants.PREFERENCES_FONT_NAME, (String) jcFont.getSelectedItem());
            p.put(Constants.PREFERENCES_FONT_SIZE, "" + fontSizeSlider.getValue());
            p.put(Constants.PREFERENCES_COLUMN_DISTANCE, "" + columnDistanceSlider.getValue());
            p.put(Constants.PREFERENCES_ROW_DISTANCE, "" + blockDistanceSlider.getValue());
            p.put(Constants.PREFERENCES_BLOCK_MAX_WIDTH, "" + blockMaxWidthSlider.getValue());
            p.put(Constants.PREFERENCES_FLOW_DESCRIPTION_DISTANCE, "" + flowDescriptionDistanceSlider.getValue());
            p.put(Constants.PREFERENCES_ARROW_SIZE, "" + arrowSizeSlider.getValue());
            p.put(Constants.PREFERENCES_SHOW_GRID, "" + showGridCheckBox.isSelected());

            // and closes the windows
            this.dispose();
        }
        // the user pressed the cancel button
        else if (e.getActionCommand().equals("Cancel")) {

            // just closes the window
            this.dispose();
        }
        else if (e.getActionCommand().equals("comboBoxChanged")) {

            Constants.DIAGRAM_FONT = new Font((String) jcFont.getSelectedItem(), Font.PLAIN, fontSizeSlider.getValue());
            main.parseDiagram();
        }
    }
}
