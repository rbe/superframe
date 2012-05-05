/*
 * Datei angelegt am 21.11.2003
 */
package com.bensmann.wadf.taglib.pages;

import com.bensmann.superframe.java.Debug;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * @author rb
 * @version $Id: SetTemplateTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 * 
 * Used in pages that use bricks to identify layout brick to use
 * 
 * TODO: Error when body is empty
 *  
 */
public class SetTemplateTag extends BodyTagSupport {

    /**
     * Path of JSP layout file to use
     */
    private String path;

    /**
     *  
     */
    public int doStartTag() throws JspException {

        // Set JSP template
        pageContext.getRequest().setAttribute("brickset-template",
                new BrickSet());
        
        // Set JSP code
        pageContext.getRequest().setAttribute("brickset-code", new BrickSet());

        return EVAL_BODY_BUFFERED;

    }

    /**
     *  
     */
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    /**
     *  
     */
    public int doEndTag() throws JspException {

        try {

            // Debug
            Debug.log("UseTemplate trying to forward to " + path);

            // Clear so far generated content
            pageContext.getOut().clearBuffer();
            
            // Forward to JSP
            pageContext.forward(path);

        }
        catch (Exception e) {

            e.printStackTrace();
            throw new JspException(e.getCause());

        }

        return SKIP_PAGE;

    }

    /**
     * @param path
     *            The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

}