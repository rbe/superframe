/*
 * Checker.java
 *
 * Created on 29. Juli 2005, 09:30
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import com.bensmann.superframe.java.Debug;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class Checker extends UnicastRemoteObject implements Notify {
    
    /**
     * Creates a new instance of Checker
     * @param model 
     * @throws java.rmi.RemoteException 
     */
    public Checker(Model model) throws RemoteException {
    }

    public void signal() {
        Debug.log("We have been notified...");
    }
    
    public void update() {
        
    }
    
}
