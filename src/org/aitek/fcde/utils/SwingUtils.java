package org.aitek.fcde.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;

import org.aitek.fcde.gui.ErrorForm;

public class SwingUtils {

    /**
     * returns a Color object from RGB values
     *
     * @param strColor a formatted string with the RGB values separated by comma
     * - e.g. "123,24,255"
     * @return the corresponding Color object
     * @throws Exception
     */
    public static Color getColor(String strColor) throws Exception {

        // initializes the tokenizer with comma
        StringTokenizer st = new StringTokenizer(strColor, ",");

        // gets the three values
        int r = new Integer(st.nextToken()).intValue();
        int g = new Integer(st.nextToken()).intValue();
        int b = new Integer(st.nextToken()).intValue();

        // and returns the Color
        return new Color(r, g, b);
    }

    /**
     * fills the combobox with the values in the AL
     *
     * @param cb
     * @param alValues
     */
    public static void fillComboBox(JComboBox<String> cb, ArrayList<String> alValues) {

        // cycles on the values
        for (int j = 0; j < alValues.size(); j++) {

            // add the value to the CB
            cb.addItem(alValues.get(j));
        }

    }

    /**
     * fills the combobox with the values in the string array
     *
     * @param cb the combo box to fill
     * @param strValues the values used to fill the combo box
     */
    public static void fillComboBox(JComboBox<String> cb, String[] strValues, boolean isToClean, String strFirstElement) {

        // checks the combobox
        if (cb == null) {
            return;
        }

        // if specified, cleans all the combo
        if (isToClean) {
            cb.removeAllItems();
        }

        // if specified, adds the first element
        if (strFirstElement != null) {
            cb.addItem(strFirstElement);
        }

        // cycles on the values
        for (int j = 0; j < strValues.length; j++) {

            // add the value to the CB
            cb.addItem(strValues[j]);
        }

    }

    /**
     * fills the combobox with the values in the AL
     *
     * @param cb
     * @param alValues
     * @param isToClean if all the existing data must be deleted
     * @param strFirstElement the value of the first element, or null if it's
     * not desired
     */
    public static void fillComboBox(JComboBox<String> cb, ArrayList<String> alValues, boolean isToClean, String strFirstElement) {

        // checks the combobox
        if (cb == null) {
            return;
        }

        // if specified, cleans all the combo
        if (isToClean) {
            cb.removeAllItems();
        }

        // if specified, adds the first element
        if (strFirstElement != null) {
            cb.addItem(strFirstElement);
        }

        // cycles on the values
        for (int j = 0; j < alValues.size(); j++) {

            // add the value to the CB
            cb.addItem(alValues.get(j));
        }

    }

    public static void centerFrame(JFrame frame) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }

    public static void centerFrame(JDialog jd) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jd.getSize();
        jd.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }

    /**
     * inits the error form with the specified exceptio and shows it
     *
     * @param ex
     */
    public static void showFormError(Exception ex) {

        // creates the window with the error message
        ErrorForm ef = new ErrorForm(ex);

        // and shows it
        ef.setVisible(true);
    }

    /**
     * inits the error form with the specified exceptio and shows it
     *
     * @param
     */
    public static void showFormError(String strMessage, String strStack) {

        // creates the window with the error message
        ErrorForm ef = new ErrorForm(strMessage, strStack);

        // and shows it
        ef.setVisible(true);
    }

    /**
     * centers the viewpoint in the middle of the panel
     *
     * @param jp
     */
    public static void centerPanel(JPanel jp) {

        // position the visible part of the panel in the middle
        JViewport jv = (JViewport) jp.getParent();

        jv.setViewPosition(SwingUtils.getPaneCenter(jv));

    }

    /**
     * returns the specified component
     *
     * @param strName the name of the component to search
     * @param cmp an array of components
     * @return
     */
    public static Component getComponent(String strName, Component[] cmp) {

        // cycles on the components
        for (int j = 0; j < cmp.length; j++) {

            // gets the j-th name
            String strCmpName = cmp[j].getName();

            // if the names are equal, returns the j-th component
            if (strCmpName != null) {
                if (strCmpName.equals(strName)) {
                    return cmp[j];
                }
            }
        }

        // if arrives here, there's no such component
        return null;
    }

    /**
     * adds an escpae listener to the dialog, so that pressing escape key, the
     * dialog will close
     *
     * @param dialog
     */
    public static void addEscapeListener(final JDialog dialog) {

        ActionListener escListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dialog.dispose();
            }
        };

        dialog.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * find a components in a jdialog or a jpanel
     *
     * @param cnt the dialog or jpanel where to search
     * @param strName the name of the cmp to search
     * @return
     */
    public static Component findComponentByName(Container cnt, String strName) {

        // checks for name
        if (strName == null) {
            return null;
        }

        // cycles on the components of the container
        for (Component cmp : cnt.getComponents()) {

            // if the name of the next compoennet equals the searched one,
            if (strName.equals(cmp.getName())) {

                // returns it
                return cmp;
            }

            // if the component is a JRootPane
            if (cmp instanceof JRootPane) {

                // gets it
                JRootPane rpNested = (JRootPane) cmp;

                // and keeps searching inside it
                return findComponentByName(rpNested.getContentPane(), strName);
            }

            // if the component is a JPanel
            if (cmp instanceof JPanel) {

                // gets it
                JPanel jpNested = (JPanel) cmp;

                // and keeps searching inside it
                return findComponentByName(jpNested, strName);
            }
        }

        // if arrives here, no component with the specified name has been found
        return null;
    }

    /**
     * returns the coordinates of the center of the pane passed as a parameter
     *
     * @param jv the pane
     * @return the center of the pane
     */
    public static Point getPaneCenter(JViewport jv) {

        // inits the point
        Point p = new Point();

        // gets the dimensions of the pane
        int x = jv.getViewPosition().x;
        int y = jv.getViewPosition().y;
        int w = jv.getWidth() / 2;
        int h = jv.getHeight() / 2;

        // computes the center
        p.x = x + w;
        p.y = y + h;

        // and returns the point
        return p;
    }

    /**
     * writes a text file; if the file already exists, asks the user the confirm
     * to write returns true if the file has been written, false if not
     *
     * @param strFileName
     * @param strContent
     * @param callingComp
     * @throws Exception
     */
    // public static boolean confirmWriteTextFile(String strFileName, String strContent, String
    // strEncoding, Component callingComp, AbstractFileDeployer fd) throws Exception {
    //
    // // checks if the file exists
    // if (fd.isFileExisting(strFileName)) {
    //
    // // asks the user if wants to overwrite the file
    // if (JOptionPane.showConfirmDialog(callingComp, "The file '" + strFileName +
    // "' already exists.\nDo you want to overwrite it?", "Confirm Overwrite",
    // JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION) {
    //
    // // if the user doesn't want, just returns
    // return false;
    // }
    // }
    //
    // // checks for existance of directory
    // FileUtils.createDirectory(strFileName.substring(0,
    // strFileName.lastIndexOf(File.separatorChar)));
    //
    // // and writes or overwrites the file
    // fd.writeTextFile(strFileName, strContent, strEncoding);
    //
    // // and returns
    // return true;
    // }
    /**
     * computes the width of a string drawn on a canvas
     *
     * @param g the graphics object where to draw
     * @param f the font used to draw
     * @param strValue the string to draw
     * @return
     */
    public static int getStringWidth(Graphics g, Font f, String strValue) {

        // gets the metrics of the font
        FontMetrics fm = g.getFontMetrics(f);

        // and returns its width
        return (int) fm.getStringBounds(strValue, g).getWidth();
    }

    /**
     * computes the width of a string drawn on a canvas
     *
     * @param g the graphics object where to draw
     * @param f the font used to draw
     * @param strValue the string to draw
     * @return
     */
    public static int getStringHeight(Graphics g, Font f, String strValue) {

        if (f == null || g == null || strValue == null) {
            return 0;
        }

        // gets the metrics of the font
        FontMetrics fm = g.getFontMetrics(f);

        // and returns its width
        return (int) fm.getStringBounds(strValue, g).getHeight();
    }

    /**
     * returns the max width of all the string contained in the arraylist
     * specified
     *
     * @param al
     * @param g
     * @param fn
     * @return
     */
    public static int getStringsMaxWidth(ArrayList<String> al, Graphics g, Font fn) {

        int max = 0;

        // cycles on the AL
        for (int j = 0; j < al.size(); j++) {

            // gets the width of the j-th string
            int w = SwingUtils.getStringWidth(g, fn, al.get(j));

            // if is't bigger than the relative max,
            // the new max is the width of this line
            if (max < w) {
                max = w;
            }
        }

        return max;
    }

    /**
     * splits the query in multiple lines, according to the max width specified
     *
     * @param strQuery
     * @param maxwidth the max width of the lines to be split
     * @return
     */
    public static ArrayList<String> splitString(String strQuery, Graphics g, int maxwidth) {

        return splitString(strQuery, g, Constants.DIAGRAM_FONT, maxwidth);
    }

    /**
     * splits the query in multiple lines, according to the max width specified
     *
     * @param message
     * @param g the graphics where to draw
     * @param fn the font to use
     * @param maxwidth the max width of the lines to be split
     * @return
     */
    public static ArrayList<String> splitString(String message, Graphics g, Font fn, int maxwidth) {

        // inits the AL
        ArrayList<String> lines = new ArrayList<String>();

        // cycles the query; when the width of the substring
        // becomes greater than the maxwidth, breaks to the preceding white
        // space
        int cnt = 0;
        int start = 0;

        // cycles on every char of the query
        while (cnt < message.length()) {

            //System.err.println("snt=" + cnt + " start=" + start);

            // scans the query till the max width
            while (cnt < message.length() - 1 && message.charAt(cnt) != 10 && getStringWidth(g, fn, message.substring(start, cnt)) < maxwidth) {
                cnt++;
            }

            if (message.charAt(cnt) != 10 && cnt < message.length() - 1) {

                // if it's not the last line, gets the first space before this point
                while (message.charAt(cnt) != ' ' && cnt > 0) {
                    cnt--;
                }
            }
            else {

                cnt++;
            }

            // adds the line
            lines.add(message.substring(start, cnt));

            // if an unbreakable line (no spaces) if greater than the maxwidth,
            // it juts gets out
            if (start == cnt) {
                break;
            }

            // reset the counters
            start = cnt;
        }

        // for (int j = 0; j < lines.size(); j++) {
        //
        // String line = lines.get(j);
        //
        // if (line.indexOf('\n') > 0) {
        // lines.remove(j);
        // lines.add(j, line.substring(0, line.indexOf('\n')));
        // lines.add(j + 1, line.substring(line.indexOf('\n'), line.length()));
        // }
        // }

        // and returns the lines
        return lines;
    }

    /**
     * return an int array with x values of the specified points; if a null
     * Point object is inside the array, it returns the values collected before
     * the null Point.
     *
     * @param points
     * @return
     */
    public static int[] getXPoints(Point[] points) {
        int[] x = new int[points.length];

        for (int j = 0; j < points.length; j++) {
            
            if (points[j] == null) {
                return x;
            }
            x[j] = points[j].x;
        }

        return x;
    }

    /**
     * return an int array with y values of the specified points; if a null
     * Point object is inside the array, it returns the values collected before
     * the null Point.
     *
     * @param points
     * @return
     */
    public static int[] getYPoints(Point[] points) {
        int[] y = new int[points.length];

        for (int j = 0; j < points.length; j++) {

            if (points[j] == null) {
                return y;
            }
            y[j] = points[j].y;
        }

        return y;
    }
}
