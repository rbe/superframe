/*
 * TomcatException.java
 *
 * Created on 14. Dezember 2006, 13:36
 *
 */

package com.bensmann.superjee.as.tomcat.exception;

/**
 *
 * @author rb
 */
public class TomcatException extends Exception{
    
    /**
     * Creates a new instance of TomcatException
     * @param message 
     */
    public TomcatException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
