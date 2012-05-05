/*
 * Created on 06.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import com.bensmann.superframe.persistence.jdbc.ColumnDescription;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * @author rb
 * @version $Id: ForEachRowTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 *
 */
public class ForEachRowTag extends BnmBodyTagSupport {
    
    /**
     * MyResult; object is created by ExecuteTag
     */
    protected Iterator<Map<String, ColumnDescription>> rowsIterator;
    
    /**
     * Position within result rows
     */
    private int position;
    
    /**
     * Actual row in result (that we are processing)
     */
    private Map<String, ColumnDescription> actualRow;
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        
        // Obtain enclosing tag object
        ExecuteQueryTag executeTag = (ExecuteQueryTag) findAncestorWithClass(
                this, ExecuteQueryTag.class);
        
        // Retrieve iterator over result rows
        rowsIterator = executeTag.getResultRowsIterator();
        
        // Debug
        Debug.log("About to process " + executeTag.getNumberOfRows() + " rows");
        
        // If there are rows left, fetch row, increase position counter and
        // re-evaluate body
        if (rowsIterator.hasNext()) {
            
            actualRow = Collections.synchronizedMap(
                    (Map<String, ColumnDescription>) rowsIterator.next());
            position++;
            
            // Debug
            Debug.log("Processing row #" + position);
            
            return EVAL_BODY_INCLUDE;
            
        } else {
            
            // Debug
            Debug.log("No more rows left");
            
            return SKIP_BODY;
            
        }
        
    }
    
    /**
     *
     * @return
     */
    public int doAfterBody() {
        
        if (rowsIterator.hasNext()) {
            
            nextRow();
            
            // Debug
            Debug.log("Processing row #" + position);
            
            return EVAL_BODY_AGAIN;
            
        } else {
            
            // Debug
            Debug.log("No more rows left");
            
            position = 0;
            
            return SKIP_BODY;
            
        }
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        
        try {
            
            if (getBodyContent() != null && getPreviousOut() != null) {
                getPreviousOut().write(getBodyContent().getString());
            }
            
        } catch (IOException e) {
            throw new JspTagException(e.toString());
        }
        
        return EVAL_PAGE;
        
    }
    
    /**
     *
     */
    public void nextRow() {
        
        if (rowsIterator.hasNext()) {
            
            actualRow = Collections.synchronizedMap(
                    (Map<String, ColumnDescription>) rowsIterator.next());
            
            position++;
            
        }
        
    }
    
    /**
     * Return actual row that we are processing
     *
     * @return
     */
    public Map<String, ColumnDescription> getActualRow() {
        return actualRow;
    }
    
    /**
     * Return actual position/number of row in result set
     *
     * @return
     */
    public int getPosition() {
        return position;
    }
    
}