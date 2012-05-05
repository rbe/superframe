/*
 * ImageProperties.java
 *
 * Created on 20. November 2005, 20:12
 *
 */

package com.bensmann.wadf.image;

import java.io.IOException;
import java.util.Properties;

/**
 * This class holds configuration settings
 *
 * $Header$
 * @author rb
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
public final class ImageProperties {
    
    /**
     *
     */
    private static final Properties props;
    
    /**
     * Static initializer
     */
    static {
        
        props = new Properties();
        
        try {
            
            props.load(ImageProperties.class.getResourceAsStream(
                    "image.properties"));
            
        } catch (IOException e) {
        }
    
    }
    
    /**
     * Do not create a new instance of ImageProperties
     */
    private ImageProperties() {
    }
    
    /**
     * 
     * @param property 
     * @return 
     */
    public static final String get(String property) {
        return (String) props.get(property);
    }
    
}
