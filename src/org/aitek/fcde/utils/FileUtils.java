package org.aitek.fcde.utils;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FileUtils {

	/**
	 * gets an XML fragment of a document of the XML resoruce document
	 * 
	 * @param strResourceId
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static Node getResource(String strResourceId, Document doc) throws Exception {

		Node ndRes = null;

		// gets the tags with the specified name
		NodeList nl = doc.getElementsByTagName("RESOURCE");

		// if there is some
		if (nl != null) {
			// if it contains some data
			for (int j = 0; j < nl.getLength(); j++) {
				// gets the first
				Element e = (Element) nl.item(j);

				// if this resource is the one specified, assigns it to the Node
				// to be returned
				if (e.getAttribute("ID").equals(strResourceId)) ndRes = e;
			}
		}

		// and returns it
		return ndRes;
	}

	/**
	 * writes a binary file to disk
	 * 
	 * @param strFileName the filename
	 * @param btFileContent the content of the file
	 * @throws Exception
	 */
	public static void writeBinaryFile(String strFileName, byte[] btFileContent) throws Exception {

		// creates the stream
		FileOutputStream fos = new FileOutputStream(strFileName);
		DataOutputStream dos = new DataOutputStream(fos);

		try {
			// writes the content to the file
			dos.write(btFileContent);
		}
		finally {
			// and closes the stream
			if (dos != null) dos.close();
		}
	}

	/**
	 * writes a text file to disk
	 * 
	 * @param strFileName the filename
	 * @param btFileContent the content of the file
	 * @throws Exception
	 */
	public static void writeTextFile(String strFileName, String strContent) throws Exception {

		writeTextFile(strFileName, strContent, null);
	}

	/**
	 * writes a text file to disk with the specified encoding
	 * 
	 * @param strFileName the filename
	 * @param btFileContent the content of the file
	 * @param strEncoding the encoding to use for writing the file
	 * @throws Exception
	 */
	public static void writeTextFile(String strFileName, String strContent, String strEncoding) throws Exception {

		writeTextFile(strFileName, strContent, strEncoding, false);
	}

	/**
	 * writes a text file to disk with the specified encoding
	 * 
	 * @param strFileName the filename
	 * @param btFileContent the content of the file
	 * @param strEncoding the encoding to use for writing the file
	 * @param isToAppend true if the content has to be appended to the file, false else
	 * @throws Exception
	 */
	public static void writeTextFile(String strFileName, String strContent, String strEncoding, boolean isToAppend) throws Exception {

		Writer wr = null;

		// creates the stream
		if (strEncoding == null) {
			wr = new FileWriter(strFileName, isToAppend);

		}
		else {
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(strFileName, isToAppend), strEncoding));
		}

		try {
			// writes the content to the file
			wr.write(strContent, 0, strContent.length());
		}
		finally {
			// and closes the writer
			if (wr != null) wr.close();
		}
	}

	/**
	 * writes a text file to disk
	 * 
	 * @param strFileName the filename
	 * @param btFileContent the content of the file
	 * @throws Exception
	 */
	public static void writeTextFile(File fl, String strContent) throws Exception {

		// creates the stream
		FileWriter fw = new FileWriter(fl);

		try {
			// writes the content to the file
			fw.write(strContent, 0, strContent.length());
		}
		finally {
			// and closes the writer
			if (fw != null) fw.close();
		}
	}

	/**
	 * returns the length in bytes of the specified file
	 * 
	 * @param strFileName the filename
	 * @return the length
	 */
	public static long getFileLength(String strFileName) {

		// gets the specified file
		File f = new File(strFileName);

		// and returns its filelength
		return f.length();
	}

	/**
	 * reads a binary file
	 * 
	 * @param strFileName the name of the file
	 * @return the content of the file
	 * @throws Exception
	 */
	public static byte[] readBinaryFile(String strFileName) throws Exception {

		// gets the streams
		FileInputStream fis = new FileInputStream(strFileName);
		DataInputStream dis = new DataInputStream(fis);

		// sets the buffer
		byte b[] = new byte[(int) getFileLength(strFileName)];

		try {
			// reads from the stream
			dis.read(b);

		}
		finally {
			// closes the streams
			if (dis != null) dis.close();
			if (fis != null) fis.close();
		}

		// and returns it
		return b;
	}

	/**
	 * reads a text file
	 * 
	 * @param fileName the name of the file
	 * @return the content of the file
	 * @throws Exception
	 */
	public static String readTextFile(String fileName, String encoding) throws Exception {

		return readTextFile(new File(fileName), encoding);
	}

	/**
	 * reads a text file
	 * 
	 * @param fileName the name of the file
	 * @return the content of the file
	 * @throws Exception
	 */
	public static String readTextFile(File f, String encoding) throws Exception {

		InputStreamReader fr = null;
		StringBuffer content = new StringBuffer();

		try {
			// gets the file stream
			fr = new InputStreamReader(new FileInputStream(f), encoding);

			// reads it through a buffer
			char data[] = new char[4096];
			while ((fr.read(data)) != -1)
				content.append(data);
		}
		finally {
			// closes everything
			if (fr != null) fr.close();
		}

		// and returns the content
		return content.toString();
	}

	/**
	 * copies a file to another location
	 * 
	 * @param strSrcFileName
	 * @param strDestFileName
	 */
	public static void copyFile(String strSrcFileName, String strDestFileName) throws Exception {

		FileInputStream fis = null;
		FileOutputStream fos = null;

		// checks if the directory where the file is to be written, exists
		String strDirName = strDestFileName.substring(0, strDestFileName.lastIndexOf(File.separatorChar));
		File dir = new File(strDirName);
		if (!dir.exists()) dir.mkdirs();

		try {
			// inits the reader and the writer
			fis = new FileInputStream(strSrcFileName);
			fos = new FileOutputStream(strDestFileName);

			// the buffer for reading
			byte[] buf = new byte[1024];
			int i = 0;

			// reads the source file til it reaches EOF
			while ((i = fis.read(buf)) != -1) {

				// writes the buffer on the destination file
				fos.write(buf, 0, i);
			}
		}
		finally {
			// whatever happens, it closes the file reader and writer
			if (fis != null) fis.close();
			if (fos != null) fos.close();
		}

	}

	/**
	 * deletes a file on filesystem
	 * 
	 * @param strFileName the name of the file to be deleted
	 */
	public static void deleteFile(String strFileName) {

		// gets the file
		File fTmp = new File(strFileName);

		// and deletes it
		fTmp.delete();
	}

	/**
	 * deletes a directory and all its contents
	 * 
	 * @param strPath the path of teh dir to delete
	 * @return
	 */
	public static void deleteDirectoryContent(String strPath) throws Exception {

		// if the dir exists
		if (strPath != null && isDirExisting(strPath)) {

			// creates a file from the path
			File fDir = new File(strPath);

			// gets all the content
			File[] fs = fDir.listFiles();

			// cycles on them
			for (int i = 0; i < fs.length; i++) {

				// if the i-th element is a directory
				if (fs[i].isDirectory()) {

					// calls recursively this method
					deleteDirectoryContent(fs[i].getAbsolutePath());
				}
				// if is a file
				else {

					// just deletes it
					fs[i].delete();
				}
			}

			// after having deleted all the files, deletes the dir itself
			fDir.delete();
		}
	}

	/**
	 * extracts the file name from the absoulte filename
	 * 
	 * @param strFilePath
	 * @return
	 */
	public static String getFileNameFromPath(String strFilePath) {

		if (strFilePath != null) {

			// if there's a separator, returns the file path
			if (strFilePath.lastIndexOf("/") > 0) return strFilePath.substring(strFilePath.lastIndexOf("/") + 1);

			// if there's a separator, returns the file path
			if (strFilePath.lastIndexOf("\\") > 0) return strFilePath.substring(strFilePath.lastIndexOf("\\") + 1);

			// if there's no separator, returns the complete name
			else return strFilePath;
		}
		return "";
	}

	/**
	 * extracts the file path from the absolute filename
	 * 
	 * @param strFileName
	 * @return
	 */
	public static String getPathFromFileName(String strFileName) {

		if (strFileName != null) {

			// if there's a separator, returns the file path
			if (strFileName.lastIndexOf("/") > 0) return strFileName.substring(0, strFileName.lastIndexOf("/"));

			// if there's no separator, returns the complete name
			else return strFileName;
		}
		return "";
	}

	/**
	 * creates a directory or a directory structure (if not exists)
	 * 
	 * @param strDirName
	 * @throws Exception
	 */
	public static void createDirectory(String strDirName) throws Exception {

		// if the dir has a name
		if (strDirName != null) {

			// creates a file on it
			File f = new File(strDirName);

			// if it's not existing
			if (!f.exists()) {

				// creates the dir or the dir structure
				f.mkdirs();
			}

		}

	}

	/**
	 * checks if a dir already exists
	 * 
	 * @param strFileName the file to check
	 * @throws Exception
	 */
	public static boolean isDirExisting(String strFileName) throws Exception {

		return isFileExisting(strFileName);
	}

	/**
	 * checks if a file already exists
	 * 
	 * @param strFileName the file to check
	 * @throws Exception
	 */
	public static boolean isFileExisting(String strFileName) throws Exception {

		// if the dir has a name
		if (strFileName != null) {

			// creates a file on it
			File f = new File(strFileName);

			// if it's not existing
			return f.exists();
		}

		else return false;

	}

	/**
	 * writes a text file; if the file already exists, asks the user the confirm to write returns
	 * true if the file has been written, false if not
	 * 
	 * @param strFileName
	 * @param strContent
	 * @param callingComp
	 * @throws Exception
	 */
	public static boolean confirmWriteTextFile(String strFileName, String strContent, Component callingComp) throws Exception {

		// checks if the file exists
		if (isFileExisting(strFileName)) {

			// asks the user if wants to overwrite the file
			if (JOptionPane.showConfirmDialog(callingComp, "The file '" + strFileName + "' already exists.\nDo you want to overwrite it?", "Confirm Overwrite", JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION) {

				// if the user doesn't want, just returns
				return false;
			}
		}

		// checks for existance of directory
		FileUtils.createDirectory(strFileName.substring(0, strFileName.lastIndexOf(File.separatorChar)));

		// and writes or overwrites the file
		writeTextFile(strFileName, strContent);

		// and returns
		return true;
	}

	/**
	 * returns a string with a number of "../" equals to the number of "/" contained in the
	 * spedified filename
	 * 
	 * @param strFileName the filename where to look for "/" characters
	 * @return the string with the the path (e.g. "../../../" if filename is
	 *         "test/foo/andrea/hi.txt"
	 */
	public static String getPathUpperDir(String strFileName) {

		// sets the initial value
		String strUpperPath = "";
		int p;

		// cycles on slashes in the filename
		while ((p = strFileName.indexOf(File.separatorChar)) >= 0) {
			strFileName = strFileName.substring(p + 1);
			strUpperPath = strUpperPath + ".." + File.separatorChar;
		}

		return strUpperPath;
	}

	/**
	 * returns true if the specified filename has a separator char inside it, false else
	 * 
	 * @param strFileName the name to check
	 * @return
	 */
	public static boolean hasSubDirectories(String strFileName) {

		return strFileName.indexOf(File.separatorChar) >= 0;
	}

	/**
	 * moves a file to another (like unix command 'mv')
	 * 
	 * @param strSrc
	 * @param strDest
	 */
	public static void moveFile(String strSrc, String strDest) throws Exception {

		// copies the source onto the destination file
		copyFile(strSrc, strDest);

		// and deletes the source
		deleteFile(strSrc);
	}

	/**
	 * reads a resource text file
	 * 
	 * @param strFileName the name of the resource
	 * @return the content of the file
	 * @throws Exception
	 */
	public static String readResourceTextFile(String strFileName, Class<?> clAccess) throws Exception {

		// declares the varibales
		int car;
		StringBuffer sbContent = new StringBuffer();

		// gets the file as an URL
		URL u = clAccess.getResource(strFileName);

		// checks if exists
		if (u == null) throw new Exception("File [" + strFileName + "] does not esist.");

		// opens the file
		InputStream is = u.openStream();

		try {
			// reads it til EOF
			while ((car = is.read()) != -1)

				// and appends every char
				sbContent.append((char) car);
		}
		finally {

			// closes everything
			if (is != null) is.close();
		}

		// and returns the content
		return sbContent.toString();
	}

	public static ArrayList<String> readDirectory(String strDirName) throws Exception {

		// inits the array
		ArrayList<String> alNames = new ArrayList<String>();

		// if its' the GUI, just skips it
		if (strDirName.endsWith("/gui")) return alNames;

		// if it's missing, add the dir separator at the end of the dirname
		if (!strDirName.endsWith("" + File.separatorChar)) strDirName = strDirName + File.separatorChar;

		// if has no scheme definition, adds it
		// if (strDirName.startsWith("" + File.separatorChar)) strDirName = "rcsc:" + strDirName;

		// looks into the passed dir
		URI uri = FileUtils.class.getResource(strDirName).toURI();
		File f = new File(uri);

		// gets all the files in this directory
		File fs[] = f.listFiles();

		// cycles on the files
		for (int j = 0; j < fs.length; j++) {

			// gets the name of the file
			File nf = new File(FileUtils.class.getResource(strDirName + fs[j].getName()).toURI());

			// if it's a file, it's added to the AL
			if (nf.isFile()) alNames.add(strDirName + nf.getName());

			// if it's a dir call recursively this procedure
			else if (nf.isDirectory()) alNames.addAll(readDirectory(strDirName + fs[j].getName()));
		}

		// and returns the AL
		return alNames;
	}

}
