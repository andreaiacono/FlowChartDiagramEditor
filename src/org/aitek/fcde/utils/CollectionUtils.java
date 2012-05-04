package org.aitek.fcde.utils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CollectionUtils {

	public static ArrayList<Object> removeDuplicateElements(ArrayList<Object> alData) {

		ArrayList<Object> alNew = new ArrayList<Object>();

		// cicla sull'ArrayList da controllare
		for (int i = 0; i < alData.size(); i++) {
			boolean hasItem = false;
			// prende l'i-esimo item
			Object obData = (Object) alData.get(i);

			// cicla su quelli già inseriti
			for (int j = 0; j < alNew.size(); j++) {
				Object obNew = (Object) alNew.get(j);

				// se \uFFFD gi\uFFFD stato inserito, lo flagga
				if (obNew.equals(obData)) hasItem = true;
			}

			// se non l'ha trovato nel nuovo ArrayList, lo inserisce
			if (!hasItem) alNew.add(obData);
		}

		// e restituisce il nuovo arraylist senza duplicati
		return alNew;
	}

	public static ArrayList<String> removeDuplicateString(ArrayList<String> alData) {

		ArrayList<String> alNew = new ArrayList<String>();

		// cycles on the AL
		for (int i = 0; i < alData.size(); i++) {
			boolean hasItem = false;

			// gets the i-th string
			String strData = alData.get(i);

			// cicla su quelli già inseriti
			for (int j = 0; j < alNew.size(); j++) {

				// gtes the j-th string
				String strNew = alNew.get(j);

				// if already inserted, sets the flag
				if (strNew.equals(strData)) hasItem = true;
			}

			// if not found in the new AL, inserts it
			if (!hasItem) {
				alNew.add(strData);
			}
		}

		// and returns the new AL
		return alNew;
	}

	/**
	 * returns the first occurrence of the specified Tag
	 * 
	 * @param strTag the Tag to search
	 * @return The string containing the value of the first occurrence
	 */
	public static boolean isPresent(Document doc, String strTag) throws Exception {

		try {
			// gets the first tag with the specified name
			NodeList nl = doc.getElementsByTagName(strTag);

			// if it contains some data, then returns true
			if (nl != null) if (nl.getLength() > 0) return true;
		}
		catch (Exception e) {
			throw new Exception("org.aitek.dbadmin.tools.XmlUtils.getFirstTagValue: " + e.toString());
		}
		return false;
	}

	/**
	 * gets a tokenizer and returns an arraylist with the separated elements of the tokenizer
	 * 
	 * @param strt the tokenizer
	 * @param hasToTrim if has to trim every single element of the tokenizer
	 * @return the AL with the elements
	 */
	public static ArrayList<String> toArrayList(StringTokenizer strt, boolean hasToTrim) {

		// creates the AL
		ArrayList<String> al = new ArrayList<String>();

		while (strt.hasMoreElements()) {
			// gets the next element
			String strElement = (String) strt.nextElement();

			// if is to be trimmed, it does it
			if (hasToTrim) strElement = strElement.trim();

			// and adds it to the AL
			al.add(strElement);
		}

		// returns the AL
		return al;
	}

	/**
	 * gets a tokenizer and returns an arraylist with the separated elements of the tokenizer
	 * 
	 * @param strt the tokenizer
	 * @param hasToTrim if has to trim every single element of the tokenizer
	 * @return the AL with the elements
	 */
	public static ArrayList<String> toArrayList(String strValues, String strSeparator, boolean hasToTrim) {

		// creates the AL
		ArrayList<String> al = new ArrayList<String>();
		StringTokenizer strt = new StringTokenizer(strValues, strSeparator);

		while (strt.hasMoreElements()) {
			// gets the next element
			String strElement = (String) strt.nextElement();

			// if is to be trimmed, it does it
			if (hasToTrim) strElement = strElement.trim();

			// and adds it to the AL
			al.add(strElement);
		}

		// returns the AL
		return al;
	}

	/**
	 * transofmrs a string array into an AL
	 * 
	 * @param strValues the values to trasnofrm
	 * @param hasToTrim if has to trim every single element of the tokenizer
	 * @return the AL with the elements
	 */
	public static ArrayList<String> toArrayList(String[] strValues, String strSeparator, boolean hasToTrim) {

		// creates the AL
		ArrayList<String> al = new ArrayList<String>();

		for (int j = 0; j < strValues.length; j++) {

			// gets the next element
			String strElement = strValues[j];

			// if is to be trimmed, it does it
			if (hasToTrim) strElement = strElement.trim();

			// and adds it to the AL
			al.add(strElement);
		}

		// returns the AL
		return al;
	}

	/**
	 * gets a tokenizer and returns an arraylist with the separated elements of the tokenizer
	 * 
	 * @param strt the tokenizer
	 * @param hasToTrim if has to trim every single element of the tokenizer
	 * @return the AL with the elements
	 */
	public static String[] getStringArrayFromString(String strValues, String strSeparator, boolean hasToTrim) {

		// if null
		if (strValues == null) return new String[0];

		// creates the AL
		ArrayList<String> al = new ArrayList<String>();
		StringTokenizer strt = new StringTokenizer(strValues, strSeparator);

		while (strt.hasMoreElements()) {
			// gets the next element
			String strElement = (String) strt.nextElement();

			// if is to be trimmed, it does it
			if (hasToTrim) strElement = strElement.trim();

			// and adds it to the AL
			al.add(strElement);
		}

		String[] strArray = new String[al.size()];

		// returns the AL
		return al.toArray(strArray);
	}

	/**
	 * gets an array of string values and transofrms them into arraylist of string
	 * 
	 * @param alNames
	 * @return the string with the definition
	 */
	public static ArrayList<String> getArrayListFromStringArray(String[] strNames) {

		// inits the AL
		ArrayList<String> alValues = new ArrayList<String>();

		// cycles on the values
		for (int j = 0; j < strNames.length; j++) {

			// adds the j-th value
			alValues.add(strNames[j]);
		}

		// and returns it
		return alValues;

	}

	/**
	 * 
	 * @param doc
	 * @param strTagName
	 * @return
	 */
	public static ArrayList<String> getArrayListFromNodes(Document doc, String strTagName) {

		ArrayList<String> alValues = new ArrayList<String>();

		NodeList nl = doc.getElementsByTagName(strTagName);

		// if there are tags
		if (nl != null) {

			// cycles on all of them
			for (int i = 0; i < nl.getLength(); i++) {

				// if it is a field Node
				if (nl.item(i).getNodeType() == 1) {

					// gets the i-th element
					Element e = (Element) nl.item(i);

					// if it has a value adds it to the AL
					if (e.getFirstChild() != null) alValues.add(e.getFirstChild().getNodeValue());
				}
			}
		}

		return alValues;
	}

	/**
	 * creates a string from an arraylist
	 * 
	 * @param alValues the values to be concatenated
	 * @param strSeparator the separator between one element and the other
	 * @return the string with the values seaparated by the strSeparator
	 */
	public static String[] getStringArrayFromArrayList(ArrayList<String> alValues) {

		// inits the buffer
		String[] strValues = new String[alValues.size()];

		// cycles on the values of the AL
		for (int j = 0; j < alValues.size(); j++) {

			// appends the j-th vlaur to the buffer
			strValues[j] = alValues.get(j);
		}

		// and returns it
		return strValues;
	}

	/**
	 * returns the index of the serached string from the array. If not found returns -1.
	 * 
	 * @param strValues
	 * @param strSearchedValue
	 * @return
	 */
	public static int getIndex(String[] strValues, String strSearchedValue) {

		// checks for null input
		if (strValues == null || strSearchedValue == null) return -1;

		// cycles on the vcalues of the array
		for (int j = 0; j < strValues.length; j++) {

			// gets the j-th element
			String strVal = strValues[j];

			// if is the searched one, returns its index
			if (strVal.equals(strSearchedValue)) return j;
		}

		// if arrives here, the searched value hasn't been found
		return -1;
	}

	/**
	 * returns the index of the serached string from the array. If not found returns -1.
	 * 
	 * @param strValues
	 * @param strSearchedValue
	 * @return
	 */
	public static int getIndex(ArrayList<String> alValues, String strSearchedValue) {

		// cycles on the vcalues of the array
		for (int j = 0; j < alValues.size(); j++) {

			// gets the j-th element
			String strVal = alValues.get(j);

			// if is the searched one, returns its index
			if (strVal.equals(strSearchedValue)) return j;
		}

		// if arrives here, the searched value hasn't been found
		return -1;
	}

	/**
	 * returns true if the specified value is present into the array list; else returns false
	 * 
	 * @param alValues the AL of values
	 * @param obValue the object to be searched into the AL
	 * @return
	 */
	public static boolean isValuePresent(Object obValue, ArrayList<?> alValues) {

		// cycles on the values of the AL
		for (int j = 0; j < alValues.size(); j++) {

			// gets the j-th item
			Object obVal = alValues.get(j);

			// if is equal to the string passed returns true
			if (obVal != null) if (obVal.equals(obValue)) return true;
		}

		// if arrives here, there's no equal string into the AL
		return false;
	}

	public static void removeStringFromArrayList(String strItem, ArrayList<String> alItems) {

		// cycles on the arraylist
		for (int j = 0; j < alItems.size(); j++) {

			// gets the j-thg item
			String strValue = alItems.get(j);

			// if the j-th item is equal to the passed one
			if (strValue.equals(strItem)) {

				// removes it
				alItems.remove(j);

				// and returns
				return;
			}

		}

	}

	/**
	 * return a properties with the values passed as params
	 * 
	 * @param strKeys the keys to insert into the pr
	 * @param strValues the values to insert into the pr
	 * @return
	 */
	public static Properties loadProperties(String[] strKeys, String[] strValues) {

		// checks the params
		if (strKeys == null || strValues == null) return null;

		// inits the PR
		Properties pr = new Properties();

		// cycles on keys
		for (int j = 0; j < strKeys.length; j++) {

			// insert the j-th key into the properties
			pr.setProperty(strKeys[j], strValues[j]);
		}

		// and returns it
		return pr;
	}

	/**
	 * returns true if all the elemtns of the AL are null (or the arryalist itself is null)
	 * 
	 * @param al
	 * @return
	 */
	public static boolean isAllNull(ArrayList<?> al) {

		// checks for null value
		if (al == null) return true;

		// cycles on the elements
		for (int j = 0; j < al.size(); j++)
			if (al.get(j) != null) return false;

		// if did not found any not null element, returns true
		return true;
	}

	/**
	 * returns true if all the elemtns of the string arrayare null (or the string array itself is
	 * null)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAllNull(String[] str) {

		// checks for null value
		if (str == null) return true;

		// cycles on the elements
		for (int j = 0; j < str.length; j++)
			if (str[j] != null) return false;

		// if did not found any not null element, returns true
		return true;
	}

	/**
	 * returns true if all the elements of the string arrayare null or are empty string (or the
	 * string array itself is null)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAllNullOrEmpty(String[] str) {

		// checks for null value
		if (str == null) return true;

		// cycles on the elements
		for (int j = 0; j < str.length; j++)
			if (!"".equals(str[j])) return false;

		// if did not found any not null element, returns true
		return true;
	}

	/**
	 * gets the first value on the first AL that is equal to another in the second
	 * 
	 * @param alFirst
	 * @param alSecond
	 * @return the string value equal between the two AL, or null if such value doesn't exist
	 */
	public static String getFirstSameValue(ArrayList<String> alFirst, ArrayList<String> alSecond) {

		// cycles of the first AL
		for (int j = 0; j < alFirst.size(); j++) {

			// gets the j-th value
			String strValue = alFirst.get(j);

			// cycles of the second AL
			for (int i = 0; i < alSecond.size(); i++) {

				// if the two values are equal, return the value
				if (strValue.equals(alSecond.get(i))) return strValue;

			}

		}

		// hasn't found nothing, so returns null
		return null;
	}

	/**
	 * get multiple lines from a string with \n
	 * 
	 * @param strHtml
	 * @return
	 */
	public static ArrayList<String> getLines(String strText) {

		ArrayList<String> alLines = new ArrayList<String>();

		// gets the postition of the first tag of the string
		int pos = strText.indexOf("\n");

		// and writes line after line till it ends the space of the div
		while (pos >= 0) {

			alLines.add(strText.substring(0, pos));

			// deletes the tag at the start of the string
			strText = strText.substring(pos + 1);

			// gets the position of a new tag
			pos = strText.indexOf("\n");

		}

		// and return the AL
		return alLines;
	}

	/**
	 * get multiple lines from a string with \n
	 * 
	 * @param strText the script to divide into single lines
	 * @param strEndInstruction the character that marks the end of an instruction
	 * @return
	 */
	public static ArrayList<String> getScriptLines(String strText, String strEndInstruction) throws Exception {

		// inits the list and the reader
		ArrayList<String> alLines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new StringReader(strText));
		String strLine = null;
		StringBuffer sbLine = new StringBuffer();

		// reads the script through the reader
		while ((strLine = br.readLine()) != null) {

			// if is a comment skips the line
			if (strLine.length() == 0 || strLine.startsWith("\n") || strLine.startsWith("--") || strLine.startsWith("#")) continue;

			// adds the script line to the buffer
			sbLine.append(strLine);

			// if it's the end of the command, adds it to the AL
			if (strLine.trim().endsWith(strEndInstruction) || strEndInstruction.equals("")) {
				alLines.add(sbLine.toString());
				sbLine = new StringBuffer("");
			}
		}

		// and return the AL
		return alLines;
	}

	/**
	 * returns a P with the value of the specifed attribute of the specifed tags
	 * 
	 * @param doc
	 * @param strTagName
	 * @param strAttribute
	 * @return
	 */
	public static Properties getPropertiesFromArrayList(ArrayList<String> al) {

		Properties pr = new Properties();

		// cycles on all of them
		for (int i = 0; i < al.size(); i++) {

			pr.setProperty(al.get(i), "");
		}

		return pr;
	}

	/**
	 * 
	 * @param doc
	 * @param strTagName
	 * @return
	 */
	public static ArrayList<String> getArrayListFromNodesAttributes(Document doc, String strTagName, String strAttributeName) {

		ArrayList<String> alValues = new ArrayList<String>();

		NodeList nl = doc.getElementsByTagName(strTagName);

		// if there are tags
		if (nl != null) {

			// cycles on all of them
			for (int i = 0; i < nl.getLength(); i++) {

				// if it is a field Node
				if (nl.item(i).getNodeType() == 1) {

					// gets the i-th element
					Element e = (Element) nl.item(i);

					// if it has a value adds it to the AL
					alValues.add(e.getAttribute(strAttributeName));
				}
			}
		}

		return alValues;
	}

}
