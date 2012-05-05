/*
 * Created on May 20, 2004
 *
 */
package com.bensmann.wadf.exceptions;


/**
 * @author rb
 * @version $Id: TagException.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class TagException extends WafException {
    
    public TagException() {
    }
    
    public TagException(String message) {
        super(message);
    }
    
    public TagException(Exception e) {
        super(e.getMessage());
        this.setStackTrace(e.getStackTrace());
    }
    
}