/*
 * Created on 28.07.2003
 *
 */
package com.bensmann.wadf.exceptions;

/**
 * @author rb
 * @version $Id: WafException.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class WafException extends Exception {
    
    public WafException() {
    }
    
    public WafException(String message) {
        super(message);
    }
    
    public WafException(Exception e) {
        super(e.getMessage());
        this.setStackTrace(e.getStackTrace());
    }
    
}