/*
 * Created on 09.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import com.bensmann.superframe.persistence.jdbc.SingleJdbcConnection;
import com.bensmann.wadf.taglib.core.BnmTagSupport;

/**
 *  
 * @author rb
 * @version $Id: GetSelectListTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class GetSelectListTag extends BnmTagSupport {

    /**
     * Name for select-tag
     */
    private String name;

    /**
     * SQL table to query
     */
    private String sqltable;

    /**
     * Column of SQL table that is used as option value
     */
    private String optionvalue;

    /**
     * Text that is displayed by option
     */
    private String optiontext;

    /*
     * (non-Javadoc)
     * 
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        // Get session
        HttpSession session = pageContext.getSession();

        // Generate tag
        StringBuffer tag = new StringBuffer("<select name=\"" + name + "\">");

        // Get JDBC connection
        SingleJdbcConnection sjc = (SingleJdbcConnection) session
                .getAttribute("mainjdbc");

        // Generate ident
        String ident = "prepstmt-SELECT-" + sqltable + "-" + name;

        // Look for prepared statement in session
        PreparedStatement p = (PreparedStatement) session.getAttribute(ident);
        // If it does not exist, create new prepared statement and store it in
        // session
        if (p == null) {

            String sqlQuery = "SELECT " + optionvalue + "," + optiontext
                    + " FROM " + sqltable;

            // Debug
            Debug.log("Creating new prepared SELECT statement: " + sqlQuery);

            try {

                // Create new prepared statement
                p = sjc.getPreparedStatement(sqlQuery);

                // Store it in session
                session.setAttribute(ident, p);

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else {

            // Debug
            Debug.log("Re-using prepared statement from sessions");

        }

        try {

            // Execute query
            ResultSet r = p.executeQuery();

            // Append every option to tag
            while (r.next()) {

                String v = r.getString(optionvalue.toUpperCase());
                String t = r.getString(optiontext.toUpperCase());

                // Debug
                Debug.log("Adding option: value='" + v + "' with text '" +
                        t + "'");

                tag.append("<option value=\"" + v + "\">" + t + "</option>");

            }

            // Close result set
            r.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // Close tag
        tag.append("</select>");

        // Debug
        Debug.log("Generated select-tag: " + tag.toString());

        // Print tag
        try {
            pageContext.getOut().print(tag.toString());
        }
        catch (IOException e1) {
            e1.printStackTrace();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSqltable(String sqltable) {
        this.sqltable = sqltable;
    }

    public void setOptionvalue(String optionvalue) {
        this.optionvalue = optionvalue;
    }

    public void setOptiontext(String optiontext) {
        this.optiontext = optiontext;
    }

}