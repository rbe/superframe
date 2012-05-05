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
 * @version $Id: FileTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class FileTag extends BnmTagSupport {
    
    private String name;
    
    /**
     * Creates a new instance of SubmitTag
     */
    public FileTag() {
    }
    
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }
    
    public int doEndTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        
        try {
            out.print("<input type=\"file\" name=\"" + name + "\">");
        } catch (IOException e) {
        }
        
        return EVAL_PAGE;
        
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
