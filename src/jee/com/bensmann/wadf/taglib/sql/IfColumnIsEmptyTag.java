/*
 * Created on 09.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import com.bensmann.superframe.persistence.jdbc.ColumnDescription;
import javax.servlet.jsp.JspException;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * @author rb
 * @version $Id: IfColumnIsEmptyTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 *  
 */
public class IfColumnIsEmptyTag extends BnmBodyTagSupport {

    /**
     * Name of column to check
     */
    private String name;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        // Obtain enclosing ForEachRowTag
        ForEachRowTag forEachRowTag = (ForEachRowTag) findAncestorWithClass(
                this, ForEachRowTag.class);

        // Get actual row from ForEachRowTag, get actual row and value of column
        ColumnDescription cd = forEachRowTag.getActualRow().get(
                name.toUpperCase());
        
        if (cd != null && cd.getColumnValue() == null) {
            
            // Debug
            Debug.log("Column '" + name + "' is empty ('" +
                    cd.getColumnValue() + "'), including body of tag");
            
            return EVAL_BODY_INCLUDE;
            
        } else {
            
            // Debug
            Debug.log("Column '" + name + "' is not empty ('" +
                    cd.getColumnValue() + "'), skipping body of tag");
            
            return SKIP_BODY;
            
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * Set name of column to check
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}