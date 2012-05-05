/*
 * Created on 19.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * @author rb
 *  
 */
public class MainFrameMenuBar extends JMenuBar {

	protected JMenu fileMenu = new JMenu();

	protected JMenuItem fileExit = new JMenuItem();

	protected JMenu helpMenu = new JMenu();

	protected JMenuItem helpAbout = new JMenuItem();

	public MainFrameMenuBar() {

		// File
		fileMenu.setText("Datei");

		// File, Exit
		fileExit.setText("Beenden");
		fileExit.setIcon(com.bensmann.superframe.beta.ui.Configuration.getInstance().imageExit);
		fileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ExitConfirmationDialog
						.exitConfirmation(com.bensmann.superframe.beta.ui.Configuration.getInstance().mainFrame) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		fileMenu.add(fileExit);

		// Help
		helpMenu.setText("Hilfe");

		// Help, About
		helpAbout.setText("Über...");
		helpAbout
				.setIcon(com.bensmann.superframe.beta.ui.Configuration.getInstance().imageHelp);
		helpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		helpMenu.add(helpAbout);

		add(fileMenu);
		add(helpMenu);

	}

}