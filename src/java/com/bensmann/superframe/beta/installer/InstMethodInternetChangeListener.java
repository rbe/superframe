/*
 * Created on 18.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author rb
 * @version $Id: InstMethodInternetChangeListener.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 *
 */
public class InstMethodInternetChangeListener implements ChangeListener {

	DeploymentPanel deploymentPanel;

	public InstMethodInternetChangeListener(DeploymentPanel soAddOnPanel) {
		this.deploymentPanel = soAddOnPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		deploymentPanel.disableInstMethodFs();
		deploymentPanel.instMethodInternetServers.setEnabled(true);
	}

}
