/*
 * Created on 16.07.2004
 *  
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

class WNCFileFilter extends FileFilter {

	public String getDescription() {
		return "1Ci Installation ZIP files (WNC*.zip)";
	}

	public boolean accept(File f) {
		if (f == null) {
			return false;
		}

		if (f.isDirectory()) {
			return true;
		}

		return f.getName().toLowerCase().startsWith("wnc")
				&& f.getName().toLowerCase().endsWith(".zip");
	}

}

/**
 * @author rb
 * @version $Id: InstMethodFsActionListener.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 *  
 */

public class InstMethodFsActionListener implements ActionListener {

	private DeploymentPanel soAddOnPanel;

	public InstMethodFsActionListener(DeploymentPanel soAddOnPanel) {
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
		j.addChoosableFileFilter(new WNCFileFilter());

		if (j.showDialog(soAddOnPanel, "Quelle wählen") == JFileChooser.APPROVE_OPTION) {
			soAddOnPanel.instMethodFsTextArea.setText(j.getSelectedFile()
					.getAbsolutePath());
		}

	}

}