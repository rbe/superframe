package com.bensmann.wadf.servlet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class holds information about errors that occured within execution of
 * application. Error messages should be looked up through a resource bundle
 * 
 * This class should exists in every user session
 *  
 * @author rb
 * @version $Id: SessionErrorInfo.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class SessionErrorInfo {

    /**
     * List of errors. Errors should be retrieved in the order they occured
     */
    private List<String> errors = new LinkedList<String>();

    private Iterator iterator;

    public SessionErrorInfo() {
    }

    /**
     * Add error to list
     * 
     * @param error
     */
    public void addError(String error) {
        errors.add(error);
        iterator = errors.iterator();
    }

    /**
     * Do we have any errors that can be retrieved?
     * 
     * @return
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Return next error
     * 
     * @return
     */
    public String getNext() {
        return (String) iterator.next();
    }

}