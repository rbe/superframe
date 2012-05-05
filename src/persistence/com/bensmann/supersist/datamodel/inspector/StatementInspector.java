/*
 * com/bensmann/supersist/datamodel/StatementInspector.java
 *
 * StatementInspector.java created on 2. Februar 2007, 11:40 by rb
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

package com.bensmann.supersist.datamodel.inspector;

import com.bensmann.supersist.exception.DatamodelException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;

/**
 *
 * @author rb
 */
public interface StatementInspector {
    
    /**
     *
     *
     *
     * @param packageName
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    CallableStatement getCallableStatement(String packageName, String name)
    throws DatamodelException;
    
    /**
     *
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    PreparedStatement getPreparedInsertStatement(String name)
    throws DatamodelException;
    
    /**
     * Creates a prepared statement for a select query against a certain
     * table or view. If parameter columnNames is null all columns will
     * be included in SELECT statment.
     *
     * @param name
     * @param columnNames
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    PreparedStatement getPreparedInsertStatement(
            String name, String[] columnNames)
            throws DatamodelException;
    
    /**
     *
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    PreparedStatement getPreparedSelectStatement(String name)
    throws DatamodelException;
    
    /**
     * Creates a prepared statement for a select query against a certain
     * table or view
     *
     * @param name
     * @param columnNames
     * @param whereClause
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    PreparedStatement getPreparedSelectStatement(
            String name, String[] columnNames, String whereClause)
            throws DatamodelException;
    
    /**
     *
     *
     *
     * @param name
     * @param whereClause
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    PreparedStatement getPreparedUpdateStatement(
            String name, String whereClause)
            throws DatamodelException;
    
    /**
     *
     *
     *
     * @param name
     * @param columnNames
     * @param whereClause
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    PreparedStatement getPreparedUpdateStatement(
            String name, String[] columnNames, String whereClause)
            throws DatamodelException;
    
    /**
     *
     *
     * @param callableStatement
     * @param argumentPosition
     * @param argumentDataType
     * @param argumentDataLength
     * @param argumentDataPrecision
     * @param argumentDataScale
     * @param value
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    void setCallableStatementArgument(
            CallableStatement callableStatement, int argumentPosition,
            String argumentDataType, int argumentDataLength,
            int argumentDataPrecision, int argumentDataScale, Object value)
            throws DatamodelException;
    
}
