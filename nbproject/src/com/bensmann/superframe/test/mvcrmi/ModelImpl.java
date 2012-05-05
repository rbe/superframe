/*
 * ModelImpl.java
 *
 * Created on 28. Juli 2005, 19:15
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import com.bensmann.superframe.java.Debug;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class ModelImpl extends UnicastRemoteObject implements Model {
    
    /**
     * List of Views (listeners of changes) that want to be informed of
     * changes in the model
     */
    public ArrayList<View> changeListeners = new ArrayList<View>();
    
    /**
     * Creates a new instance of ModelImpl
     * @throws java.rmi.RemoteException 
     */
    public ModelImpl() throws RemoteException {
    }
    
    /**
     * Register a View with this model
     * @param view 
     */
    public void registerView(View view) {
        changeListeners.add(view);
    }
    
    /**
     * The model changed, so inform all views about the change
     */
    private void fireChangeEvent() {
        
        Debug.log("Fireing change event to " + changeListeners.size() +
                " views");
        
        for (View v : changeListeners) {
            
            Debug.log("... view: " + v);
            v.handleModelChangeEvent();
            
            Debug.log("... dummy of view: " + v.getDummy());
            
        }
        
    }
    
    /**
     * Update data and fire change event
     */
    public void updateModel() throws RemoteException {
        // Do something
        Debug.log("Updating data");
        // Fire change event
        fireChangeEvent();
    }

}
