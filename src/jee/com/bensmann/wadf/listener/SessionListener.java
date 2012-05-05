/*
 * Created on 14.10.2004
 *
 */

package com.bensmann.wadf.listener;

import com.bensmann.superframe.java.Debug;
import com.bensmann.wadf.Configuration;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.bensmann.superframe.persistence.jdbc.JdbcConnectionManager;
import com.bensmann.superframe.persistence.jdbc.SingleJdbcConnection;
import java.sql.SQLException;
import java.util.Set;

/**
 *
 * @author rb
 * @version $Id: SessionListener.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class SessionListener implements HttpSessionListener {
    
    /**
     *
     * @param event
     */
    public void sessionCreated(HttpSessionEvent event) {
        
        // Debug
        Debug.log("Setting up JDBC database connections");
        
        // Go through all datasources, set up a connection and give it
        // to JdbcConnectionManager
        Set<String> set = Configuration.getInstance().getAllDatasources().
                keySet();
        for (String k : set) {
            
            SingleJdbcConnection sjc = Configuration.getInstance().
                    getDatasource(k);
            
            try {
                
                sjc.openConnection();
                JdbcConnectionManager.getInstance().addConnection(
                        event.getSession().getId(), sjc);
                
            } catch (ClassNotFoundException e) {
                
                // Debug
                Debug.log("COULD NOT LOAD JDBC DRIVER FOR CONNECTION " +
                        sjc.getJdbcUrl());
                
            } catch (SQLException e) {
                
                // Debug
                Debug.log("COULD NOT ESTABLISH JDBC DATABASE CONNECTION TO " +
                        sjc.getJdbcUrl());
                
            }
            
        }
        
        // Debug
        Debug.log("Session " + event.getSession().getId() + " created!");
        
    }
    
    /**
     *
     * @param event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        
        // Debug
        Debug.log("Destroying session " + event.getSession().getId());
        
        // Go through all datasources and close the connection
        Set<String> set = Configuration.getInstance().getAllDatasources().
                keySet();
        for (String k : set) {
            
            SingleJdbcConnection sjc = Configuration.getInstance().
                    getDatasource(k);
            
            try {
                sjc.closeConnection();
                JdbcConnectionManager.getInstance().removeConnection(
                        event.getSession().getId() + "_" + sjc.getJdbcUrl());
            } catch (Exception e) {
                Debug.log("Exception while closing database connection: " +
                        e.getClass().getName() + ": " + e.getMessage());
            }
            
        }
        
        // Debug
        Debug.log("Session " + event.getSession().getId() + " destroyed!");
        
    }
    
}
