/*
 * RMIServer.java
 *
 * Created on 28. Juli 2005, 19:12
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import com.bensmann.superframe.java.Debug;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;


/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class RMIServer {
    
    /** Creates a new instance of RMIServer */
    public RMIServer() {
    }
    
    public static void main(String[] args) throws MalformedURLException,
            RemoteException {
        
        ModelImpl m = new ModelImpl();
        Naming.rebind("//localhost/RMIServer", m);
        
        Debug.log("RMIServer bound to registry");
        
    }

}
