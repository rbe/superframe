/*
 * Controller.java
 *
 * Created on 28. Juli 2005, 19:40
 *
 */

package com.bensmann.superframe.test.mvcrmi;

import com.bensmann.superframe.java.Debug;
import java.rmi.RemoteException;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class Controller {
    
    private View view;
    
    private Model model;
    
    /**
     * Creates a new instance of Controller
     * @param view
     * @param model
     */
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.registerController(this);
    }
    
    /**
     * The view told us that data should be updated. Instruct model to
     * update data.
     */
    public void handleUpdateDataGesture() {
        
        Debug.log("View updates data");
        
        try {
            model.updateModel();
        } catch (RemoteException e) {
            Debug.log("RemoteExcpetion: " + e);
        }
        
    }
    
}
