package org.aitek.fcde.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class StringUtils {

	private static String[] hexValues = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af", "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf", "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf", "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df", "e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef", "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff" };

	/**
	 * transforms every occurrence of a quote character "'" into a double quote "''" for SQL queries
	 * 
	 * @param strValue the string to be converted
	 * @return the converted string
	 */
	public static String addDoubleQuote(String strValue) {

		// if there's a value to convert
		if (strValue != null) {

			return strValue.replaceAll("\'", "\'\'");

			// StringBuffer sbValue=new StringBuffer("");
			// // cycles on every character of the stirng
			// for (int j=0;j<strValue.length();j++) {
			// // if it's a quote, adds a quote
			// if (strValue.charAt(j)=='\'') sbValue.append('\'');
			// // and copy every other character
			// sbValue.append(strValue.charAt(j));
			// }
			//
			// // and returns the converted string
			// return sbValue.toString();
		}
		// or null
		else return null;
	}

	/**
	 * converts all the \n and \t into spaces
	 * 
	 * @param strValue the string to be converted
	 * @return the converted string
	 */
	public static String newLinesTabsToSpaces(String strValue) {

		if (strValue == null) return null;

		strValue = strValue.replaceAll("\n", " ");
		strValue = strValue.replaceAll("\r", " ");
		strValue = strValue.replaceAll("\t", " ");

		// converts more than one spaces into one space
		strValue = strValue.replaceAll("  ", " ");
		strValue = strValue.replaceAll("   ", " ");
		strValue = strValue.replaceAll("    ", " ");

		return strValue;
	}

	/**
	 * converts a "\n" into a &lt;br&gt; tag.
	 * 
	 * @param strValue the string to be converted
	 * @return the converted string
	 */
	public static String CrLfToBr(String strValue) {

		// if there's a value to convert
		if (strValue != null && !"".equals(strValue)) {
			StringBuffer sbValue = new StringBuffer("");

			// cycles on every character of the string
			for (int j = 0; j < strValue.length() - 1; j++) {

				// if there's a CL/LF sequence, replaces it with a &lt;br&gt;
				// tag
				if ((strValue.charAt(j) == '\r' && strValue.charAt(j + 1) == '\n') || (strValue.charAt(j + 1) == '\r' && strValue.charAt(j) == '\n')) {
					sbValue.append("<br/>");
					j++;
				}
				// if there's just a newline, appends a <br/> tag
				else if (strValue.charAt(j) == '\n') sbValue.append("<br/>");
				// or copies the other character
				else sbValue.append(strValue.charAt(j));
			}
			sbValue.append(strValue.charAt(strValue.length() - 1));

			// and returns the converted string
			return sbValue.toString();
		}
		else return null;
	}

	/**
	 * Converts an array of bytes into a string
	 * 
	 * @param bt the array of bytes to be converted
	 * @return the string containing the bytes
	 */
	static public String bytesToString(byte bt[]) {

		// checks for null value of the array
		if (bt == null) return null;

		// inits thebuffer
		StringBuffer sbContent = new StringBuffer();

		// cycles on the array
		for (int j = 0; j < bt.length; ++j) {

			// adds to the left column the hex value of the byte
			sbContent.append((char) bt[j]);
		}

		// and returns it
		return sbContent.toString();
	}

	/**
	 * transforms a string into a string of the representation int hexadecimal format
	 * 
	 * @param bt the bytes to be converted
	 * @param n the number of bytes per row to be displayed
	 * @return the Hexadecimal representation
	 */
	static public String getHexFromString(String str, int n) {

		return getHexFromBytes(str.getBytes(), n);
	}

	/**
	 * transforms an array of byte into a string of the representation int hexadecimal format
	 * 
	 * @param bt the bytes to be converted
	 * @param n the number of bytes per row to be displayed
	 * @return the Hexadecimal representation
	 */
	static public String getHexFromBytes(byte bt[], int n) {

		// if no bytes are supplied, returns null
		if (bt == null) return null;

		StringBuffer sbHex = new StringBuffer();
		StringBuffer sbLeft = new StringBuffer();
		StringBuffer sbRight = new StringBuffer();

		// cycles on the array of byte
		for (int j = 0; j < bt.length; ++j) {

			// adds to the left column the hex value of the byte
			sbLeft.append(hexValues[(int) bt[j] & 0x000000ff] + " ");

			// and the the right column the value of the byte,
			if (bt[j] > 31) sbRight.append((char) bt[j]);
			// or a '.' if it's not printable
			else sbRight.append('.');

			// adds a space in the middle of the columns to make them more
			// readable
			if (j % (n / 2) == (n / 2) - 1) {
				sbLeft.append(" ");
				sbRight.append(" ");
			}

			// if the number of bytes to be displayed in a single row
			// has been reached
			if (j % n == n - 1) {
				// appends the two strings
				sbHex.append(sbLeft + "\t" + sbRight + "\n");

				// and clear them
				sbLeft.delete(0, sbLeft.length());
				sbRight.delete(0, sbRight.length());
			}
		}

		return sbHex.toString();
	}

	/**
	 * initalizes the servlet creating the DatabaseAdmin objet
	 * 
	 * @param strConfigFile the path and filename of the configuration file
	 * @return the object created public static DatabaseAdmin initServlet() { //throws
	 *         DbAdminException { return new DatabaseAdmin(false); } /** initalizes the servlet
	 *         creating the DatabaseAdmin objet
	 * @param strConfigFile the path and filename of the configuration file
	 * @return the object created public static DatabaseAdmin initServlet(String strConfigFile)
	 *         throws DbAdminException { // returns the created DatabaseAdmin object return new
	 *         DatabaseAdmin(strConfigFile); }
	 */

	public static String getWord(String strTotal) {

		// gets the position of the first space
		int pos = strTotal.indexOf(" ");
		// and, if there's a space, it returns the substring til the space
		if (pos != -1) return strTotal.substring(0, pos);
		// else the whole string
		else return strTotal;
	}

	/**
	 * concatenates the content of an array of string using a separator
	 * 
	 * @param strValues the string to concatenate
	 * @param strSeparator the separator for the strings
	 * @param hasToTrim if has to trim every single string
	 * @return a string with all the values
	 */
	public static String getStringFromStringArray(String[] strValues, String strSeparator, boolean hasToTrim) {

		// checks for null
		if (strValues == null) return null;

		// inits the buffer
		StringBuffer sbValues = new StringBuffer();

		// cycles on the strings
		for (int j = 0; j < strValues.length; j++) {

			// gets the j-th string
			String strValue = strValues[j];

			// if there's no value, skips to the next
			if (strValue == null) continue;

			// checks if has to append the separator
			if (j > 0) sbValues.append(strSeparator);

			// checks if has to trim the value
			if (hasToTrim) strValue = strValue.trim();

			// appends the j-th string to the buffer
			sbValues.append(strValue);
		}

		// and returns the buffer
		return sbValues.toString();
	}

	/**
	 * concatenates the content of an array of char to obtain a string
	 * 
	 * @param chValues the char to concatenate
	 * @return a string formed by the sequence of chars
	 */
	public static String getStringFromCharArray(char[] chValues) {

		// inits the buffer
		StringBuffer sbValues = new StringBuffer();

		// cycles on the strings
		for (int j = 0; j < chValues.length; j++) {

			sbValues.append(chValues[j]);
		}

		// and returns the buffer
		return sbValues.toString();
	}

	/**
	 * gets the value of the specified fields from the resultset and concatenates them to the output
	 * string
	 * 
	 * @param rs
	 * @param strFields
	 * @param strSeparator
	 * @return
	 */
	public static String getValuesFromResultSet(ResultSet rs, String strFields, String strFieldSeparator, String strValueSeparator) throws Exception {

		StringBuffer sbValues = new StringBuffer();

		// tokenizes the field names
		StringTokenizer stFields = new StringTokenizer(strFields, strFieldSeparator);

		// for every field
		while (stFields.hasMoreElements()) {

			// gets the field name from the tokenizer
			String strFieldName = stFields.nextToken().trim();

			// and appends its value from the resultset to the buffer
			sbValues.append(rs.getString(strFieldName) + strValueSeparator);
		}

		// removes the last value separator
		sbValues = sbValues.delete(sbValues.length() - strValueSeparator.length(), sbValues.length());

		// and returns it
		return sbValues.toString();

	}

	/**
	 * transform the specified string into a string that can fit into java string declaration
	 * (removes linefeeds, backscapes quotes, ..
	 * 
	 * @param strVal
	 * @return
	 */
	public static String toJavaString(String strVal) {

		// copies the string
		String strJava = strVal;

		// removes linefeed
		strJava = strJava.replace("\n", "");

		// backscapes the quotes
		strJava = strJava.replace("\"", "\\\"");

		// removes tabs
		strJava = strJava.replace("\t", "");

		// and returns it
		return strJava;
	}

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

		// gets the metrics of the font
		FontMetrics fm = g.getFontMetrics(f);

		// and returns its width
		return (int) fm.getStringBounds(strValue, g).getHeight();
	}

	/**
	 * returns the max width of all the string contained in the arraylist specified
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
			int w = StringUtils.getStringWidth(g, fn, al.get(j));

			// if is't bigger than the relative max,
			// the new max is the width of this line
			if (max < w) max = w;
		}

		return max;
	}

	/**
	 * splits the query in multiple lines, according to the max width specified
	 * 
	 * @param strQuery
	 * @param strHeader an optional parameter fot setting a text before the query (if null is not
	 *            considered)
	 * @param maxwidth the max width of the lines to be split
	 * @return
	 */
	public static ArrayList<String> splitString(String strQuery, String strHeader, Graphics g, int maxwidth) {

		return splitString(strQuery, strHeader, g, Constants.DIAGRAM_FONT, maxwidth);
	}

	/**
	 * splits the query in multiple lines, according to the max width specified
	 * 
	 * @param strQuery
	 * @param strHeader an optional parameter fot setting a text before the query (if null is not
	 *            considered)
	 * @param g the graphics where to draw
	 * @param fn the font to use
	 * @param maxwidth the max width of the lines to be split
	 * @return
	 */
	public static ArrayList<String> splitString(String strQuery, String strHeader, Graphics g, Font fn, int maxwidth) {

		// inits the AL
		ArrayList<String> alLines = new ArrayList<String>();

		// if a header was defined
		if (strHeader != null) strQuery = strHeader + " " + strQuery;

		// cycles the query; when the width of the substring
		// becomes greater than the maxwidth, breaks to the preceding white
		// space
		int cnt = 0;
		int start = 0;

		// cycles on every char of the query
		while (cnt < strQuery.length()) {

			// scans the query till the max width
			while (cnt < strQuery.length() && getStringWidth(g, fn, strQuery.substring(start, cnt)) < maxwidth)
				cnt++;

			// if it's not the last line, gets the first space before this point
			if (cnt < strQuery.length()) while (strQuery.charAt(cnt) != ' ' && cnt >= 0)
				cnt--;

			// adds the line
			alLines.add(strQuery.substring(start, cnt));

			// if an unbreakable line (no spaces) if greater than the maxwidth,
			// it juts gets out
			if (start == cnt) break;

			// reset the counters
			start = cnt;
		}

		for (int j = 0; j < alLines.size(); j++) {

			String strLine = alLines.get(j);

			if (strLine.indexOf('\n') > 0) {
				alLines.remove(j);
				alLines.add(j, strLine.substring(0, strLine.indexOf('\n')));
				alLines.add(j + 1, strLine.substring(strLine.indexOf('\n'), strLine.length()));
			}
		}

		// and returns the lines
		return alLines;
	}

	/**
	 * divide the text in multiple lines, according to the max width specified
	 * 
	 * @param strText the text to be computed
	 * @param strToInsert tthe string to insert into the strText
	 * @param char the maximum number of chars on a single line
	 * @return a string containing new lines characters every at most "char" character
	 */
	public static String insertStringEvery(String strText, String strToInsert, int chars) {

		// cycles the text; when the width of the substring
		// becomes greater than the maxwidth, breaks to the preceding white
		// space
		int cnt = chars;

		// inits the buffer
		StringBuffer sbText = new StringBuffer(strText);

		// cycles on every char of the query
		while (cnt < sbText.length()) {

			// if it's over the end, just jumps out
			if (cnt >= sbText.length() || sbText.length() > 1000) break;

			// if it's not over the end
			while (sbText.charAt(cnt) != ' ' && cnt > 0) {

				// decrements the counter
				cnt--;
			}

			// and inserts a new line char
			sbText.insert(cnt, strToInsert);

			// and increments the counter
			cnt += chars;
		}

		// and returns the lines
		return sbText.toString();
	}

	/**
	 * divide the text in multiple lines, according to the max width specified
	 * 
	 * @param strText the text to be computed
	 * @param char the maximum number of chars on a single line
	 * @return a string containing new lines characters every at most "char" character
	 */
	public static String insertNewLineEvery(String strValue, int chars) {

		// copy the text
		String strText = new String(strValue);

		// the number of lines to have
		int cnt = strText.length() / chars;

		// splits the message on multiple lines
		for (int j = 1; j < cnt; j++) {

			// sets the position
			int pos = chars * j;

			// skips the position till the next space
			while (pos != ' ' && pos < strText.length())
				pos++;

			// and inserts a newline
			strText = strText.substring(0, pos) + "\n" + strText.substring(pos);
		}

		// and returns the lines
		return strText;
	}

	/**
	 * substitues every quote charatcer with a backslah-wuote sequence
	 * 
	 * @param strValue
	 * @return
	 */
	public static String escapeQuotes(String strValue) {

		if (strValue != null) return strValue.replace("\"", "\\\"");
		else return null;

	}

	/**
	 * gets an AL of string values and transofrms them into a string with the declaration of a
	 * string array (e.g. String[] strTest = { "val1", "val2"}
	 * 
	 * @param alNames
	 * @return the string with the definition
	 */
	public static String getStringFromArrayList(ArrayList<String> alNames) {

		// inits the buffer
		StringBuffer sbDecl = new StringBuffer("String[] strValues = {");

		// cycles on the values
		for (int j = 0; j < alNames.size(); j++) {

			// appends the j-th value
			sbDecl.append("\"" + alNames.get(j) + "\"");

			// if is not the last one, appends a comma
			if (j < alNames.size() - 1) sbDecl.append(", ");
		}

		// appends the closing }
		sbDecl.append("}");

		// and returns it
		return sbDecl.toString();

	}

	public static String insertValueInString(String strSource, String strValue, String strSeparator, int maxValues) {

		// inits the buffer
		boolean isPresent = false;

		// gets the values as AL
		ArrayList<String> al = CollectionUtils.toArrayList(strSource, strSeparator, false);

		// cycles on the values
		for (int j = 0; j < al.size(); j++) {

			// gets the j-th value
			String strVal = al.get(j);

			// checks if the value is already present
			if (strVal.equals(strValue)) isPresent = true;

		}

		// if there are too many values, drops the first inserted
		if (al.size() == maxValues) strSource = strSource.substring(strSource.indexOf(strSeparator));

		// if is not present, insert the value passed
		if (!isPresent) strSource = strSource + strValue + strSeparator;

		// and returns it
		return strSource;
	}

	/**
	 * creates a string from an arraylist
	 * 
	 * @param alValues the values to be concatenated
	 * @param strSeparator the separator between one element and the other
	 * @return the string with the values seaparated by the strSeparator
	 */
	public static String getStringFromArrayList(ArrayList<?> alValues, String strSeparator) {

		// inits the buffer
		StringBuffer sbVal = new StringBuffer("");

		// cycles on the values of the AL
		for (int j = 0; j < alValues.size(); j++) {

			// if is not the first element, adds the separator
			if (j != 0) sbVal.append(strSeparator);

			// appends the j-th vlaur to the buffer
			sbVal.append(alValues.get(j));
		}

		// and returns it
		return sbVal.toString();
	}

	/**
	 * creates a string from an arraylist
	 * 
	 * @param alValues the values to be concatenated
	 * @param strSeparator the separator between one element and the other
	 * @param strFixedValue a value to be appended to each value of the ARRAYLIST
	 * @return the string with the values seaparated by the strSeparator
	 */
	public static String getStringFromArrayList(ArrayList<String> alValues, String strFixedValue, String strSeparator) {

		// inits the buffer
		StringBuffer sbVal = new StringBuffer();

		// cycles on the values of the AL
		for (int j = 0; j < alValues.size(); j++) {

			// if is not the first element, adds the separator
			if (j != 0) sbVal.append(strSeparator);

			// appends the j-th vlaur to the buffer
			sbVal.append(strFixedValue + alValues.get(j));

		}

		// and returns it
		return sbVal.toString();
	}

	/**
	 * formats a number with printf-like format
	 * 
	 * @param strFormat
	 * @param value
	 * @return
	 */
	public static String formatNumber(String strFormat, Object value) {

		// inits the buffer and the formatter
		StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);

		// executes the formattation
		f.format(strFormat, value);
		f.close();

		// and returns the formatted value
		return sb.toString();
	}

	/**
	 * transforms a byte array into a character array
	 * 
	 * @param b the byte array to be transformed
	 * @return a char array with the content of the specified byte array
	 */
	public static char[] bytesToChar(byte[] b) {

		// checks the array
		if (b == null) return null;

		// inits the counter and the char array
		int i = 0;
		char[] c = new char[b.length];

		// ByteArrayInputStream bais=new ByteArrayInputStream(b);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// PrintWriter p = new PrintWriter(baos);
		//		
		// reads the byte array
		while (i < b.length) {

			// writes the content of the byte array into the char array
			c[i] = (char) b[i];

			// increments i
			i++;
		}

		return c;
	}

	/**
	 * returns true if the string contains a number, else false
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean isNumber(String strValue) {

		try {
			new Float(strValue).floatValue();
			return true;
		}
		catch (Exception ex) {}

		return false;
	}

	/**
	 * returns true if the string contains an integer value, else false
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean isInteger(String strValue) {

		try {
			new Integer(strValue).intValue();
			return true;
		}
		catch (Exception ex) {}

		return false;
	}

	/**
	 * returns an array of string taken from the values of a properties
	 * 
	 * @param pr
	 * @return
	 */
	public static String[] getValuesFromProperties(Properties pr) {

		// inits the array
		String str[] = new String[pr.keySet().size()];
		Enumeration<Object> en = pr.keys();
		int j = 0;

		// cycles on the properties
		while (en.hasMoreElements()) {

			// gets the key
			String strKey = (String) en.nextElement();

			// inserts the key into the array
			str[j++] = pr.getProperty(strKey);
		}

		// and returns the array
		return str;
	}

	private static byte[] crypt(byte[] input, int mode) throws Exception {

		SecretKey key = generateKey();
		PBEParameterSpec spec = new PBEParameterSpec(salt, 40);
		Cipher ciph = Cipher.getInstance("PBEWithMD5AndDES");
		ciph.init(mode, key, spec);
		return ciph.doFinal(input);
	}

	private static byte[] salt = { (byte) 0x15, (byte) 0x8c, (byte) 0xa3, (byte) 0x4a, (byte) 0x66, (byte) 0x51, (byte) 0x2a, (byte) 0xbc };

	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	private static byte[] encryptString(String strPlainText) throws Exception {

		return crypt(strPlainText.getBytes(), Cipher.ENCRYPT_MODE);
	}

	private static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {

		PBEKeySpec spec = new PBEKeySpec("NuvolettaAssassina".toCharArray());
		return SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(spec);
	}

	/**
	 * encrypts a string
	 * 
	 * @param strText the string to be encrypted
	 * @return the crypted string
	 * @throws Exception
	 */
	public static String encrypt(String strText) throws Exception {

		// checks the text to crypt
		if (strText == null) return null;

		byte[] cipherText = encryptString(strText);

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < cipherText.length; i++) {
			Byte b = new Byte(cipherText[i]);
			int irep = b.intValue();
			if (irep < 0) irep += 256;
			if (irep < 16) result.append('0');
			result.append(Integer.toHexString(irep));
		}

		return result.toString();
	}

	static byte[] decryptString(String plainText) throws Exception {

		byte[] plainBytes = new byte[plainText.length() / 2];
		char[] plainChars = plainText.toCharArray();

		for (int i = 0; i < plainChars.length; i += 2) {
			int irep = Integer.parseInt(new String(plainChars, i, 2), 16);
			if (irep > 127) irep -= 256;
			plainBytes[i / 2] = new Integer(irep).byteValue();
		}
		return crypt(plainBytes, Cipher.DECRYPT_MODE);
	}

	/**
	 * decrypts a string
	 * 
	 * @param strText the string to be decrypted
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String strText) throws Exception {

		return new String(decryptString(strText));
	}

	/**
	 * obfuscates a string
	 * 
	 * @param strVal the string to be obfusctated
	 * @return the crypted string
	 * @throws Exception
	 */
	public static String obfuscate(String strVal) throws Exception {

		StringBuffer sbVal = new StringBuffer();

		// encodes base 64 the value
		String strParam = new sun.misc.BASE64Encoder().encode(strVal.getBytes());

		// cycles on the string to obfuscate
		for (int j = 0; j < strParam.length() - 2; j++) {

			// inserts a fake value
			char c = (char) ('a' + ((j * 17) % 26));
			sbVal.append(c);

			// insert the correct j-th value
			sbVal.append(strParam.charAt(j));
		}

		// appends the last 2 characters
		sbVal.append(strParam.substring(strParam.length() - 2));

		// and returns the string
		return sbVal.toString();
	}

	/**
	 * separate all the uppercase characters of the specified string from the preceding character
	 * using the spcecifed separator
	 * 
	 * @param strValue the string to separate
	 * @param strSeparator the character to use for separing
	 * @return
	 */
	public static String separateUpperCaseLetters(String strValue, String strSeparator) {

		// inits the buffer with the string
		StringBuffer sbValue = new StringBuffer();

		// cycles on all the characters of the string
		for (int j = 0; j < strValue.length(); j++) {

			// if the preceding char was lowercase and this char is uppercase,
			// inserts the separator
			if (j > 0) if (strValue.toLowerCase().charAt(j - 1) == strValue.charAt(j - 1) && strValue.toUpperCase().charAt(j) == strValue.charAt(j)) sbValue.append(strSeparator);

			// and appends the j-th character
			sbValue.append(strValue.charAt(j));
		}

		// and returns the buffer
		return sbValue.toString();
	}

	/**
	 * returns true if the specified string is present into the array list; else returns false
	 * 
	 * @param alValues the AL of values
	 * @param strValue the string to serach into the AL
	 * @return
	 */
	public static boolean isStringPresent(String strValue, ArrayList<String> alValues) {

		// cycles on the values of the AL
		for (int j = 0; j < alValues.size(); j++) {

			// gets the j-th item
			String strVal = alValues.get(j);

			// if is equal to the string passed returns true
			if (strVal.equals(strValue)) return true;
		}

		// if arrives here, there's no equal string into the AL
		return false;
	}

	/**
	 * returns true if the specified string is present into the array list; else returns false
	 * 
	 * @param strValue the string to serach into the AL
	 * @param strValues the array of values
	 * @return
	 */
	public static boolean isStringPresent(String strValue, String[] strValues) {

		// cycles on the values of the AL
		for (int j = 0; j < strValues.length; j++) {

			// gets the j-th item
			String strVal = strValues[j];

			// if is equal to the string passed returns true
			if (strVal.equals(strValue)) return true;
		}

		// if arrives here, there's no equal string into the AL
		return false;
	}

	/**
	 * creates an html table with the values contained in the properties
	 * 
	 * @param prValues
	 * @return
	 */
	public static String getValuesInHtml(Properties prValues) {

		// if there are no values, just returns this
		if (prValues.size() == 0) return "<html>No Values present</html>";

		// creates the HTML table for the tooltip of the button
		StringBuffer sbTt = new StringBuffer("<html><b>Values present:</b><table>");
		Enumeration<Object> en = prValues.keys();

		// cycles on the properties
		while (en.hasMoreElements()) {

			// gets the key
			String strKey = (String) en.nextElement();

			// appends key and value to the html table
			sbTt.append("<tr><td>" + strKey + "</td><td>" + prValues.getProperty(strKey) + "</td></tr>");
		}

		// close the table and sets the TT
		sbTt.append("</table></html>");

		return sbTt.toString();
	}

	/**
	 * returns a string with all the parenthesis content of the specified string removed
	 * (parenthesis included)
	 * 
	 * @param strValue
	 * @return
	 */
	public static String removeParenthesisContent(String strValue) {

		// inits the buffer
		StringBuffer sbRemoved = new StringBuffer();

		// the parenthesis counter
		int p = 0;

		// cycles on the input string
		for (int j = 0; j < strValue.length(); j++) {

			// counts the parenthesis
			if (strValue.charAt(j) == '(') p++;
			if (strValue.charAt(j) == ')') p++;

			// if is outside any parenthesis, adds the char
			if (p == 0 && strValue.charAt(j) != ')') sbRemoved.append(strValue.charAt(j));
		}

		// and returns the buffer
		return sbRemoved.toString();
	}

	/**
	 * removes the comment lines from script;
	 * 
	 * @param strScript the script
	 * @param strComment the form of a comment eg: // or -- or etc)
	 */
	public static String removeLineComments(String strScript, String strComment) throws Exception {

		// inits the buffer
		StringBuffer sbNew = new StringBuffer();

		// gets a buff reader of the script
		BufferedReader br = new BufferedReader(new StringReader(strScript));

		// cycles on the script
		String strLine;
		while ((strLine = br.readLine()) != null) {

			// if this line contains a comment, just adds the part of line
			// before the comment
			if (strLine.indexOf(strComment) >= 0) sbNew.append(strLine.substring(0, strLine.indexOf(strComment)) + "\n");

			// else, appends the whole line
			else sbNew.append(strLine + "\n");
		}

		// and returns the buffer
		return sbNew.toString();
	}

	/**
	 * escapes all the special chars for regular expressions
	 * 
	 * @param strVal
	 * @return
	 */
	public static String escapeRegExp(String strVal) {

		strVal = strVal.replace("$", "\\$");
		strVal = strVal.replace("[", "\\[");
		strVal = strVal.replace("]", "\\]");
		strVal = strVal.replace(".", "\\.");
		strVal = strVal.replace("/", "\\/");

		return strVal;
	}

	public static String getFirstCharToUpper(String str) {

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String getFirstCharToLower(String str) {

		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String getFirstCharToUpperAndLowerTheRest(String str) {

		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
