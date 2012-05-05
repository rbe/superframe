/*
 * IfRowNotEmpty.java
 *
 * Created on 16. Mai 2005, 17:46
 *
 */

package com.bensmann.wadf.taglib.sql;

import com.bensmann.wadf.taglib.core.BnmTagSupport;
import javax.servlet.jsp.JspException;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: IfNextRowIsNotEmptyTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class IfNextRowIsNotEmptyTag extends BnmTagSupport {
    
    /**
     * Creates a new instance of IfRowNotEmpty
     */
    public IfNextRowIsNotEmptyTag() {
    }

    public int doStartTag() throws JspException {
        
        // Obtain enclosing tag object
        ForEachRowTag forEachRowTag = (ForEachRowTag) findAncestorWithClass(
                this, ForEachRowTag.class);
        
        if (forEachRowTag.rowsIterator.hasNext()) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
        
    }

    public int doEndTag() throws javax.servlet.jsp.JspException {
        return EVAL_PAGE;
    }
    
}
