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
 * @version $Id: FormTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 */
public class FormTag extends BnmTagSupport {
    
    private String name;
    
    private String action = "sql.do";
    
    private String sqlTable;
    
    private String sqlProc;
    
    /**
     * Creates a new instance of SubmitTag
     */
    public FormTag() {
    }
    
    public int doStartTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        
        if (sqlTable != null) {
            // Store SQL table in session
            pageContext.getSession().setAttribute("sqltable", sqlTable);
        } else if (sqlProc != null) {
            // Store SQL procedure in session
            pageContext.getSession().setAttribute("sqltable", sqlProc);
        }
        
        try {
            
            out.print("<form method=\"post\" name=\"" + name +
                    "\" action=\"" + action + "\"");
//            if (sqlTable != null) {
//                out.print(" sqltable=\"" + sqlTable + "\"");
//            } else if (sqlProc != null) {
//                out.print(" sqlproc=\"" + sqlProc + "\"");
//            }
            out.print(">");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return EVAL_PAGE;
        
    }
    
    public int doEndTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        
        try {
            out.print("</form>");
        } catch (IOException e) {
        }
        
        return EVAL_PAGE;
        
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSqltable(String sqlTable) {
        this.sqlTable = sqlTable;
    }
    
    public void setSqlproc(String sqlProc) {
        this.sqlProc = sqlProc;
    }
    
}
