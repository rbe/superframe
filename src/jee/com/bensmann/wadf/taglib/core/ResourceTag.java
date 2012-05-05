package com.bensmann.wadf.taglib.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;

import com.bensmann.wadf.servlet.Helper;

/**
 * 
 * @author rb
 * @version $Id: ResourceTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *  
 */
public class ResourceTag extends BnmTagSupport {

    /**
     * name der variable
     */
    private String path;

    /**
     * Constructor
     *  
     */
    public ResourceTag() {
    }

    /**
     * Method called at start of tag
     * 
     * @return SKIP_BODY
     */
    public int doStartTag() {
        return SKIP_BODY;
    }

    /**
     * Methode die die angegebene variable ausliest und direkt ausgibt falls
     * kein scope angegeben wurde, liest die methode erst aus dem request, dann
     * aus der session ansonsten wird direkt aus der session oder aus dem
     * request gelesen
     * 
     * @return EVAL_PAGE
     */
    public int doEndTag() throws JspTagException {

        HttpServletRequest servletRequest = (HttpServletRequest) pageContext
                .getRequest();

        // Get context path
        String contextPath = servletRequest.getContextPath();

        // Get request URI
        String requestUri = servletRequest.getRequestURI();
        String sb = Helper.getPathFromUri(requestUri);

        // Add slash if necessary
        if (path.charAt(0) == '/') {
            path = sb.toString() + path;
        }
        else {
            path = sb.toString() + "/" + path;
        }

        // Output
        try {
            if (path != null)
                pageContext.getOut().write(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }

    /**
     * Setter for path
     * 
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

}