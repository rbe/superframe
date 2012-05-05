/*
 * Created on 10.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import com.bensmann.superframe.persistence.jdbc.StoredProcTypeInfo;
import com.bensmann.wadf.taglib.core.BnmTagSupport;
import com.bensmann.wadf.taglib.forms.EditFormTag;

/**
 * @author rb
 * @version $Id: ProcParamTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 *
 * Store parameters and values for calling stored procedures in session
 */
public class ProcParamTag extends BnmTagSupport {
    
    /**
     * Name of parameter
     */
    private String parameter;
    
    /**
     * Position of parameter in stored procedure
     */
    private int position;
    
    /**
     *
     */
    private String type;
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        
        HttpSession session = pageContext.getSession();
        
        // Obtain enclosing EditFormTag object
        EditFormTag editFormTag = (EditFormTag) findAncestorWithClass(this,
                EditFormTag.class);
        
        String parameterCountPrefix = "procparamcounter-"
                + editFormTag.getProc();
        
        String parameterPrefix = "procparam-" + editFormTag.getProc();
        
        // Retrieve value for parameter; a) from request b) from session
        String v;
        v = pageContext.getRequest().getParameter(parameter);
        if (v == null) {
            v = (String) session.getAttribute(parameter);
        }
        
        // Debug
        Debug.log("Storing StoredProcTypeInfo for '" + parameterPrefix + "-"
                + position + "' in session");
        
        // Store position and value in session
        session.setAttribute(parameterPrefix + "-" + position,
                new StoredProcTypeInfo(position, type, v));
        
        // Store parameter counter in session.
        // Look for parameter count in session; my be null and check if position
        // is higher than information in session. So it doesn't matter when
        // ProcParamTags are not in ascending order
        Integer i = (Integer) session.getAttribute(parameterCountPrefix);
        if (i == null) {
            session.setAttribute(parameterCountPrefix, new Integer(position));
        } else if (i.intValue() < position) {
            session.setAttribute(parameterCountPrefix, new Integer(position));
        }
        
        // Debug
        Debug.log("Set parameter count for stored procedure '" +
                editFormTag.getProc() + "' in session to '" + position + "'");
        
        return 0;
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
    
    /**
     * Set position for parameter for calling stored procedure
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * Set name of parameter which value should be used at position 'position'
     *
     * @param parameter
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    
    /**
     * Set type of parameter
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
    
}