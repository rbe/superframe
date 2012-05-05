/*
 * SubmitTag.java
 *
 * Created on 9. Juni 2005, 23:05
 *
 */

package com.bensmann.wadf.taglib.forms;

import com.bensmann.wadf.taglib.core.BnmTagSupport;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: TextAreaTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class TextAreaTag extends BnmTagSupport {
    
    private String name;
    
    private int cols;
    
    private int rows;
    
    private String value;
    
    /**
     * Creates a new instance of SubmitTag
     */
    public TextAreaTag() {
    }
    
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }
    
    public int doEndTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        
        FormVariable formVariable = new FormVariable("page", name);
        if (value != null) {
            formVariable.setValue(value);
        }
        
        // Create form variable in session
        pageContext.getSession().setAttribute(formVariable.getUniqueIdent(),
                formVariable);
        
        try {
            
            out.print("<textarea name=\"" + name + "\" cols=\"" + cols +
                    "\" rows=\"" + rows + "\">");
            if (value != null) {
                out.print(value);
            }
            out.print("</textarea>");
            
        } catch (IOException e) {
        }
        
        return EVAL_PAGE;
        
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setCols(int cols) {
        this.cols = cols;
    }
    
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
