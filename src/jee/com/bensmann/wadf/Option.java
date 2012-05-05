/*
 * Option.java
 *
 * Created on 17. Juni 2005, 21:25
 *
 */

package com.bensmann.wadf;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: Option.java,v 1.1 2005/07/19 12:03:50 rb Exp $
 */
public class Option {
    
    private String name;
    
    private String value;
    
    /**
     * Creates a new instance of Option
     */
    public Option(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
}
