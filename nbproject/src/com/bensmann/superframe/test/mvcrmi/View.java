/*
 * View.java
 *
 * Created on 28. Juli 2005, 19:40
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import com.bensmann.superframe.java.Debug;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class View implements Serializable {
    
    private Model model;
    
    private Controller controller;
    
    private Dummy dummy;
    
    /** Creates a new instance of View */
    public View(Model model) {
        
        this.model = model;
        
        try {
            model.registerView(this);
        } catch (RemoteException e) {
            Debug.log("RemoteException: " + e);
        }

        // Another object...
        dummy = new Dummy();
        Debug.log("Initialized dummy=" + dummy);
        
    }
    
    /**
     *
     * @param controller
     */
    public void registerController(Controller controller) {
        this.controller = controller;
    }
    
    /**
     * The model has changed...
     */
    public void handleModelChangeEvent() {
        
        Debug.log("The model has changed!");
        Debug.log("dummy=" + dummy);
        
    }
    
    /**
     * We updated some data in the view, send it to the controller
     */
    public void updateDataGesture() {
        controller.handleUpdateDataGesture();
    }
    
    /**
     * 
     * @return 
     */
    public Dummy getDummy() {
        return dummy;
    }
    
}
