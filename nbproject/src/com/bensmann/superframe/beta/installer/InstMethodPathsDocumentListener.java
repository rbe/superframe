/*
 * Created on 20.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author rb
 * @version $Id: InstMethodPathsDocumentListener.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 * 
 * Class that listenes to content of text fields and enables or disables
 * components depending on length and/or content of text field
 *  
 */
public class InstMethodPathsDocumentListener implements DocumentListener {

	private DeploymentPanel deploymentPanel;

	/**
	 * 
	 * @param deploymentPanel
	 */
	public InstMethodPathsDocumentListener(DeploymentPanel deploymentPanel) {
		this.deploymentPanel = deploymentPanel;
	}

	/**
	 * Does file/path "file" exist?
	 * 
	 * @param file
	 * @return
	 */
	public boolean fileExists(String file) {
		return new File(file).exists();
	}

	/**
	 * Check the path entered in a document
	 * 
	 * @param document
	 * @return
	 */
	private boolean check(Document document) {

		boolean b = false;

		try {

			if (document.getLength() > 0
					&& fileExists(document.getText(0, document.getLength())))
				b = true;

		} catch (BadLocationException e) {
		}

		return b;

	}

	public void changedUpdate(DocumentEvent e) {

		if (check(e.getDocument()))
			deploymentPanel.enableInstMethodElements();
		else
			deploymentPanel.disableInstMethodElements();

	}

	public void insertUpdate(DocumentEvent e) {

		if (check(e.getDocument()))
			deploymentPanel.enableInstMethodElements();
		else
			deploymentPanel.disableInstMethodElements();

	}

	public void removeUpdate(DocumentEvent e) {

		if (check(e.getDocument()))
			deploymentPanel.enableInstMethodElements();
		else
			deploymentPanel.disableInstMethodElements();

	}

}