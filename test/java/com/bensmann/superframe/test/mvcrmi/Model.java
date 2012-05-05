/*
 * Model.java
 *
 * Created on 28. Juli 2005, 19:14
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public interface Model extends Remote {
    
    /**
     * Register a View with a Model
     */
    void registerView(View view) throws RemoteException;
    
    /**
     * Updates the model
     */
    void updateModel() throws RemoteException;
    
}
