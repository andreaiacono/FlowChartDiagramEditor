package org.aitek.fcde.utils;

import java.io.*;

public class IoUtils {

	/**
	 * transforms a stack trace into a string
	 * 
	 * @param ex
	 *            The exception where to get the stack trace
	 * @return the stack trace
	 */
	public static String stackTraceToString(Exception ex) {

		// gets a new stream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// uses it as a source for a PrintWriter
		PrintWriter pwWriter = new PrintWriter(baos, true);

		// writes the stacktrace to the printwriter
		ex.printStackTrace(pwWriter);

		// and return the stream as a string
		return baos.toString();
	}

	/**
	 * transforms an inputstream into a string
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream in) throws IOException {

		// inits the buffers
		StringBuffer sbOut = new StringBuffer();
		byte[] b = new byte[4096];

		// cycles on the stream
		for (int n; (n = in.read(b)) != -1;) {

			// and appends every char to the buffer
			sbOut.append(new String(b, 0, n));
		}

		return sbOut.toString();
	}

	/**
	 * transforms an inputstream into an array of bytes
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] inputStreamToBytes(InputStream in) throws IOException {

		// define length
		int LENGTH = 512;

		// inits the output stream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte b[] = new byte[LENGTH];
		int numRead;

		// cycles on the stream, reading and writing it on the byte array
		while ((numRead = in.read(b, 0, LENGTH)) != -1)
			baos.write(b, 0, numRead);

		// closes the stream
		baos.close();

		// and returns it
		return baos.toByteArray();
	}

	/**
	 * copies from an inputstream to an output stream
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {

		// inits the buffers
		byte[] buffer = new byte[1024];
		int len;

		// reads from IS "buffer" bytes each step and writes them on the OS
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		// and closes the streams
		in.close();
		out.close();
	}

}
