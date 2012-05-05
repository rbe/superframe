/*
 * FileType.java
 *
 * Created on 18. Juni 2005, 19:47
 *
 */

package com.bensmann.superframe.beta.webspider;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: FileType.java,v 1.1 2005/07/19 15:51:42 rb Exp $
 */
public enum FileType {
    
    MPG(new String[] { ".mpg", ".mpeg" }), ZIP(new String[] { ".zip" }), PDF(new String[] { ".pdf" }), JPEG(new String [] { ".jpg", ".jpeg" }), GIF(new String[] { ".gif "});
    
    private String[] extensions;
    
    FileType(String[] extensions) {
        this.extensions = extensions;
    }
    
    /**
     * 
     * @param ext 
     * @return 
     */
    public boolean compareToIngoreCase(String ext) {
        
        for (String e : extensions) {
            if (ext.equalsIgnoreCase(e)) {
                return true;
            }
        }
        
        return false;
        
    }
    
    /**
     * 
     * @param str 
     * @return 
     */
    public boolean containsIgnoreCase(String str) {
        
        for (String e : extensions) {
            
            if (str.toLowerCase().contains(e.toLowerCase())) {
                return true;
            }
            
        }
        
        return false;
        
    }
    
}
