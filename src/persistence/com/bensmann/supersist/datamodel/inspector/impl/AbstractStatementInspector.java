/*
 * com/bensmann/supersist/datamodel/inspector/impl/AbstractStatementInspector.java
 *
 * AbstractStatementInspector.java created on 2. Februar 2007, 14:59 by rb
 *
 * Copyright (C) 2006-2007 Ralf Bensmann, java@bensmann.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA
 *
 */

package com.bensmann.supersist.datamodel.inspector.impl;

import com.bensmann.supersist.datamodel.inspector.ProcedureInspector;
import com.bensmann.supersist.datamodel.inspector.StatementInspector;
import com.bensmann.supersist.datamodel.inspector.TableInspector;
import com.bensmann.supersist.exception.DatamodelException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author rb
 * @version 1.0
 */
public abstract class AbstractStatementInspector
        extends BaseInspector
        implements StatementInspector {
    
    /**
     *
     */
    private TableInspector tableInspector;
    
    /**
     *
     */
    private ProcedureInspector procedureInspector;
    
    /**
     * Creates a new instance of AbstractStatementInspector
     */
    public AbstractStatementInspector(
            Connection connection,
            TableInspector tableInspector,
            ProcedureInspector procedureInspector) {
        
        super(connection);
        
        this.tableInspector = tableInspector;
        this.procedureInspector = procedureInspector;
        
    }
    
    /**
     * Creates a prepared statement for a select query against a certain
     * table or view
     *
     * @param name
     * @param columnNames
     * @param whereClause
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public PreparedStatement getPreparedSelectStatement(
            String name, String[] columnNames, String whereClause)
            throws DatamodelException {
        
        int i = 1;
        int length = 0;
        StringBuffer statement = new StringBuffer();
        PreparedStatement preparedStatement = null;
        
        // Retrieve column names for table/view
        if (columnNames == null) {
            columnNames = tableInspector.getTableColumnNames(name);
        }
        length = columnNames.length;
        
        // Create SELECT query
        statement.append("SELECT ");
        for (String s : columnNames) {
            
            // Check for null-value ...
            // (see selectIntoBean: checking for transient fields...)
            // TODO: method to purge null-values from array
            if (s != null) {
                
                statement.append(s);
                
                if (i++ < length) {
                    statement.append(", ");
                }
                
            }
            
        }
        
        // Append FROM-clause
        statement.append(" FROM ").append(name);
        
        // Purge ", FROM"
        i = statement.indexOf(",  FROM");
        if (i > 5) {
            statement.delete(i, i + 2);
        }
        
        // Append WHERE-clause
        if (whereClause != null) {
            statement.append(" WHERE ").append(whereClause);
        }
        
        try {
            preparedStatement =
                    connection.prepareStatement(statement.toString());
        } catch (SQLException e) {
            throw new DatamodelException("Cannot get prepared statement", e);
        }
        
        return preparedStatement;
        
    }
    
    /**
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public PreparedStatement getPreparedSelectStatement(String name)
    throws DatamodelException {
        
        return getPreparedSelectStatement(name, null, null);
        
    }
    
    /**
     * Creates a prepared statement for a select query against a certain
     * table or view. If parameter columnNames is null all columns will
     * be included in SELECT statment.
     *
     * @param name
     * @param columnNames
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public PreparedStatement getPreparedInsertStatement(
            String name, String[] columnNames)
            throws DatamodelException {
        
        int i = 1;
        int length = 0;
        StringBuffer statement = new StringBuffer();
        PreparedStatement preparedStatement = null;
        
        // Retrieve column names for table/view if no columnNames
        // were given
        if (columnNames == null) {
            columnNames = getColumnNames(name);
        }
        length = columnNames.length;
        
        // Create INSERT query
        statement.append("INSERT INTO ").append(name).append(" (");
        for (String s : columnNames) {
            
            statement.append(s);
            
            if (i++ < length) {
                statement.append(", ");
            }
            
        }
        statement.append(")");
        
        // Append placeholder for values
        statement.append(" VALUES (");
        for (i = 1; i < length; i++) {
            
            statement.append("?");
            
            if (i < length) {
                statement.append(", ");
            }
            
        }
        statement.append(")");
        
        try {
            preparedStatement =
                    connection.prepareStatement(statement.toString());
        } catch (SQLException e) {
            throw new DatamodelException("Cannot get prepared statement", e);
        }
        
        return preparedStatement;
        
    }
    
    /**
     *
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public PreparedStatement getPreparedInsertStatement(String name)
    throws DatamodelException {
        
        return getPreparedInsertStatement(name, null);
        
    }
    
    /**
     *
     *
     * @param name
     * @param columnNames
     * @param whereClause
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public PreparedStatement getPreparedUpdateStatement(String name,
            String[] columnNames, String whereClause)
            throws DatamodelException {
        
        int i = 1;
        int length = 0;
        StringBuffer statement = new StringBuffer();
        PreparedStatement preparedStatement = null;
        
        // Retrieve column names for table/view if no columnNames
        // were given
        if (columnNames == null) {
            columnNames = getColumnNames(name);
        }
        length = columnNames.length;
        
        // Create UPDATE query
        statement.append("UPDATE ").append(name).append(" SET ");
        for (String s : columnNames) {
            
            statement.append(s + " = ?");
            
            if (i++ < length) {
                statement.append(", ");
            }
            
        }
        
        // Append WHERE-clause
        if (whereClause != null)  {
            statement.append(whereClause);
        }
        
        try {
            
            preparedStatement =
                    connection.prepareStatement(statement.toString());
            
        } catch (SQLException e) {
            throw new DatamodelException("Cannot get prepared statement", e);
        }
        
        return preparedStatement;
        
    }
    
    /**
     *
     *
     * @param name
     * @param whereClause
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public PreparedStatement getPreparedUpdateStatement(String name,
            String whereClause)
            throws DatamodelException {
        
        return getPreparedUpdateStatement(name, null, whereClause);
        
    }
    
    /**
     *
     *
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public CallableStatement getCallableStatement(String packageName,
            String name)
            throws DatamodelException {
        
        StringBuffer statement = new StringBuffer();
        int argumentCount = 0;
        CallableStatement callableStatement = null;
        
        // Initialize statement
        statement.append("{call " + packageName + "." + name).append("(");
        // Append parameters
        argumentCount = procedureInspector.getArgumentNames(packageName, name).length;
        for (int i = 0; i < argumentCount; i++) {
            
            statement.append("?");
            
            if (i < argumentCount - 1) {
                statement.append(", ");
            }
            
        }
        // Close statement
        statement.append(")").append("}");
        
        try {
            callableStatement = connection.prepareCall(statement.toString());
        } catch (SQLException e) {
            throw new DatamodelException("Cannot prepare call for " +
                    "'" + name + "'", e);
        }
        
        System.out.println("callableStatement=" + statement.toString());
        return callableStatement;
        
    }
    
    /**
     * Database-specific
     *
     * @param callableStatement
     * @param argumentPosition
     * @param argumentDataType
     * @param argumentDataLength
     * @param argumentDataPrecision
     * @param argumentDataScale
     * @param value
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void setCallableStatementArgument(
            CallableStatement callableStatement, int argumentPosition,
            String argumentDataType, int argumentDataLength,
            int argumentDataPrecision, int argumentDataScale, Object value)
            throws DatamodelException;
    
}
