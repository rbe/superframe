/*
 * HtmlContentHandler.java
 *
 * Created on 18. Juni 2005, 21:36
 *
 */

package com.bensmann.superframe.beta.webspider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: HtmlContentHandler.java,v 1.1 2005/07/19 15:51:42 rb Exp $
 */
public class HtmlContentHandler extends ContentHandler {
    
    public String getContent(URLConnection urlConnection) throws IOException {
        
        StringBuffer out = new StringBuffer();
        BufferedInputStream in = new BufferedInputStream(
                urlConnection.getInputStream());
        int c;
        
        try {
            
            c = in.read();
            while(c != -1) {
                out.append((char) c);
                c = in.read();
            }
            
        } finally {
            in.close();
        }
        
        out.append('\n');
        
        return out.toString();
        
    }
    
}
