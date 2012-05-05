/*
 * Created on 18.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author rb
 * @version $Id: InstMethodFsChangeListener.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 * 
 * When installation method "filesystem" was chosen, disable combobox for
 * internet servers and enable all components for choosing parameters for
 * filesystem
 *  
 */
public class InstMethodFsChangeListener implements ChangeListener {

	DeploymentPanel deploymentPanel;

	public InstMethodFsChangeListener(DeploymentPanel soAddOnPanel) {
		this.deploymentPanel = soAddOnPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		deploymentPanel.enableInstMethodFs();
		deploymentPanel.instMethodInternetServers.setEnabled(false);
	}

}