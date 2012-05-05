/*
 * Created on 11.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author rb
 * @version $Id: CancelButtonActionListener.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 *  
 */
public class CancelButtonActionListener implements ActionListener {

	private JFrame jFrame;

	public CancelButtonActionListener(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (ExitConfirmationDialog.exitConfirmation(jFrame) == JOptionPane.YES_OPTION) {
			jFrame.dispose();
			System.exit(0);
		}

	}

}