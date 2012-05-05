/*
 * Created on 11.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author rb
 *  
 */
public class ExitConfirmationDialog {

	public static int exitConfirmation(JFrame jFrame) {
		return JOptionPane.showOptionDialog(jFrame,
				"Wollen Sie wirklich beenden?", "Installation beenden",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
	}

}