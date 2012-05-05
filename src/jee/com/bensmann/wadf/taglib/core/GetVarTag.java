package com.bensmann.wadf.taglib.core;
import com.bensmann.superframe.java.Debug;

/**
 * Return (rendered) value of a variable from session or request. When two
 * variables with same name exist in session and request, the variable in the
 * request is preferred. The attribute 'scope' enables to explicitly choose the
 * context for variable.
 *
 * @author rb
 * @version $Id: GetVarTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class GetVarTag extends BnmTagSupport {
    
    /**
     * Name of variable to get
     */
    private String name;
    
    /**
     * Scope
     */
    private String scope;
    
    /**
     * Method called at start of Tag
     *
     * @return SKIP_BODY
     */
    public int doStartTag() {
        return SKIP_BODY;
    }
    
    /**
     * Methode die die angegebene variable ausliest und direkt ausgibt falls
     * kein scope angegeben wurde, liest die methode erst aus dem request, dann
     * aus der session ansonsten wird direkt aus der session oder aus dem
     * request gelesen
     *
     * @return EVAL_PAGE
     */
    public int doEndTag() {
        
        String value = null;
        
        // Read value
        if (scope == null || scope.equals("")) {
            value = getValue();
        } else if (scope.equals("session")) {
            value = getValueFromSession();
        } else if (scope.equals("request")) {
            value = getValueFromRequest();
        }
        
        // output
        try {
            
            if (value != null) {
                pageContext.getOut().write(value);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return EVAL_PAGE;
        
    }
    
    public String getValue() {
        
        String value = getValueFromRequest();
        
        if (value == null) {
            value = getValueFromSession();
        }
        
        return value;
        
    }
    
    /**
     * Getter for request
     *
     * @return Variable aus dem Request
     */
    private String getValueFromRequest() {
        
        String str = pageContext.getRequest().getParameter(name);
        
        Debug.log("Fetched value for '" + name + "' from request -> " + str);
        
        return str;
    }
    
    /**
     * Getter for session variable
     *
     * @return value
     */
    private String getValueFromSession() {
        
        String str = (String) pageContext.getSession().getAttribute(name);
        
        Debug.log("Fetched value for '" + name + "' from session -> " + str);
        
        return str;
    }
    
    /**
     * Setter for name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Setter for scope
     *
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
    
}