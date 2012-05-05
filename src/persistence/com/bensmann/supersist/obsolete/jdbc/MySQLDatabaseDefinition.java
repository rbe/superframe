/**
 * Created on Jan 29, 2003
 *
 */

package com.bensmann.supersist.obsolete.jdbc;

/**
 * @author rb
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MySQLDatabaseDefinition implements DatabaseDefinition {

	public static String vendor = "MySQL";

	public static String URL = "http://www.mysql.com/";

	public MySQLDatabaseDefinition() {
	}

	/**
	 * @see com.wanci.dmerce.dmf.JdbcDatabaseDefinition#isMySQL()
	 */
	public boolean isMySQL() {
		return true;
	}

	/**
	 * @see com.wanci.dmerce.dmf.JdbcDatabaseDefinition#isOracle()
	 */
	public boolean isOracle() {
		return false;
	}

	/**
	 * @see com.wanci.dmerce.dmf.JdbcDatabaseDefinition#isPostgreSQL()
	 */
	public boolean isPostgreSQL() {
		return false;
	}

	/**
	 * @see com.wanci.dmerce.dmf.JdbcDatabaseDefinition#isPointbase()
	 */
	public boolean isPointbase() {
		return false;
	}

	/**
	 * @see com.wanci.dmerce.dmf.JdbcDatabaseDefinition#isHsqldb()
	 */
	public boolean isHsqldb() {
		return false;
	}

}