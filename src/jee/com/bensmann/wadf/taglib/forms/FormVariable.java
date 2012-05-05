/*
 * FormVariable.java
 *
 * Created on 16. Juni 2005, 20:06
 *
 */

package com.bensmann.wadf.taglib.forms;

/**
 *
 * @author Ralf Bensmann
 * @revision $Id: FormVariable.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class FormVariable {
    
    /**
     * Name of page/JSP this form variable is used with
     */
    private String pageName;
    
    /**
     * Name of variable
     */
    private String variableName;
    
    /**
     * Value of form variable
     */
    private String value;
    
    /**
     * Creates a new instance of FormVariable
     * @param pageName 
     * @param variableName 
     */
    public FormVariable(String pageName, String variableName) {
        this.pageName = pageName;
        this.variableName = variableName;
    }
    
    /**
     * 
     * @return 
     */
    public String getPageName() {
        return pageName;
    }
    
    /**
     * 
     * @return 
     */
    public String getVariableName() {
        return variableName;
    }
    
    /**
     * Unique identifier consists of page and variable name
     * @return 
     */
    public String getUniqueIdent() {
        return pageName + "-" + variableName;
    }
    
    /**
     * 
     * @return 
     */
    public String getValue() {
        return value;
    }
    
    /**
     * 
     * @param value 
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
