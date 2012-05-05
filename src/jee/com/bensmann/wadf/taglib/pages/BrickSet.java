/*
 * Created on 21.11.2003
 */
package com.bensmann.wadf.taglib.pages;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rb
 * @version $Id: BrickSet.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 *
 * Set/define a brick set: a set of templates
 *
 */
public class BrickSet {
    
    /**
     * A hashmap to keep all bricks of this set
     */
    private Map<String, String> templates = new HashMap<String, String>();
    
    public BrickSet() {
    }
    
    public void addTemplate(String key, String template) {
        templates.put(key, template);
    }
    
    public String getTemplate(String key) {
        return (String) templates.get(key);
    }
    
}