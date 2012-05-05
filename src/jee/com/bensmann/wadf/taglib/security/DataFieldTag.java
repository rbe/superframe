/*
 * Created on 14.09.2004
 *
 */
package com.bensmann.wadf.taglib.security;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.bensmann.wadf.servlet.AuthInfo;
import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 * @author rb
 * @version $Id: DataFieldTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 * 
 * Sucht Daten aus dem AuthInfo Objekt und gibt sie aus
 *  
 */
public class DataFieldTag extends BnmTagSupport {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public int doEndTag() {

		// Hole AuthInfo aus Session Context
		AuthInfo authInfo = (AuthInfo) pageContext.getSession().getAttribute(
				"qAuthInfo");

		String text = "unknown";

		if (authInfo != null) {
			text = authInfo.getDataField(name);
		}

		try {
			pageContext.getOut().print(text);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;

	}

	/* (non-Javadoc)
	 * @see com.bensmann.wadf.taglib.base.BnmTagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

}