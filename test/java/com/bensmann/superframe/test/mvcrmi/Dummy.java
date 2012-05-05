/*
 * Dummy.java
 *
 * Created on 28. Juli 2005, 22:31
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import java.io.Serializable;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class Dummy implements Serializable {
    
    private int i;
    
    /** Creates a new instance of Dummy */
    public Dummy() {
        i = 5;
    }
    
    public int getI() {
        return i;
    }
    
}
