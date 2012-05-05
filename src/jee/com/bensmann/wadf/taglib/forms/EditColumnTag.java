/*
 * Created on 07.04.2005
 *
 */
package com.bensmann.wadf.taglib.forms;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.bensmann.wadf.taglib.core.BnmTagSupport;


/**
 * @author rb
 * @version $Id: EditColumnTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 * 
 * Generate <input>-tag for editing a SQL field
 * <p>
 * If a ID was given in enclosing tag EditFormTag we will show the value of that
 * name
 *  
 */
public class EditColumnTag extends BnmTagSupport {

    /**
     * Name of name to edit
     */
    private String name;

    /**
     * Type of edit: default is "text". Other possibilities are: "textarea"
     */
    private String type = "text";

    /**
     * Edit using a textarea: rows
     */
    private int rows;

    /**
     * Edit using a textarea: cols
     */
    private int cols;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        // Obtain enclosing tag object
        EditFormTag editFormTag = (EditFormTag) findAncestorWithClass(this,
                EditFormTag.class);

        // Register name with edit-form-tag
        editFormTag.registerColumn(name);

        // StringBuffer to create tag
        StringBuffer tag = new StringBuffer();

        // Begin tag; check for type of tag
        if (type.equalsIgnoreCase("text")) {
            tag.append("<input type=\"" + type + "\"");
        }
        // Append attributes for textarea
        else if (type.equalsIgnoreCase("textarea")) {
            tag.append("<textarea rows=\"" + rows + "\" cols=\"" + cols + "\"");
        }

        // Append name of field
        tag.append(" name=\"" + editFormTag.getTable() + "_" + name + "\"");
        
        // Type text: add attribute value
        if (type.equalsIgnoreCase("text")) {
            tag.append("value=\"");
        }

        // Avoid NullPointerException
        if (!editFormTag.isFirstTime()) {

            // Get value for column ID from enclosing tag
            String idColumnValue = editFormTag.getIdColumnValue();
            // Append value to tag
            if (idColumnValue != null && idColumnValue.length() > 0) {

                try {

                    // Apend value of column to tag
                    tag.append(editFormTag.getMyResult().getResultSet()
                            .getString(name));

                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }

        }

        // Begin tag; check for type of tag
        if (type.equalsIgnoreCase("text")) {
            tag.append("\">");
        }
        // Append attributes for textarea
        else if (type.equalsIgnoreCase("textarea")) {
            tag.append("</textarea>");
        }

        // Print tag
        try {
            out.print(tag.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;

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
     * Set name of colunm to edit
     * 
     * @param name
     */
    public void setName(String column) {
        this.name = column;
    }

    /**
     * Set type of edit field
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Textarea: set rows
     * 
     * @param rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Textarea: set cols
     * 
     * @param cols
     */
    public void setCols(int columns) {
        this.cols = columns;
    }

}