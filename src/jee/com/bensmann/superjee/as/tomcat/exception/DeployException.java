/*
 * DeployException.java
 *
 * Created on 13. Dezember 2006, 18:00
 *
 */

package com.bensmann.superjee.as.tomcat.exception;

/**
 *
 * @author rb
 */
public class DeployException extends TomcatException {
    
    /**
     * Creates a new instance of DeployException
     *
     * @param message 
     * @param throwable 
     */
    public DeployException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    /**
     * 
     * @param message 
     * @param throwable 
     */
    public DeployException(String message) {
        super(message, null);
    }
    
}
