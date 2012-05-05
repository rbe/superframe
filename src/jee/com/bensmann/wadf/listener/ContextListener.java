/*
 * Created on 14.10.2004
 *
 */
package com.bensmann.wadf.listener;

import com.bensmann.superframe.java.Debug;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.bensmann.wadf.Configuration;

/**
 *
 * @author rb
 * @version $Id: ContextListener.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class ContextListener implements ServletContextListener {
    
    /**
     * 
     * @param event 
     */
    public void contextInitialized(ServletContextEvent event) {
        
        System.out.println("-----------> " + event.getServletContext().
                getRealPath("/"));
        
        // Initialize Configuration singleton with base path
        Configuration.getInstance(event.getServletContext().getRealPath("/"));
        
        // Debug
        Debug.log("Servlet Context init");
        
    }
    
    /**
     * 
     * @param event 
     */
    public void contextDestroyed(ServletContextEvent event) {
        
        // Debug
        Debug.log("Servlet Context deinit");
        
    }
    
}