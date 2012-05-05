/*
 * FilterContentHandlerFactory.java
 *
 * Created on 18. Juni 2005, 21:36
 *
 */

package com.bensmann.superframe.beta.webspider;

import java.net.ContentHandler;
import java.net.ContentHandlerFactory;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: FilterContentHandlerFactory.java,v 1.1 2005/07/19 15:51:42 rb Exp $
 */
public class FilterContentHandlerFactory implements ContentHandlerFactory {
    
    private FileType fileType;
    
    /**
     * 
     * @param fileType 
     */
    public FilterContentHandlerFactory(FileType fileType) {
        this.fileType = fileType;
    }
    
    /**
     * 
     * @param mimeType 
     * @return 
     */
    public ContentHandler createContentHandler(String mimeType) {
        
        //System.out.println("createContentHandler(): " + mimeType);
        
        if (mimeType.equals("text/html")) {
            return new FilterHtmlLinksContentHandler(fileType);
        } else if (mimeType.equals("application/zip")) {
            return new RawContentHandler();
        } else if (mimeType.equals("application/pdf")) {
            return new RawContentHandler();
        }
        
        return null;
    }
    
}
