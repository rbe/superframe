/*
 * com/bensmann/supervise/oracle/webservice/exception/ServiceException.java
 *
 * ServiceException.java created on 15. Januar 2007, 13:54 by rb
 *
 * Copyright (C) 2006 Ralf Bensmann, java@bensmann.com
 *
 */

package com.bensmann.superjee.exception;

/**
 *
 * @author rb
 * @version 1.0
 */
public class ServiceException extends Exception {
    
    /**
     * Creates a new instance of ServiceException
     *
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }
    
    /**
     * Creates a new instance of ServiceException
     *
     * @param message
     * @param throwable
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
