/*
 * Created on 19.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import javax.swing.JTabbedPane;

/**
 * @author rb
 * @version $Id: TabbedPane.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 *  
 */
public class TabbedPane extends JTabbedPane {

	public TabbedPane() {

		add("Informationen", null);
		add("Deployment",
				com.bensmann.superframe.beta.ui.Configuration.getInstance().deploymentPanel);
		add("Consulting/Support", null);
		add("Monitoring", null);
		//add("DownloadManager", Configuration.getInstance().downloadManager);
		add("Einstellungen", null);

	}

}