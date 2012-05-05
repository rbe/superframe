/*
 * Datei angelegt am 21.11.2003
 */
package com.bensmann.wadf.taglib.pages;

import com.bensmann.superframe.java.Debug;
import javax.servlet.jsp.JspException;
import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 * @author rb
 * @version $Id: UseBrickTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 * 
 * Used in layout brick page to set a placeholder to be filled by pages when
 * using this layout. This tag only needs a 'id' attribute to identify the
 * placeholder
 *  
 */
public class UseBrickTag extends BnmTagSupport {

    /**
     * ID of brick
     */
    private String name;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.base.BnmTagSupport#doStartTag()
     */
    public int doStartTag() {
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 
     */
    public int doEndTag() throws JspException {

        // Get template for brick
        String template = ((BrickSet) pageContext.getRequest().getAttribute(
                "brickset-template")).getTemplate(name);
        
        // Get JSP code for brick
        String includecode = ((BrickSet) pageContext.getRequest().getAttribute(
                "brickset-code")).getTemplate(name);

        try {

            if (template != null) {

                // Debug
                Debug.log("Using brick '" + name + "', including '" +
                        template + "'");

                // Include JSP template
                pageContext.include(template);

            }
            // Include JSP code
            else if (includecode != null) {

                Debug.log("Use brick '" + name + "': include code directly");

                // Use .print to prevent additional newline after including
                // content of brick
                pageContext.getOut().print(includecode);

            }
            else {

                // Include error message in JSP
                pageContext.getOut().println(
                        "[No template or code for brick '" + name + "' found]");

            }

        }
        catch (Exception e) {

            e.printStackTrace();
            throw new JspException(e.getCause());

        }

        return EVAL_PAGE;

    }

    /**
     * Set name of brick
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}