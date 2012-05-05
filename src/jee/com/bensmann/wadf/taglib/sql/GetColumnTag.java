/*
 * Created on 06.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import com.bensmann.superframe.persistence.jdbc.ColumnDescription;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * Get a field from actual row of a result set
 *  
 * @author rb
 * @version $Id: GetColumnTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class GetColumnTag extends BnmBodyTagSupport {

    /**
     * Name of field
     */
    private String name;

    /**
     * Format for field (used with number and date fields)
     */
    private String format;

    private NumberFormat nf = NumberFormat.getInstance();

    private boolean grouping;

    private int minPrecision;

    private int maxPrecision;

    private int precision;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        // Value of column
        Object value = null;

        // Obtain enclosing tag object
        ForEachRowTag forEachRowTag = (ForEachRowTag) findAncestorWithClass(
                this, ForEachRowTag.class);

        // Get value for column of actual row
        ColumnDescription columnDescription = (ColumnDescription) forEachRowTag
                .getActualRow().get(name.toUpperCase());

        if (columnDescription != null) {

            // Retrieve column type and value
            String columnType = columnDescription.getColumnType().toUpperCase();
            int columnPrecision = columnDescription.getColumnPrecision();
            int columnScale = columnDescription.getColumnScale();
            value = columnDescription.getColumnValue();

            // Debug
            Debug.log("Got value '" + value + "' for column '" + name + "'" +
                    " of type '" + columnType + "'");

            // Colunm is null/empty so return an empty string or zero, depending
            // on column type
            if (value == null) {

                if (columnType.indexOf("CHAR") >= 0
                        || columnType.indexOf("LOB") >= 0) {

                    value = "";

                }
                else if (columnType.indexOf("NUMBER") >= 0) {

                    // Oracle:
                    // NUMBER -> P=0 S=0
                    // NUMBER -> P=0 S=-127
                    // NUMBER(10) -> P=10, S=0
                    // FLOAT -> P=126, S=-127
                    if (columnPrecision == 0 && columnScale == 0) {
                        value = "0";
                    }
                    else if (columnPrecision == 0 && columnScale == -127) {
                        value = "0";
                    }
                    else if (columnPrecision > 0 && columnScale == 0) {
                        value = "0";
                    }
                    else if (columnPrecision == 126 && columnScale == -127) {
                        value = "0.0";
                    }

                }

            }

            // Column is now no longer empty/null, format it. Exception: DATE.
            // We handle IllegalArgumentException from SimpleDateFormat in that
            // case

            // Ensure that grouping flag reflects our needs (as set by attribute
            // grouping). If column ID is displayed we never want grouping!
            if (name.equalsIgnoreCase("ID")) {
                nf.setGroupingUsed(false);
            }
            else {
                nf.setGroupingUsed(grouping);
            }

            // Column is a DATE; format it using SimpleDateFormat
            // TODO: TIMESTAMP is returned as 'yyyy-M-d.HH .mm .ss' ?!?!?!
            // Use CAST(xxx AS DATE) with Oracle
            if (columnType.indexOf("DATE") >= 0 ||
                    columnType.indexOf("TIMESTAMP") >= 0) {

                try {
                    value = new SimpleDateFormat(format).format(value);
                }
                catch (IllegalArgumentException e) {
                    value = "[unknown date]";
                }

            }
            // Format number
            else if (columnType.indexOf("NUMBER") >= 0) {

                Debug.log("Formatting value '" + value + "/" +
                        value.getClass() + "' as number " +
                        "minimumPrecision=" + minPrecision +
                        " maximumPrecision=" + maxPrecision +
                        " precision=" + precision + ", " +
                        (nf.isGroupingUsed() ? "" : "not ") +
                        "using grouping");

                value = nf.format(Double.valueOf((String) value));

            }

        }
        // ColumnDescription not found; forgot to SELECT that column
        else {
            value = "[Column missing]";
        }

        // Print out value
        try {
            pageContext.getOut().write((String) value);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set format for value of column
     * 
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    public void setGrouping(boolean grouping) {
        this.grouping = grouping;
        nf.setGroupingUsed(grouping);
    }

    /**
     * 
     * @param minPrecision
     */
    public void setMinPrecision(int minPrecision) {
        this.minPrecision = minPrecision;
        nf.setMinimumFractionDigits(this.minPrecision);
    }

    /**
     * 
     * @param minPrecision
     */
    public void setMaxPrecision(int maxPrecision) {
        this.maxPrecision = maxPrecision;
        nf.setMinimumFractionDigits(this.maxPrecision);
    }

    /**
     * 
     * @param minPrecision
     */
    public void setPrecision(int precision) {
        this.precision = precision;
        setMinPrecision(precision);
        setMaxPrecision(precision);
    }

}