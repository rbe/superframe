/*
 * CrawlUrl.java
 *
 * Created on 18. Juni 2005, 13:52
 *
 */

package com.bensmann.superframe.beta.webspider;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: CrawlUrl.java,v 1.1 2005/07/19 15:51:42 rb Exp $
 */
public class CrawlUrl {
    
    /**
     * Creates a new instance of CrawlUrl
     */
    public CrawlUrl() {
    }
    
    /**
     *
     * @param url
     * @param fileType
     * @throws java.io.IOException
     */
    public File[] retrieveFilesFromUrl(URL url, FileType fileType)
    throws IOException {
        
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setContentHandlerFactory(
                new FilterContentHandlerFactory(fileType));
        String[] s = (String[]) urlConnection.getContent();
        
        File[] downloadedFiles = new File[s.length];
        int i = 0;
        
        for (String k : s) {
            
            System.out.println(k);
            urlConnection = new URL(k).openConnection();
            
            try {
                downloadedFiles[i++] = (File) urlConnection.getContent();
            } catch (ClassCastException e) {
                System.out.println(e.getMessage());
            }
            
        }
        
        return downloadedFiles;
        
    }
    
    public void saveUrl(URL url, String fileName) throws IOException {
        
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoInput(true);
        
        RawContentHandler rch = new RawContentHandler();
        File f = rch.getContent(urlConnection);
        
        if (fileName != null) {
            f.renameTo(new File(fileName));
        }
        
    }
    
    /**
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        /*
        new CrawlUrl().retrieveFilesFromUrl(
                new URL("http://shop.osborne.com/cgi-bin/oraclepress/downloads.html"),
                FileType.MPG);
         */
        new CrawlUrl().saveUrl(new URL("http://localhost:8084/WAF/vwclubms/index.jsp"), null);
    }
    
}