/*
 * com/bensmann/supersist/datamodel/inspector/impl/AbstractDatamodelInspector.java
 *
 * AbstractDatamodelInspector.java created on 2. Februar 2007, 12:54 by rb
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

import com.bensmann.supersist.datamodel.inspector.DatamodelInspector;
import com.bensmann.supersist.datamodel.inspector.InspectorDocument;
import com.bensmann.supersist.datamodel.transfer.DatamodelTransferObject;
import com.bensmann.supersist.exception.DatamodelException;
import org.w3c.dom.Element;

/**
 *
 * @author rb
 * @version 1.0
 */
public abstract class AbstractDatamodelInspector
        extends BaseInspector
        implements DatamodelInspector {
    
    /**
     * Creates a new instance of AbstractDatamodelInspector
     */
    public AbstractDatamodelInspector() {
    }
    
    /**
     * Database-specific
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void generateDatamodelTransferObjects()
    throws DatamodelException;
    
    /**
     * Database-specific
     *
     * @param bean
     * @param packageName
     * @param procedureName
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void applyTransferObjectToProcedure(
            Object datamodelTransferObject,
            String packageName, String procedureName)
            throws DatamodelException;
    
    /**
     * Database-specific
     *
     * @param tableOrView
     * @param datamodelBean
     * @param whereClause
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    public abstract DatamodelTransferObject[] selectIntoTransferObject(
            String tableOrView,
            DatamodelTransferObject datamodelTransferObject,
            String whereClause)
            throws DatamodelException;
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void inspectProcedures() throws DatamodelException;
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void inspectFunctions() throws DatamodelException;
    
    /**
     * Database-specific method to get information about all procedures,
     * functions and - if supported - packages in the schema
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public void inspectProceduresAndFunctions() throws DatamodelException {
        inspectProcedures();
        inspectFunctions();
    }
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void inspectTables() throws DatamodelException;
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public abstract void inspectViews() throws DatamodelException;
    
    /**
     * Database-specific method to get information about all tables and
     * views in the schema
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public void inspectTablesAndViews() throws DatamodelException {
        inspectTables();
        inspectViews();
    }
    
    /**
     * Get the XML document containg data model
     *
     * @return
     */
    public void inspect() {
        
        Element rootElement = InspectorDocument.getInstance().getRootElement();
        
        if (rootElement != null && rootElement.getChildNodes().getLength() == 0) {
            
            try {
                inspectTablesAndViews();
                inspectProceduresAndFunctions();
            } catch (DatamodelException e) {
                // do nothing
                e.printStackTrace();
            }
            
        }
        
    }
    
    /**
     * Gather datamodel (again). A new XML document is created and so
     * the datamodel will be read by getDocument()
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public void refreshDatamodel() throws DatamodelException {
        InspectorDocument.getInstance().createDocument();
        inspect();
    }
    
}
