/*
 * Created on 16.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.io.File;

/**
 * @author rb
 *  
 */
public class DiscoverInstallationPath {

	/**
	 *  
	 */
	private static String[] possibleStarOfficePaths = new String[] {
			"\\StarOffice7", "\\OpenOffice1.1.2", "\\OpenOffice1.1.1",
			"\\Programme\\StarOffice7", "\\Programme\\OpenOffice1.1.1",
			"\\Programme\\OpenOffice1.1.2" };

	/**
	 *  
	 */
	private static String[] possibleDmerceSoftwarePaths = new String[] {
			"\\dmerce", "\\Programme\\dmerce" };

	/**
	 * 
	 * @param drive
	 * @return
	 */
	private static File testDir(char drive, String[] possiblePaths) {

		File f;

		for (int i = 0; i < possiblePaths.length; i++) {
			
			f = new File(drive + ":" + possiblePaths[i]);
			if (f.exists())
				return f;

		}

		return null;

	}

	/**
	 * 
	 * @return
	 */
	public static File discoverStarOfficePath() {

		File f;

		for (char c = '\u0043'; c < '\u005A'; c++) {

			f = testDir(c, possibleStarOfficePaths);
			if (f != null)
				return f;

		}

		return null;

	}

	/**
	 * 
	 * @return
	 */
	public static File discoverDmerceSoftwarePath() {

		File f;

		for (char c = '\u0043'; c < '\u005A'; c++) {

			f = testDir(c, possibleDmerceSoftwarePaths);
			if (f != null)
				return f;

		}

		return null;

	}

}