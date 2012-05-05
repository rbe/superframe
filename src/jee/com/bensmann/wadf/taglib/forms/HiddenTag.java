/*
 * SubmitTag.java
 *
 * Created on 9. Juni 2005, 23:05
 *
 */

package com.bensmann.wadf.taglib.forms;
import com.bensmann.superframe.java.Debug;
import com.bensmann.wadf.taglib.core.BnmTagSupport;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: HiddenTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class HiddenTag extends BnmTagSupport {
    
    private String name;
    
    private String var;
    
    /**
     * Creates a new instance of SubmitTag
     */
    public HiddenTag() {
    }
    
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }
    
    public int doEndTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        String value = null;
        
        // Retrieve value from request or from session
        value = pageContext.getRequest().getParameter(var);
        if (value == null) {
            value = (String) pageContext.getSession().getAttribute(var);
            Debug.log("Got value " + value + " from session");
        } else {
            Debug.log("Got value " + value + " from request");
        }
        // We must have a value!
        if (value == null) {
            throw new JspException("HiddenTag: got no value for " + var);
        }
        
        FormVariable formVariable = new FormVariable("page", name);
        formVariable.setValue(value);
        
        // Create form variable in session
        pageContext.getSession().setAttribute(formVariable.getUniqueIdent(),
                formVariable);
        
        try {
            
            // Print HTML
            out.print("<input type=\"hidden\" name=\"" + name + "\" value=\"" +
                    value + "\">");
            
        } catch (IOException e) {
        }
        
        return EVAL_PAGE;
        
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
}
