/*
 * Created on 19.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import javax.swing.JFrame;

/**
 * @author rb
 *  
 */
public class ExitAction {

	/**
	 * Perform a clean shutdown
	 * 
	 * @param exitCode
	 */
	public static void exit(JFrame jFrame, int exitCode) {
		jFrame.dispose();
		System.exit(exitCode);
	}

}