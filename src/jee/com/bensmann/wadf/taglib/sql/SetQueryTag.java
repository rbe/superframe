/*
 * Created on 06.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * @author rb
 * @version $Id: SetQueryTag.java,v 1.1 2005/07/19 12:03:53 rb Exp $
 * 
 */
public class SetQueryTag extends BnmBodyTagSupport {

    /**
     * Ident of SQL query
     */
    private String name;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    /**
     * 
     */
    public int doAfterBody() throws JspException {

        // Get session object
        HttpSession session = pageContext.getSession();
        
        // Look for SQL query in session
        String sqlQuery = (String) session.getAttribute(name);

        // Check if the sql query is already stored in session
        if (sqlQuery == null) {

            // Get the SQL query (body content)
            String bodyContent = getBodyContent().getString().trim();

            // ... and store it in the session
            session.setAttribute("sql-" + name, bodyContent);

        }

        return SKIP_BODY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * Set name for SQL query
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}