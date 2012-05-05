/*
 * Created on 20.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

/**
 * @author rb
 * @version $Id: InstallAction.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 *  
 */
public class InstallAction implements ActionListener {

	private DeploymentPanel deploymentPanel;

	/**
	 * 
	 * @param deploymentPanel
	 */
	public InstallAction(DeploymentPanel deploymentPanel) {
		this.deploymentPanel = deploymentPanel;
	}

	/**
	 * 
	 *
	 */
	private void installFromInternet() {

		URL u = null;

		try {

			u = new URL(deploymentPanel.instMethodInternetServers
					.getSelectedItem().toString());
			BufferedReader br = new BufferedReader(new InputStreamReader(u
					.openStream()));
			String s;
			File f;
			FileWriter fw = null;
			String downloadFileName = "";

			if (deploymentPanel.softwareTypeComboBox.getSelectedItem().equals(
					"dmerce")) {

				downloadFileName = deploymentPanel.dmerceSoftwareTextField
						.getText();

			} else if (deploymentPanel.softwareTypeComboBox.getSelectedItem()
					.equals("Office Addon")) {

				downloadFileName = deploymentPanel.officeAddonTextField
						.getText();

			}

			downloadFileName += File.separator + "temp.dat";
			f = new File(downloadFileName);
			fw = new FileWriter(f);

			if (fw != null) {

				while ((s = br.readLine()) != null) {
					fw.write(s);
				}

				fw.close();

				JOptionPane.showMessageDialog(deploymentPanel, f
						.getAbsolutePath()
						+ " written", "Erfolgreicher Download",
						JOptionPane.INFORMATION_MESSAGE);

			} else
				JOptionPane.showMessageDialog(deploymentPanel, "Kann",
						"Fehler beim Download", JOptionPane.ERROR_MESSAGE);

		} catch (MalformedURLException e1) {

			if (u != null)
				JOptionPane.showMessageDialog(deploymentPanel,
						"Konnte die URL " + u.toExternalForm()
								+ " nicht kontaktieren.",
						"Fehler beim Download", JOptionPane.ERROR_MESSAGE);

		} catch (IOException e2) {

			JOptionPane.showMessageDialog(deploymentPanel,
					"Konnte keine Daten von der URL " + u.toExternalForm()
							+ " lesen!", "Fehler beim Download",
					JOptionPane.ERROR_MESSAGE);

		}

	} /*
	   * (non-Javadoc)
	   * 
	   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	   */

	public void actionPerformed(ActionEvent e) {

		if (deploymentPanel.instMethodInternetRadioButton.isSelected()) {
			installFromInternet();
		}

	}
}