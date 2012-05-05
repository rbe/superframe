/*
 * Created on 18.07.2004
 *
 */
package com.bensmann.superframe.beta.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @author rb
 * @version $Id: UserInterfaceManager.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 * 
 * Useful functions for graphical user interfaces that are used in nearly every
 * application
 */
public class UserInterfaceManager {
    
    /**
     * Singleton
     */
    private static UserInterfaceManager singleton;
    
    private static String ui;
    
    /**
     * Image: 1Ci icon
     */
    public final ImageIcon image1CiIcon = new ImageIcon(this.getClass().
            getResource("1ci_icon.gif"));
    
    /**
     * Image: 1Ci logo
     */
    public final ImageIcon image1CiLogo = new ImageIcon(this.getClass().
            getResource("1ci_logo_klein.gif"));
    
    /**
     * Singleton pattern: private constructor
     *
     */
    private UserInterfaceManager() {
    }
    
    /**
     * Singleton pattern
     *
     */
    public static UserInterfaceManager getInstance() {
        
        if (singleton == null) {
            singleton = new UserInterfaceManager();
        }
        
        return singleton;
        
    }
    
    /**
     * Place a jFrame in the middle of the screen
     *
     * @param jFrame
     */
    public void setLocationInMiddleOfScreen(JFrame jFrame) {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jFrame.getSize();
        
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        
        jFrame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        
    }
    
    /**
     * Set default look and feel
     */
    public void setSystemLookAndFeel() {
        
        try {
            
            ui = UIManager.getSystemLookAndFeelClassName();
            
            if (ui.equals(
                    "com.sun.java.swing.plaf.motif.MotifLookAndFeel")) {
                ui = "javax.swing.plaf.metal.MetalLookAndFeel";
            }
            
            UIManager.setLookAndFeel(ui);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}