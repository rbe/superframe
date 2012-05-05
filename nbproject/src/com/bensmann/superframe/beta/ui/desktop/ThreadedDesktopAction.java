/*
 * Created on 27.03.2005
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

/**
 * @author rb
 *
 */
public interface ThreadedDesktopAction extends Runnable {

	/**
	 * Handle event in a separated thread
	 *
	 */
	public void handleEvent();
	
	/**
	 * Enable or disable action
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled);
	
}