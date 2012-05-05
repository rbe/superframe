/*
 * Created on 06.04.2005
 *
 */
package com.bensmann.wadf.taglib.sql;

import com.bensmann.superframe.java.Debug;
import com.bensmann.superframe.persistence.jdbc.ColumnDescription;
import com.bensmann.superframe.persistence.jdbc.MyResult;
import com.bensmann.superframe.persistence.jdbc.SqlQuery;
import com.bensmann.wadf.Configuration;
import com.bensmann.wadf.taglib.core.BnmBodyTagSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

/**
 *
 *
 * @author rb
 * @version $Id: ExecuteQueryTag.java,v 1.1 2005/07/19 12:03:52 rb Exp $
 */
public class ExecuteQueryTag extends BnmBodyTagSupport {
    
    /**
     * Ident of SQL query (set via set-sql-query tag)
     */
    private String name;
    
    /**
     *
     */
    private MyResult myResult;
    
    /**
     * All rows of result set stored as a hashmap: key=field name
     */
    private Vector<Map<String, ColumnDescription>> resultRows;

    /**
     *
     */
    private int numberOfColumns;
    
    /**
     *
     */
    private int numberOfRows;
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {

        // Get session
        HttpSession httpSession = pageContext.getSession();
        
        // Get SQL query
        SqlQuery sqlQuery = Configuration.getInstance().getSqlQuery(name);
        if (sqlQuery == null) {
            
            // Debug
            Debug.log("SQL query '" + name  + "' does not exist!");
            
        } else {
            
            try {
                
                // Debug
                Debug.log("Executing SQL-Query: " + sqlQuery.getQuery());
                
                // Get prepared statement
                PreparedStatement preparedStatement = sqlQuery.
                        getPreparedStatement();
                
                // Try to fill parameters of statement with values from session
                if (sqlQuery.hasPreparedStatementParameters()) {
                    
                    for (int i = 1; i < sqlQuery.
                            getPreparedStatementParameterCount() + 1; i++) {
                        
                        sqlQuery.setPreparedStatementParameter(
                                i, (String) httpSession.getAttribute(sqlQuery.
                                getPreparedStatementParameterAtPosition(i)));
                        
                    }
                    
                }
                
                // Execute query
                // TODO: Handle NullPointerException when database can not be
                // queried...
                myResult = sqlQuery.executeQuery();
                ResultSet rs = myResult.getResultSet();
                
                // Get meta data of result set
                ResultSetMetaData rsmd = rs.getMetaData();
                numberOfColumns = rsmd.getColumnCount();
                
                // Debug
                Debug.log("Found " + numberOfColumns + " colunms per row");
                
                // fieldsAndValues is a Map that holds a key=column name and a
                // ColumnDescription as value
                Map<String, ColumnDescription> fieldsAndValues = null;
                ColumnDescription columnDescription = null;
                
                // Initialize resultRows (prevents result sets are added)s
                resultRows = new Vector<Map<String, ColumnDescription>>();
                
                // Convert result set into set of rows (vector) that holds a
                // ColumnDescription
                while (rs.next()) {
                    
                    numberOfRows++;
                    
                    fieldsAndValues = Collections.synchronizedMap(
                            new HashMap<String, ColumnDescription>());
                    
                    for (int i = 1; i < numberOfColumns + 1; i++) {
                        
                        // Retrieve all neccessary information about the column
                        // All columns are treated as upper case
                        String columnName = rsmd.getColumnName(i).toUpperCase();
                        String columnType = rsmd.getColumnTypeName(i);
                        int columnPrecision = 0;
                        int columnScale = 0;
                        // BUG? OracleDriver throws NumberFormatException when
                        // calling .getPrecision for a LOB column
                        try {
                            columnPrecision = rsmd.getPrecision(i);
                            columnScale = rsmd.getScale(i);
                        } catch (NumberFormatException e) {
                            
                            // Debug
                            Debug.log("Cannot get precision/scale for" +
                                    " column 'name[" + columnName + "] type[" +
                                    columnType + "]'");
                            
                        }
                        
                        // Create column object
                        columnDescription = new ColumnDescription(columnName,
                                columnType, null, columnPrecision, columnScale);
                        
                        Object columnValue;
                        
                        // Column is a DATE. Get value as timestamp and store
                        // information in calendar object (got from
                        // ColumnDescription)
                        if (columnType.equalsIgnoreCase("DATE")) {
                            
                            columnValue = rs.getTimestamp(i,
                                    columnDescription.getCalendar());
                            
                        } else {
                            columnValue = rs.getString(i);
                        }
                        
                        columnDescription.setColumnValue(columnValue);
                        
                        // Debug
                        Debug.log("Adding column 'name[" + columnName +
                                "] type[" + columnType +
                                "] precision[" + columnPrecision +
                                "] scale[" + columnScale + "]' -> '" +
                                columnValue + "' to result row hashmap");
                        
                        // Add column description to HashMap that describes
                        // every
                        // column in a row
                        fieldsAndValues.put(columnName, columnDescription);
                        
                    }
                    
                    resultRows.add(fieldsAndValues);
                    
                }
                
            } catch (SQLException e) {
                
                throw new JspException(
                        "SQLException: Error querying database: "
                        + e.getMessage());
                
            } catch (ClassNotFoundException e) {
                
                throw new JspException(
                        "SQLException: Could not load JDBC driver: "
                        + e.getMessage());
                
            } catch (NullPointerException e) {
                
                e.printStackTrace();
                
                throw new JspException(
                        "Database connection could not be established!");
                
            }
            
        }
        
        // Debug
        Debug.log("Processed " + numberOfRows + " rows from result set");
        
        return EVAL_BODY_INCLUDE;
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see com.bensmann.wadf.taglib.core.BnmBodyTagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        
        // Close ResultSet and Statement to free up resources
        myResult.close();
        
        myResult = null;
        resultRows = null;
        numberOfColumns = 0;
        numberOfRows = 0;
        
        return EVAL_PAGE;
        
    }
    
    /**
     * Set name of SQL query
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Return MyResult object. Used by GetRowTag.
     *
     * @return
     */
    public MyResult getMyResult() {
        return myResult;
    }
    
    /**
     * Return result set
     *
     * @return
     */
    public Iterator<Map<String, ColumnDescription>> getResultRowsIterator() {
        return resultRows.iterator();
    }
    
    /**
     *
     * @return
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }
    
    /**
     *
     * @return
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }
    
}