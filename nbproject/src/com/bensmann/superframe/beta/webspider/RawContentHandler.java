/*
 * RawContentHandler.java
 *
 * Created on 18. Juni 2005, 21:31
 *
 */

package com.bensmann.superframe.beta.webspider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: RawContentHandler.java,v 1.1 2005/07/19 15:51:42 rb Exp $
 */
public class RawContentHandler extends ContentHandler {
    
    private int readBytes;
    
    /**
     * 
     * @param urlConnection 
     * @throws java.io.IOException 
     * @return 
     */
    public File getContent(URLConnection urlConnection) throws IOException {
        
        StringBuffer out = new StringBuffer();
        BufferedInputStream in = new BufferedInputStream(
                urlConnection.getInputStream());
        int c;

        /*
        System.out.println("Guessed file type=" + urlConnection.guessContentTypeFromStream(urlConnection.getInputStream()));
        System.out.println("Guessed file type=" + urlConnection.guessContentTypeFromName(urlConnection.getURL().getFile()));
        */
        
        File file = new File(new File(urlConnection.getURL().getFile()).getName());
        FileOutputStream fout = new FileOutputStream(file);
        byte[] b = new byte[1024 * 32];
        
        try {
            
            // Read bytes from stream
            while((c = in.read(b)) != -1) {
                
                // Write bytes to FileOutputStream
                fout.write(b, 0, c);
                
                // Add count of read bytes to readBytes
                readBytes += c;
                
            }
            
        } finally {
            in.close();
            fout.close();
        }
        
        return file;
        
    }
    
}
