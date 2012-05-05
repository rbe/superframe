/*
 * Created on 11.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.bensmann.superframe.beta.ui.UserInterfaceManager;

/**
 * @author rb
 * @version $Id: MainFrame.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 *  
 */
public class MainFrame extends JFrame {

	public MainFrame() {

		setSize(new Dimension(520, 500));
		setResizable(false);
		setTitle("[Vision]");
		setIconImage(UserInterfaceManager.getInstance().image1CiIcon.getImage());

		setJMenuBar(new MainFrameMenuBar());
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new InstallerWindowAdapter(this));

		getContentPane().setLayout(new BorderLayout());

	}

}