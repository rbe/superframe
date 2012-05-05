/*
 * Created on 12.09.2004
 *
 */
package com.bensmann.wadf.servlet;

import com.bensmann.superframe.exception.XmlPropertiesFormatException;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author rb
 * @version $Id: AuthInfo.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *
 */
public class AuthInfo {
    
    private String username;
    
    private String realm;
    
    private Map dataFields;
    
    private int activeSessionsCount = 0;
    
    /**
     *
     * @param dataFields
     */
    public AuthInfo(String username, Map dataFields)
    throws XmlPropertiesFormatException {
        
        this.username = username;
        this.dataFields = dataFields;
        // TODO
//        this.realm = getDataField(XmlPropertiesReader.getInstance()
//        .getProperty("auth.realmfield"));
        
    }
    
    public int getActiveSessionsCount() {
        return activeSessionsCount;
    }
    
    public String getDataField(String field) {
        
        Object o = dataFields.get(field);
        
        if (o instanceof String)
            return (String) dataFields.get(field);
        else if (o instanceof Integer | o instanceof BigInteger
                | o instanceof Float | o instanceof Double)
            return String.valueOf(dataFields.get(field));
        else
            return "unknown";
        
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getRealm() {
        return realm;
    }
    
    public void setActiveSessionsCount(int count) {
        activeSessionsCount = count;
    }
    
}