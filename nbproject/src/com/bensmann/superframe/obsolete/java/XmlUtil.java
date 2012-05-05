/*
 * Created on 25.06.2003
 */
package com.bensmann.superframe.obsolete.java;

import java.beans.XMLEncoder;

/**
 * @author pg
 * @version $Id: XmlUtil.java,v 1.1 2005/07/19 15:51:39 rb Exp $
 *
 * Helper Class for debugging features
 */
public class XmlUtil {
    
    public static void dump(Object o) {
        XMLEncoder enc = new XMLEncoder(System.out);
        enc.writeObject(o);
        enc.close();
    }
    
    public static void debug(String str) {
        System.out.println(str);
    }
    
}