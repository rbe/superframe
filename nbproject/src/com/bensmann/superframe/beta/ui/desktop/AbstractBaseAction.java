package com.bensmann.superframe.beta.ui.desktop;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

/**
 * Abstrakte Oberklasse f�r alle Actionen der Applikation sorgt daf�r das
 * Actions in einem seperaten Thread ausgef�hrt werden, damit die
 * Reaktionsf�higkeit der Oberfl�che erhalten bleibt.
 * 
 * @author rb
 * @version $Id: AbstractBaseAction.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 *  
 */
public abstract class AbstractBaseAction extends AbstractAction implements
		ThreadedDesktopAction {

	/** Komponente die den Event ausgel�st hat */
	//protected JComponent source;
	/** eigener Enabled-State */
	private boolean customEnabledState = true;

	protected JComponent comp = null;

	/** ActionCommand */
	protected String command;

	public void actionPerformed(ActionEvent ae) {
		//source = (JComponent)ae.getSource();
		command = ae.getActionCommand();
		//SwingUtilities.invokeLater(this);
		handleEvent();
	}

	/** Implementierung des Runnable-Interfaces */
	public void run() {
		handleEvent();
	}

	/**
	 * Sperren oder Freigeben der Aktion
	 * 
	 * @param arg
	 *            boolean
	 */
	public void setEnabled(boolean arg) {
		super.setEnabled(arg);

		customEnabledState = arg;

		if (comp != null)
			comp.setEnabled(customEnabledState);
	}

	public void setComponent(JComponent comp) {
		this.comp = comp;
		comp.setEnabled(customEnabledState);
	}

	/**
	 * Handle event. Method must be overridden in subclasses.
	 */
	public abstract void handleEvent();
};