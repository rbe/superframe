/**
 * Created on Jan 30, 2003
 *
 */

package com.bensmann.supersist.obsolete.jdbc;

/**
 * @author rb
 * @version $Id: AbstractDatatype.java,v 1.1 2005/07/19 15:51:41 rb Exp $
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractDatatype implements Datatype {

	private int length;

	private int precision;

	public AbstractDatatype() {
		setLength(0);
		setPrecision(0);
	}

	public AbstractDatatype(int length) {
		setLength(length);
		setPrecision(0);
	}

	public AbstractDatatype(int length, int precision) {
		setLength(length);
		setPrecision(precision);
	}

	public int getLength() {
		return length;
	}

	public abstract String getMySQLDefinition();

	public abstract String getOracleDefinition();

	public int getPrecision() {
		return precision;
	}

	public abstract String getPointbaseDefinition();

	public abstract String getPostgreSQLDefinition();

	public void setLength(int length) {
		if (length > 0)
			this.length = length;
		else
			this.length = 0;
	}

	public void setPrecision(int precision) {
		if (precision > 0)
			this.precision = precision;
		else
			this.precision = 0;
	}

}