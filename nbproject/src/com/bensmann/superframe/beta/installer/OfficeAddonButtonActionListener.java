/*
 * Created on 11.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * @author rb
 *  
 */
public class OfficeAddonButtonActionListener implements ActionListener {

	private DeploymentPanel soAddOnPanel;

	public OfficeAddonButtonActionListener(DeploymentPanel soAddOnPanel) {
		this.soAddOnPanel = soAddOnPanel;
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

		String instPath = soAddOnPanel.officeAddonTextField.getText();
		if (instPath.length() > 0)
			j.setSelectedFile(new File(instPath));
		else
			j
					.setSelectedFile(com.bensmann.superframe.beta.ui.Configuration.getInstance().discoveredOfficeInstPath);

		if (j.showDialog(soAddOnPanel, "Installation wählen") == JFileChooser.APPROVE_OPTION) {
			soAddOnPanel.officeAddonTextField.setText(j.getSelectedFile()
					.getAbsolutePath());
			soAddOnPanel.enableInstMethodElements();
		}

	}

}