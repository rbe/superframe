/*
 * DatamodelInspector.java
 *
 * Created on 17. Januar 2007, 20:17
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

import com.bensmann.supersist.datamodel.transfer.DatamodelTransferObject;
import com.bensmann.supersist.exception.DatamodelException;
import java.sql.Connection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author rb
 */
public interface DatamodelInspector {
    
    /**
     * Gather datamodel again. A new XML document is created and so
     * the datamodel will be read by getDocument()
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    void refreshDatamodel() throws DatamodelException;
    
    /**
     * Generated DatamodelBeans for every table
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    void generateDatamodelTransferObjects() throws DatamodelException;
    
    /**
     *
     * @param name
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    DatamodelTransferObject getDatamodelTransferObject(String name)
    throws DatamodelException;
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    DatamodelTransferObject[] getAllDatamodelTransferObjects()
    throws DatamodelException;
    
    /**
     *
     * @param datamodelTransferObject
     * @param tableOrView
     * @param whereClause
     * @return
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    DatamodelTransferObject[] selectIntoTransferObject(
            String tableOrView,
            DatamodelTransferObject datamodelTransferObject,
            String whereClause)
            throws DatamodelException;
    
    /**
     * Retrieves all neccessary data from a Java bean and executes a stored
     * procedure.
     *
     * @param bean
     * @param packageName
     * @param procedureName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    void applyTransferObjectToProcedure(
            Object datamodelTransferObject,
            String packageName, String procedureName)
            throws DatamodelException;
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    void inspectProcedures() throws DatamodelException;
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    void inspectFunctions() throws DatamodelException;
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    void inspectTables() throws DatamodelException;
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    void inspectViews() throws DatamodelException;
    
    /**
     *
     *
     * @param connection
     */
    void setConnection(Connection connection);
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    String[] getProcedureAndFunctionNames() throws DatamodelException;
    
    /**
     *
     * @return
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    String[] getTableAndViewNames() throws DatamodelException;
    
}
