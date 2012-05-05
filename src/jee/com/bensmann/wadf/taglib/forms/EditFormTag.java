/*
 * Created on 07.04.2005
 *
 */
package com.bensmann.wadf.taglib.forms;

import com.bensmann.superframe.java.Debug;
import com.bensmann.superframe.persistence.jdbc.JdbcConnectionManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import com.bensmann.superframe.persistence.jdbc.MyResult;
import com.bensmann.superframe.persistence.jdbc.SingleJdbcConnection;
import com.bensmann.superframe.persistence.jdbc.SqlQuery;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;

/**
 * @author rb
 * @version $Id: EditFormTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *
 * Open a HTML form for editing SQL tables
 *
 * TODO: Move to package ...forms
 *
 */
public class EditFormTag extends BnmBodyTagSupport {
    
    private boolean firstTime = true;
    
    /**
     * Name of table that is edited
     */
    private String table;
    
    /**
     * Value of ID colunm for UPDATEs
     */
    private String idColumnValue;
    
    /**
     * Columns from edit-column tags
     */
    private Vector<String> columns;
    
    /**
     * MyResult for retrieving all values for form
     */
    private MyResult myResult;
    
    /**
     * Name of stored procedure
     */
    private String proc;
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        
        columns = new Vector<String>();
        
        // Get session
        HttpSession session = pageContext.getSession();
        
        if (table != null) {
            
            // Debug
            Debug.log("Storing name of SQL table in session: '" + table + "'");
            
            // Store SQL table in session
            session.setAttribute("sqltable", table);
            
        }
        
        if (proc != null) {
            
            // Debug
            Debug.log("Storing name of stored procedure in session: '" + proc +
                    "'");
            
            // Store name of stored procedure in session
            session.setAttribute("sqlproc", proc);
            
        }
        
        // Retrieve ID column name
        idColumnValue = pageContext.getRequest().getParameter(table + "_id");
        // Store ID in session
        session.setAttribute(table + "_id", idColumnValue);
        
        // Write <form>-tag and hidden input parameters
        try {
            
            // Start form
            out.write("<form method=\"post\" action=\"sql.do\">");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return EVAL_BODY_BUFFERED;
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
     */
    public int doAfterBody() throws JspException {
        
        if (firstTime) {
            
            // All edit-column tags have registered themselves. Now query
            // database for values
            if (table != null) {
                
                // Generate SELECT statement
                StringBuffer sb = new StringBuffer("SELECT ");
                Iterator i = columns.iterator();
                while (i.hasNext()) {
                    
                    String c = (String) i.next();
                    
                    sb.append(c);
                    if (i.hasNext()) {
                        sb.append(", ");
                    }
                    
                }
                
                // Append FROM ... WHERE clause
                sb.append(" FROM " + table + " WHERE id = " + idColumnValue);
                
                // Debug
                Debug.log("Generated SELECT statement: " + sb.toString());
                
                try {
                    
                    // Fetch JDBC connection to execute generated query
                    SingleJdbcConnection sjc =
                            JdbcConnectionManager.getInstance().getConnection(
                            pageContext.getSession().getId() + "_oracle");
                    
                    // Create SqlQuery object for generated query
                    SqlQuery sqlQuery = new SqlQuery(sjc, "temp",
                            sb.toString());
                    
                    // Execute query
                    myResult = sjc.executeQuery(sb.toString());
                    myResult.getResultSet().next();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
            }
            
            getBodyContent().clearBody();
            firstTime = false;
            
            return EVAL_BODY_BUFFERED;
            
        } else {
            return EVAL_BODY_INCLUDE;
        }
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        
        try {
            
            getBodyContent().getEnclosingWriter().write(
                    getBodyContent().getString() + "\n</form>\n");
            
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        // Close myResult
        if (!firstTime) {
            
            try {
                
                myResult.close();
                myResult = null;
                
            } catch (NullPointerException e) {
                // Stored procedure: myResult is null
            }
            
        }
        
        firstTime = true;
        
        return EVAL_PAGE;
        
    }
    
    /**
     * Set table that should be edited
     *
     * @param table
     */
    public void setTable(String table) {
        this.table = table;
    }
    
    /**
     * Get table name
     *
     * @return
     */
    public String getTable() {
        return table;
    }
    
    /**
     * Set stored procedure that should be called
     *
     * @param proc
     */
    public void setProc(String proc) {
        this.proc = proc;
    }
    
    /**
     * Return name of stored procedure that should be called
     *
     * @return
     */
    public String getProc() {
        return proc;
    }
    
    /**
     * Get column name for ID
     *
     * @return
     */
    public String getIdColumnValue() {
        return idColumnValue;
    }
    
    /**
     * Every edit-column-tag has to register its column here
     *
     * @param column
     */
    public void registerColumn(String column) {
        
        // Debug
        Debug.log("Registering column '" + column + "'");
        
        columns.add(column);
        
    }
    
    public MyResult getMyResult() {
        return myResult;
    }
    
    public boolean isFirstTime() {
        return firstTime;
    }
    
}