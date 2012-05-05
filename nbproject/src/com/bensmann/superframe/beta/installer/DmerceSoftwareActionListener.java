/*
 * Created on 18.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * @author rb
 * @version $Id: DmerceSoftwareActionListener.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 *  
 */
public class DmerceSoftwareActionListener implements ActionListener {

	private DeploymentPanel deploymentPanel;

	public DmerceSoftwareActionListener(DeploymentPanel deploymentPanel) {
		this.deploymentPanel = deploymentPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		JFileChooser j = new JFileChooser();
		j.setAcceptAllFileFilterUsed(false);
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		String instPath = deploymentPanel.officeAddonTextField.getText();
		if (instPath.length() > 0)
			j.setSelectedFile(new File(instPath));
		else
			j
					.setSelectedFile(com.bensmann.superframe.beta.ui.Configuration.getInstance().discoveredOfficeInstPath);

		if (j.showDialog(deploymentPanel, "Installation wählen") == JFileChooser.APPROVE_OPTION) {
			deploymentPanel.officeAddonTextField.setText(j.getSelectedFile()
					.getAbsolutePath());
			deploymentPanel.enableInstMethodElements();
		}

	}

}