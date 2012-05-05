package com.bensmann.wadf.taglib.sql;

import javax.servlet.jsp.JspException;

import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 * @author rb
 * @version $Id
 * 
 * Das Alternate Tag gibt entweder eine 1 oder eine 2 aus. Dies kann für
 * Listenanzeigen benutzt werden, um alternierend verschiedene Stylesheetangaben
 * aufzurufen, um die Zeilen abwechselnd in verschiedenen Stilem
 * (Hintergrundfarben) zu verwenden.
 *  
 */
public class AlternateTag extends BnmTagSupport {

    /**
     * 
     * @return int
     */
    public int doStartTag() {

        ForEachRowTag forEachRowTag = (ForEachRowTag) findAncestorWithClass(
                this, ForEachRowTag.class);

        try {

            int alternate = 2 - (forEachRowTag.getPosition() % 2);
            pageContext.getOut().write(String.valueOf(alternate));

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