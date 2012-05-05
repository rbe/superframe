/*
 * Datei angelegt am 21.11.2003
 */
package com.bensmann.wadf.taglib.pages;

import com.bensmann.superframe.java.Debug;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * @author rb
 * @version $Id: SetBrickContentTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 * 
 * This tag is used to set a file or content via tag body to include for a brick
 *  
 */
public class SetBrickContentTag extends BodyTagSupport {

    /**
     * Name of brick
     */
    private String name;

    /**
     * Path of JSP to include (body content should be empty)
     */
    private String path;

    /**
     *  
     */
    public int doStartTag() throws JspException {

        // Get brick set from request
        BrickSet brickset = (BrickSet) pageContext.getRequest().getAttribute(
                "brickset-template");

        // Add JSP template path
        if (path != null) {
            brickset.addTemplate(name, path);
        }

        // Debug
        Debug.log("Setting content for brick '" + name + "' path is '" + path +
                "'");

        // Fill BodyContent ...
        return EVAL_BODY_BUFFERED;

    }

    /**
     *  
     */
    public int doAfterBody() throws JspException {

        // Include body only if no JSP template path was given
        if (path == null) {

            BrickSet brickset = (BrickSet) pageContext.getRequest()
                    .getAttribute("brickset-code");

            // Add content of body instead of filename to brick set. Trim
            // content of body to prevent additional newline etc. to be included
            // after brick content
            brickset.addTemplate(name, getBodyContent().getString().trim());

            // Debug
            Debug.log("SetBrickContent for '" + name + "' content length is " +
                    getBodyContent().getBufferSize());

        }

        // Omit body - was included via getBodyContent()
        return SKIP_BODY;

    }

    /**
     * Set ID of brick to use for content
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set path to JSP for brick content
     * 
     * @param path
     *            The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

}