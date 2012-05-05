/*
 * Created on 27.03.2005
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

import javax.swing.JFrame;

/**
 * @author rb
 *  
 */
public class DialogTester {

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(800,600);
		j.setVisible(true);
		new DesktopDialog(j);
	}

}