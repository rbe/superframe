/*
 * DatamodelException.java
 *
 * Created on 13. Mai 2006, 09:15
 *
 */

package com.bensmann.supersist.exception;

/**
 *
 * $Header$
 * @author rb
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
public class DatamodelException extends Exception {
    
    /**
     * Creates a new instance of DatamodelException
     */
    public DatamodelException() {
    }
    
    public DatamodelException(String message) {
        super(message);
    }
    
    public DatamodelException(Throwable throwable) {
        super(throwable);
    }
    
    public DatamodelException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
