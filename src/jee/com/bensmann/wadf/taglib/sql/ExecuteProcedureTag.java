/*
 * Created on 10.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import javax.servlet.jsp.JspException;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * @author rb
 * @verson $Id: ExecuteProcedureTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 * 
 * TODO: 
 *  
 */
public class ExecuteProcedureTag extends BnmBodyTagSupport {

    /**
     * Name of stored procedure
     */
    private String name;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        return EVAL_BODY_INCLUDE;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        
        // Execute stored procedure
        
        return EVAL_PAGE;
        
    }

    /**
     * Set name of stored procedure that is called
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}