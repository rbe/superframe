/*
 * Created on 08.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 * @author rb
 * @version $Id: SubmitButtonTag.java,v 1.1 2005/07/19 12:03:53 rb Exp $
 * 
 * Generate a submit button (&lt;input&gt;-tag) with additional information for
 * dispatcher servlet
 *
 * TODO: Move to package ...forms
 *  
 */
public class SubmitButtonTag extends BnmTagSupport {

    private String text;

    private String successPage;

    private String errorPage;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        try {
            pageContext.getOut().write(
                    "<input type=\"submit\" value=\"" + text + "\">");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Save success and error page in session
        pageContext.getSession().setAttribute("successpage", successPage);
        pageContext.getSession().setAttribute("errorpage", errorPage);

        return SKIP_BODY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * Set text for submit button
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set page that should be forwarded to when operation was successful
     * 
     * @param successPage
     */
    public void setSuccesspage(String successPage) {
        this.successPage = successPage;
    }

    /**
     * Set page that should be forwarded to when operation was successful
     * 
     * @param successPage
     */
    public void setErrorpage(String errorPage) {
        this.errorPage = errorPage;
    }

}