/*
 * DatamodelBean.java
 *
 * Created on 16. Mai 2006, 14:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.bensmann.superframe.persistance.jdbc;

import java.io.Serializable;

/**
 *
 * @author rb
 */
public interface DatamodelBean extends Serializable {
    
    /**
     * Reset all data set in the bean
     */
    void reset();

}
