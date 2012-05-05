/*
 * NextRowTag.java
 *
 * Created on 16. Mai 2005, 17:42
 *
 */

package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import com.bensmann.wadf.taglib.core.BnmTagSupport;
import javax.servlet.jsp.JspException;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: NextRowTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class NextRowTag extends BnmTagSupport {
    
    /**
     * Creates a new instance of NextRowTag
     */
    public NextRowTag() {
    }

    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException {
        
        // Obtain enclosing tag object
        ForEachRowTag forEachRowTag = (ForEachRowTag) findAncestorWithClass(
                this, ForEachRowTag.class);
        
        // Go to next row
        forEachRowTag.nextRow();
        
        return EVAL_PAGE;
        
    }
    
}
