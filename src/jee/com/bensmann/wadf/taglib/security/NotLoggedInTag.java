/*
 * Created on 12.09.2004
 *
 */
package com.bensmann.wadf.taglib.security;

import javax.servlet.jsp.JspTagException;

import com.bensmann.wadf.servlet.AuthInfo;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * @author rb
 * @version $Id: NotLoggedInTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 * 
 * Tag, welches seinen Body nur dann ausgibt, wenn kein Benutzer eingeloggt ist
 *  
 */
public class NotLoggedInTag extends BnmBodyTagSupport {

	public int doStartTag() throws JspTagException {

		// Hole AuthInfo aus Session Context
		AuthInfo authInfo = (AuthInfo) pageContext.getSession().getAttribute(
				"qAuthInfo");

		if (authInfo == null)
			return EVAL_BODY_INCLUDE;
		else
			return SKIP_BODY;

	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}