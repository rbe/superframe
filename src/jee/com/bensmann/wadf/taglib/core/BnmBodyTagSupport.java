/*
 * Created on May 17, 2004
 *
 */
package com.bensmann.wadf.taglib.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author rb
 * @version $Id: BnmBodyTagSupport.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *
 * Base class for taglibs
 *
 */
public abstract class BnmBodyTagSupport extends BodyTagSupport {
    
    public BnmBodyTagSupport() {
    }
    
    public abstract int doStartTag() throws JspException;
    
    public abstract int doEndTag() throws JspException;
    
}