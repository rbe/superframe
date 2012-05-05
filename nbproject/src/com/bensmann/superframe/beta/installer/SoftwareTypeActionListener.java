/*
 * Created on 18.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author rb
 * @version $Id: SoftwareTypeActionListener.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 *  
 */
public class SoftwareTypeActionListener implements ActionListener {

	private DeploymentPanel deploymentPanel;

	public SoftwareTypeActionListener(DeploymentPanel deploymentPanel) {
		this.deploymentPanel = deploymentPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (deploymentPanel.softwareTypeComboBox.getSelectedItem().equals(
				"Office Addon")) {
			
			deploymentPanel.disableDmerceSoftwareElements();
			deploymentPanel.enableOfficeAddonElements();
			
		}

		if (deploymentPanel.softwareTypeComboBox.getSelectedItem().equals(
				"dmerce")) {
			
			deploymentPanel.disableOfficeAddonElements();
			deploymentPanel.enableDmerceSoftwareElements();
			
		}

	}

}