/*
 * Created on 11.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author rb
 *  
 */
public class InstallerWindowAdapter extends WindowAdapter {

	private JFrame jFrame;

	public InstallerWindowAdapter(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {

		if (ExitConfirmationDialog.exitConfirmation(jFrame) == JOptionPane.YES_OPTION) {
			ExitAction.exit(jFrame, 0);
		}

	}

}