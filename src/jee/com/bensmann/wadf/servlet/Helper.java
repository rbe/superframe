/*
 * Created on 10.04.2005
 *
 */
package com.bensmann.wadf.servlet;

import java.util.StringTokenizer;

/**
 * Some methods to help :-)
 *
 * @author rb
 * @version $Id: Helper.java,v 1.1 2005/07/19 12:03:51 rb Exp $  
 */
public class Helper {

    /**
     * Construct relative path (without .jsp at end)
     * @param uri 
     * @return 
     */
    public static String getPathFromUri(String uri) {

        StringTokenizer st = new StringTokenizer(uri, "/");
        StringBuffer sb = new StringBuffer();
        
        while (st.hasMoreTokens()) {

            String s = st.nextToken();

            if (s.indexOf(".jsp") == -1) {
                sb.append("/" + s);
            }

        }

        return sb.toString();

    }

}