/*
 * Created on 11.07.2003
 *
 */
package com.bensmann.superframe.beta.cluster;

/**
 * @author rb
 * @version $Id: Resource.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 *
 */
public interface Resource {
	
	/**
	 * 
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 
	 *
	 */
	void start();
	
	/**
	 * 
	 *
	 */
	void stop();

}