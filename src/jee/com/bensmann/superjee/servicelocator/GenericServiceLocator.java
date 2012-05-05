/*
 * com/bensmann/supervise/webservice/GenericServiceLocator.java
 *
 * GenericServiceLocator.java created on 15. Januar 2007, 14:24 by rb
 *
 * Copyright (C) 2006 Ralf Bensmann, java@bensmann.com
 *
 */

package com.bensmann.superjee.servicelocator;

import com.bensmann.superjee.exception.ServiceException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Initialization On Demand Holder Idiom
 *
 * @author rb
 * @version 1.0
 */
public final class GenericServiceLocator {
    
    /**
     *
     */
    private Context context;
    
    /**
     * Lazy Holder for GenericServiceLocator
     */
    private static class LazyHolder {
        
        static final GenericServiceLocator instance;
        
        static {
            instance = new GenericServiceLocator();
        }
        
    }
    
    /**
     * Creates a new instance of GenericServiceLocator
     */
    private GenericServiceLocator() {
        
        try {
            context = new InitialContext();
        } catch (NamingException e) {
            // TODO
            e.printStackTrace();
        }
        
    }
    
    /**
     *
     * @return
     */
    public static GenericServiceLocator getInstance() {
        return LazyHolder.instance;
    }
    
    /**
     * 
     * @throws com.bensmann.superjee.exception.ServiceException 
     * @return 
     */
    public String[] getBindings(String prefix) throws ServiceException {
        
        List<String> list = new LinkedList<String>();
        Enumeration en = null;
        NameClassPair ncp = null;
        
        // Strip off slash
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 2);
        }
        
        try {
            
            // List bindings
            en = context.list(prefix);
            // Add binding to list
            while (en.hasMoreElements()) {
                ncp = (NameClassPair) en.nextElement();
                list.add(prefix + "/" + ncp.getName());
            }
            
        } catch (NamingException e) {
            throw new ServiceException(
                    "Cannot get list of bindings: " + e.toString(), e);
        }
        
        return list.toArray(new String[list.size()]);
        
    }
    
    /**
     *
     *
     * @param name
     * @return
     */
    public Object getObject(String name) throws ServiceException {
        
        Object object = null;
        
        if (context == null) {
            throw new ServiceException("Context not initialized!!!");
        }
        
        try {
            object = context.lookup(name);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServiceException("Could not find object " + name, e);
        }
        
        return object;
        
    }
    
    /**
     *
     * @param name
     * @return
     */
    public DataSource getDataSource(String name)
    throws ServiceException {
        
        DataSource ds = null;
        
        try {
            ds = (DataSource) getObject(name);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServiceException(
                    "Could not lookup datasource " + name, e);
        }
        
        return ds;
        
    }
    
}
