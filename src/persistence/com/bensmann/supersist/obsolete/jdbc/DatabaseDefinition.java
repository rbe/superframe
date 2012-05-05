/**
 * Created on Jan 29, 2003
 *
 */

package com.bensmann.supersist.obsolete.jdbc;

/**
 * @author rb
 *
 * TODO: Redefine a database definition: it is an interface implemented,
 * when a functionality/a class can be used with a certain database. So
 * most things would implement more than one interface
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface DatabaseDefinition {
	
	boolean isMySQL();
	
	boolean isOracle();
	
	boolean isPostgreSQL();
	
	boolean isPointbase();
	
	boolean isHsqldb();

}