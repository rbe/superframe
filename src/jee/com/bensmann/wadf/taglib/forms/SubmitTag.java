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
 * @version $Id: SubmitTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class SubmitTag extends BnmTagSupport {
    
    private String name;
    
    private String text = "Submit";
    
    private String successPage;
    
    private String errorPage;
    
    /**
     * Creates a new instance of SubmitTag
     */
    public SubmitTag() {
    }
    
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }
    
    public int doEndTag() throws JspException {
        
        // Set successpage in session
        pageContext.getSession().setAttribute("successpage", successPage);
        
        JspWriter out = pageContext.getOut();
        
        try {
            
            out.print("<input type=\"submit\" name=\"" + name +
                    "\" value=\"" + text + "\" successpage=\"" +
                    successPage + "\">");
            
        } catch (IOException e) {
        }
        
        return EVAL_PAGE;
        
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setSuccesspage(String successPage) {
        this.successPage = successPage;
    }
    
    public void setErrorpage(String errorPage) {
        this.errorPage = errorPage;
    }
    
}
