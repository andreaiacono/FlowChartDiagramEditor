package org.aitek.fcde.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.UIManager;

public class Constants {

	public static final String RECENT_FILES_KEY = "FCDE - RecentFiles";

	public static final String SWITCH_KEYWORD = "SWITCH";
	public static final String NEXT_KEYWORD = "NEXT";

	// TODO: questo dovrebbe essere la description del connection block con la lunghezza maggiore
	public static int COLUMN_DISTANCE = 120;
	public static int ROW_DISTANCE = 80;
	public static int BLOCK_WIDTH = 100;
	public static int FLOW_DESCRIPTION_DISTANCE = 15;
	public static int ARROW_SIZE = 10;
	public static boolean SHOW_GRID = false;

	public static String PREFERENCES_FONT_NAME = "FontName";
	public static String PREFERENCES_FONT_SIZE = "FontSize";
	public static String PREFERENCES_COLUMN_DISTANCE = "ColumnDistance";
	public static String PREFERENCES_FLOW_DESCRIPTION_DISTANCE = "FlowDescriptionDistance";
	public static String PREFERENCES_ROW_DISTANCE = "BlockDistance";
	public static String PREFERENCES_BLOCK_MAX_WIDTH = "BlockMaxSize";
	public static String PREFERENCES_ARROW_SIZE = "ArrowSize";
	public static String PREFERENCES_SHOW_GRID = "ShowGrid";

	public static String FONT_NAME = ((Font) UIManager.get("Label.font")).getName();
	public static int FONT_SIZE = 12;
	public static Font DIAGRAM_FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);

	public static final float MOUSEWHEEL_ZOOM = 15f;
	public static final int MOUSEWHEEL_SCROLL_Y = 30;

	public static final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
	public static final Cursor CURSOR_WAIT = new Cursor(Cursor.WAIT_CURSOR);

	public static final Color BACKGROUND_COLOR = new Color(200, 200, 200);
}
