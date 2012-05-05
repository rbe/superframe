/*
 * RMIClient.java
 *
 * Created on 28. Juli 2005, 19:14
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import com.bensmann.superframe.java.Debug;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class RMIClient {
    
    /** Creates a new instance of RMIClient */
    public RMIClient() {
    }
    
    public static void main(String[] args) throws MalformedURLException,
            RemoteException, NotBoundException {
        
        // The model was bound to RMI by RMIServer
        Model m = (Model) Naming.lookup("//localhost/RMIServer");
        // Create View
        View v = new View(m);
        // Create Controller with View and ModelImpll
        Controller c = new Controller(v, m);
        // Register Controller with View
        v.registerController(c);
        
        // Simulate call from view using Checker
        //Checker checker = new Checker(v);
        //checker.updateDataGesture();
        
    }
    
}
