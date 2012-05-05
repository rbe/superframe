/*
 * Created on Jan 28, 2003
 *
 */

package com.bensmann.supersist.obsolete.jdbc;

/**
 * @author rb
 * @version $Id: Datatype.java,v 1.1 2005/07/19 15:51:41 rb Exp $
 *
 */
interface Datatype {
	
	/**
     * 
	 * @return
	 */
    int getLength();
	
	/**
     * 
	 * @return
	 */
    String getMySQLDefinition();
	
	/**
     * 
	 * @return
	 */
    String getOracleDefinition();
	
	/**
     * 
	 * @return
	 */
    int getPrecision();
	
	/**
     * 
	 * @return
	 */
    String getPointbaseDefinition();

	/**
     * 
	 * @return
	 */
    String getPostgreSQLDefinition();
	
	/**
     * 
	 * @param length
	 */
    void setLength(int length);
	
	/**
     * 
	 * @param precision
	 */
    void setPrecision(int precision);
	
}