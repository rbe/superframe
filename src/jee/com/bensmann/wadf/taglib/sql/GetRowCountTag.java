package com.bensmann.wadf.taglib.sql;

import javax.servlet.jsp.JspException;

import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 * @author rb
 * @version $Id
 * 
 * Get row count of actual result set
 *  
 */
public class GetRowCountTag extends BnmTagSupport {

    /**
     * 
     * @return int
     */
    public int doStartTag() {

        ExecuteQueryTag executeQueryTag = (ExecuteQueryTag) findAncestorWithClass(
                this, ExecuteQueryTag.class);

        try {

            int rowcount = executeQueryTag.getNumberOfRows();
            pageContext.getOut().write(String.valueOf(rowcount));

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return EVAL_BODY_INCLUDE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}