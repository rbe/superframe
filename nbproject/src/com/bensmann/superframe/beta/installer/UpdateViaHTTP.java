package com.bensmann.superframe.beta.installer;

import com.bensmann.superframe.java.Debug;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Update this application through HTTP downloads
 * 
 * First check for a new version comparing actual version string with remote
 * version string from file "DMRC-7UPD"
 */
public class UpdateViaHTTP {

	/**
	 * Debug flag
	 */
	private boolean DEBUG = com.bensmann.superframe.beta.ui.Configuration.getInstance().DEBUG;

	/**
	 * URL for downloading updates from 1Ci
	 */
	private URL url;

	/**
	 * Constructor
	 */
	public UpdateViaHTTP() {
	}

	/**
	 * Download file with informations about updates
	 * 
	 * @throws java.io.IOException
	 */
	public void downloadUpdateInformation() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new URL(
				"http://downloads.1ci.com/DMRC-UPD").openStream()));

		// Debug
		Debug.log("");

	}

	public static void main(String[] args) throws Exception {

		new UpdateViaHTTP();

		URL a = new URL("file://t:/blanko.sxw");
		System.out.println(a.getAuthority() + "..."
				+ new File(a.getFile()).getName());

	}
}