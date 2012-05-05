/*
 * Created on 03.10.2004
 *
 */
package com.bensmann.wadf.taglib.security;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.bensmann.wadf.servlet.AuthInfo;
import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 * @author rb
 * @version $Id: CountActiveSessionsTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 *  
 */
public class CountActiveSessionsTag extends BnmTagSupport {

	public int doEndTag() {

		AuthInfo authInfo = (AuthInfo) pageContext.getSession().getAttribute(
				"qAuthInfo");

		try {
			pageContext.getOut().print(authInfo.getActiveSessionsCount());
		} catch (IOException e) {
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