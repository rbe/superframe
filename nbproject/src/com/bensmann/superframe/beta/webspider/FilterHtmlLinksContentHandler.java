/*
 * FilterHtmlLinksContentHandler.java
 *
 * Created on 18. Juni 2005, 21:34
 *
 */

package com.bensmann.superframe.beta.webspider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ContentHandler;
import java.net.URLConnection;
import java.util.Vector;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: FilterHtmlLinksContentHandler.java,v 1.1 2005/07/19 15:51:42 rb Exp $
 */
public class FilterHtmlLinksContentHandler extends ContentHandler {
    
    /**
     * Filetype to filter links
     */
    private FileType fileType;
    
    /**
     *
     */
    public FilterHtmlLinksContentHandler() {
    }
    
    /**
     * 
     * @param fileType 
     */
    public FilterHtmlLinksContentHandler(FileType fileType) {
        this.fileType = fileType;
    }
    
    /**
     * 
     * @param urlConnection 
     * @throws java.io.IOException 
     * @return 
     */
    public String[] getContent(URLConnection urlConnection) throws IOException {
        
        final Vector<String> v = new Vector<String>();
        
        ParserCallback callback = new ParserCallback() {
            
            public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
                
                if (t == Tag.A) {
                    
                    String href = (String) a.getAttribute(Attribute.HREF);
                    
                    // Should we filter by filetype?
                    if (fileType != null) {
                        if (fileType.containsIgnoreCase(href)) {
                            v.add(href);
                        }
                    } else {
                        v.add(href);
                    }
                    
                }
                
            }
            
            public void handleText(char[] data, int pos) {
                //System.out.println(data);
            }
            
            public void handleEndTag(Tag t, int pos) {
                super.handleEndTag(t, pos);
            }
            
        };
        
        Reader reader = new InputStreamReader(new BufferedInputStream(
                urlConnection.getInputStream()));
        new ParserDelegator().parse(reader, callback, false);
        
        return v.toArray(new String[v.size()]);
        
    }
    
}
