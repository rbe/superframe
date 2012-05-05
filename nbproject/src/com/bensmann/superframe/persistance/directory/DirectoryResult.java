/*
 * DirectoryResult.java
 *
 * Created on 31. Oktober 2005, 13:48
 *
 */

package com.bensmann.superframe.persistance.directory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 *
 * $Header$
 * @author Ralf Bensmann
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
public class DirectoryResult implements Serializable, Map {
    
    /**
     *
     */
    private Map attrs = new Hashtable();
    
    /**
     * Creates a new instance of DirectoryResult
     */
    public DirectoryResult() {
    }
    
    /**
     *
     * @param name
     * @param value
     */
    public void putAttribute(String name, String value) {
        attrs.put(name, value);
    }
    
    /**
     *
     * @param name
     * @return
     */
    public String getAttribute(String name) {
        return (String) attrs.get(name);
    }
    
    /**
     * Return all attributes
     * @return
     */
    public Map getAttributes() {
        return attrs;
    }
    
    /**
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return getAttribute(key);
    }
    
    /**
     * Delegate
     * @return
     */
    public Set entrySet() {
        return attrs.entrySet();
    }
    
    /**
     * Delegate
     * @return
     */
    public Object remove(Object key) {
        return attrs.remove(key);
    }
    
    /**
     * Delegate
     * @return
     */
    public Object get(Object key) {
        return attrs.get(key);
    }
    
    /**
     * Delegate
     * @return
     */
    public boolean containsValue(Object value) {
        return attrs.containsValue(value);
    }
    
    /**
     * Delegate
     * @return
     */
    public boolean containsKey(Object key) {
        return attrs.containsKey(key);
    }
    
    /**
     * Delegate
     */
    public void putAll(Map map) {
        attrs.putAll(map);
    }
    
    /**
     * Delegate
     * @return
     */
    public Collection values() {
        return attrs.values();
    }
    
    /**
     * Delegate
     * @return
     */
    public int size() {
        return attrs.size();
    }
    
    public int getSize() {
        return size();
    }
    
    /**
     * Delegate
     * @return
     */
    public Object put(Object key, Object value) {
        return attrs.put(key, value);
    }
    
    /**
     * Delegate
     * @return
     */
    public Set keySet() {
        return attrs.keySet();
    }
    
    /**
     * Delegate
     * @return
     */
    public boolean isEmpty() {
        return attrs.isEmpty();
    }
    
    /**
     * Delegate
     */
    public void clear() {
        attrs.clear();
    }
    
}
