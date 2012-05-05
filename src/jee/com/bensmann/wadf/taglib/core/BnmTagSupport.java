/*
 * Created on May 17, 2004
 *  
 */
package com.bensmann.wadf.taglib.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author rb
 * @version $Id: BnmTagSupport.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 * 
 * Base class for taglibs
 *  
 */
public abstract class BnmTagSupport extends TagSupport {

	protected boolean DEBUG = false;

	protected boolean DEBUG2 = false;

	public BnmTagSupport() {

		/*
		try {

			DEBUG = XmlPropertiesReader.getInstance().getPropertyAsBoolean(
					"debug");
			DEBUG2 = XmlPropertiesReader.getInstance().getPropertyAsBoolean(
					"core.debug");

		} catch (XmlPropertiesFormatException e) {
		}
		*/

	}

	public abstract int doStartTag() throws JspException;

	public abstract int doEndTag() throws JspException;

}