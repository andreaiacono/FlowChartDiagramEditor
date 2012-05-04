package org.aitek.fcde.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

public class GenericFileFilter extends FileFilter implements FilenameFilter {

	private ArrayList<String> alFilters = null;
	private String strDescription = null;

	/**
	 * the constructor for all the files
	 */
	public GenericFileFilter() {
		this.alFilters = new ArrayList<String>();
	}

	/**
	 * the constructor for all the files with the specified extension
	 */
	public GenericFileFilter(String strExtension) {

		this(strExtension, null);
	}

	/**
	 * Creates a file filter that accepts the given file type.
	 */
	public GenericFileFilter(String strExtension, String strDescription) {

		this();
		addExtension(strExtension);
		setDescription(strDescription);
	}

	/**
	 * Creates a file filter with the specified string array.
	 */
	public GenericFileFilter(String[] filters) {

		this(filters, null);
	}

	/**
	 * Creates a file filter from the given string array and description.
	 */
	public GenericFileFilter(String[] filters, String description) {

		this();

		// cycles on the filters
		for (int i = 0; i < filters.length; i++) {

			// add the the i-th filter
			addExtension(filters[i]);
		}

		// and sets the dexcription
		setDescription(description);
	}

	/**
	 * Return true if this file should be selected
	 */
	public boolean accept(File f) {

		// checks the file
		if (f != null) {

			// if it's a directory, it's ok
			if (f.isDirectory()) return true;

			// gets its extension
			String strExtension = getExtension(f);

			// checks if file has the extension required, and returns true
			if (strExtension != null && StringUtils.isStringPresent(strExtension, alFilters)) return true;
		}

		// if the file is null, it's not accepted
		return false;
	}

	public boolean accept(File dir, String name) {

		// get the extension of the file
		if (name.indexOf(".") >= 0) name = name.substring(name.indexOf(".") + 1);

		// if the extension of the file is listed in the allowed extensions, returns true
		if (StringUtils.isStringPresent(name, alFilters)) return true;

		// return false
		return false;
	}

	/**
	 * returns the extension of teh specified file
	 */
	public String getExtension(File f) {

		// checks for file consistancy
		if (f != null) {

			// gets its name
			String filename = f.getName();

			// gets the last point position
			int i = filename.lastIndexOf('.');

			// if there's a point, returns its extension
			if (i > 0 && i < filename.length() - 1) return filename.substring(i + 1).toLowerCase();
		}
		return null;
	}

	/**
	 * adds an extension to the filter
	 */
	public void addExtension(String strExtension) {

		// adds the extension to the hashtable
		alFilters.add(strExtension.toLowerCase());
	}

	/**
	 * Returns the human readable description of this filter. For example: "JPEG and GIF Image Files (*.jpg, *.gif)"
	 * 
	 * @see setDescription
	 * @see setExtensionListInDescription
	 * @see isExtensionListInDescription
	 * @see FileFilter#getDescription
	 */
	public String getDescription() {

		// inits the buffer
		StringBuffer sbDesc = new StringBuffer();

		// if there's a description, adds it
		if (this.strDescription != null) sbDesc.append(this.strDescription + " (");

		// cycles on the extensions
		for (int j = 0; j < alFilters.size(); j++) {

			// if isn't the first extension, appends a comma
			if (j > 0) sbDesc.append(", ");

			// appends the extensions
			sbDesc.append("*." + alFilters.get(j));
		}
		// closes the string
		sbDesc.append(")");

		// and returns it
		return sbDesc.toString();
	}

	/**
	 * Sets the human readable description of this filter. For example: filter.setDescription("Gif and JPG Images");
	 * 
	 * @see setDescription
	 * @see setExtensionListInDescription
	 * @see isExtensionListInDescription
	 */
	public void setDescription(String description) {
		this.strDescription = description;
	}
	//
	// /**
	// * Determines whether the extension list (.jpg, .gif, etc) should
	// * show up in the human readable description.
	// *
	// * Only relevent if a description was provided in the constructor
	// * or using setDescription();
	// *
	// * @see getDescription
	// * @see setDescription
	// * @see isExtensionListInDescription
	// */
	// public void setExtensionListInDescription(boolean b) {
	// useExtensionsInDescription = b;
	// fullDescription = null;
	// }
	//
	// /**
	// * Returns whether the extension list (.jpg, .gif, etc) should
	// * show up in the human readable description.
	// *
	// * Only relevent if a description was provided in the constructor
	// * or using setDescription();
	// *
	// * @see getDescription
	// * @see setDescription
	// * @see setExtensionListInDescription
	// */
	// public boolean isExtensionListInDescription() {
	// return useExtensionsInDescription;
	// }
	//    

}
