/*
 * Created on 27.03.2005
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

/**
 * @author rb
 * @version $Id: ApplHelper.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 * 
 * Help desktop applications
 * 
 * Provide access to configuration settings; Access to registered actions (from
 * MenuCreator)
 * 
 * Singleton pattern
 *  
 */
public class ApplHelper {

	/**
	 * Singleton pattern; instance of ApplHelper
	 */
	private static ApplHelper singleton;

	/**
	 * Registered actions; loaded i.e. by MenuCreator
	 */
	//private HashMap registeredActions = new HashMap();
	/**
	 * Private constructor; singleton pattern
	 *  
	 */
	private ApplHelper() {
	}

	public static ApplHelper getInstance() {

		if (singleton == null) {
			singleton = new ApplHelper();
		}

		return singleton;

	}

	/**
	 * Load a class that extends AbstractBaseAction, create a new instance and
	 * return that newly created object
	 * 
	 * @param classname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AbstractBaseAction loadActionClass(String classname) {

		Class c = null;
		AbstractBaseAction b = null;

		try {
			
			c = this.getClass().getClassLoader().loadClass(classname);
			
			try {
				b = (AbstractBaseAction) c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return b;

	}

}