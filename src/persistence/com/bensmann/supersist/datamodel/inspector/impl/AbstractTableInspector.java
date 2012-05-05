/*
 * com/bensmann/supersist/datamodel/inspector/AbstractTableInspector.java
 *
 * AbstractTableInspector.java created on 2. Februar 2007, 13:37 by rb
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

import com.bensmann.supersist.datamodel.inspector.*;
import com.bensmann.supersist.exception.DatamodelException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author rb
 * @version 1.0
 */
public class AbstractTableInspector
        extends BaseInspector
        implements TableInspector {
    
    /**
     * Inspector XML document
     */
    private InspectorDocument inspectorDocument;
    
    /**
     * Table name cache
     */
    private String[] tableNameCache;
    
    /**
     * Creates a new instance of AbstractTableInspector
     */
    public AbstractTableInspector() {
        inspectorDocument = InspectorDocument.getInstance();
    }
    
    /**
     * Queries number of tables from XML document using XPath
     *
     * @return Number of tables
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public int getTableCount() throws DatamodelException {
        
        String search = "//datamodel/tables[@table-count]/text()";
//        XPathExpression expr = null;
        Element element = null;
        
        try {
//            expr = xpath.compile(search);
//            element = (Element) expr.evaluate(
//                    inspectorDocument.getInstance().getDocument(),
//                    XPathConstants.NODE);
            element = inspectorDocument.lookForElement(search);
        } catch (DatamodelException e) {
            throw new DatamodelException("Cannot retrieve table count", e);
        }
        
        return new Integer(element.getTextContent());
        
    }
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    public String[] getTableNames() throws DatamodelException {
        
        String[] tableNames = null;
        String search = "//datamodel/tables/@name";
//        XPathExpression expr = null;
        NodeList nodes = null;
        
        if (tableNameCache == null || tableNameCache.length == 0) {
            
            try {
                
//                expr = xpath.compile(search);
//                nodes = (NodeList) expr.evaluate(
//                        document, XPathConstants.NODESET);
                nodes = inspectorDocument.getInstance().lookForElements(search);
                
                tableNames = new String[nodes.getLength()];
                for (int i = 0; i < nodes.getLength(); i++) {
                    tableNames[i] = nodes.item(i).getNodeValue();
                }
                
            } catch (DatamodelException e) {
                throw new DatamodelException(
                        "Cannot retrieve table names: " + e.getMessage(), e);
            }
            
        }
        
        return tableNames;
        
    }
    
    /**
     *
     * @param tableOrView
     * @param columnName
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    public boolean tableColumnExists(String tableOrView, String columnName)
    throws DatamodelException {
        
        boolean exists = false;
        String[] columnNames = getTableColumnNames(tableOrView);
        
        for (String column : columnNames) {
            
            if (column.toUpperCase().equals(columnName.toUpperCase())) {
                exists = true;
                break;
            }
            
        }
        
        return exists;
        
    }
    
    /**
     *
     * @param name
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    public String[] getTableColumnNames(String name) throws DatamodelException {
        
        String[] columnNames = null;
        String search = "column/@name";
        NodeList nodes = null;
        Element element = null;
        XPathExpression expr = null;
        
        // Retrieve table or view element
        element = inspectorDocument.getTableElement(name);
        
        // Retrieve all 'column' children and get their name attribute
        try {
            expr = xpath.compile(search);
            nodes = (NodeList) expr.evaluate(element, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find column names for view '" + name + "'", e);
        }
        
        columnNames = new String[nodes.getLength()];
        for (int i = 0; i < nodes.getLength(); i++) {
            columnNames[i] = nodes.item(i).getNodeValue();
        }
        
        if (columnNames.length == 0) {
            
            throw new DatamodelException("Cannot find column names for view " +
                    "'" + name + "'", null);
            
        }
        
        return columnNames;
        
    }
    
    /**
     * Create element for maximum read count depending on bytes.
     *
     * @param bytes
     * @return
     */
    public Element getReadCountElement(int bytes) {
        
        Element readCountElement =
                InspectorDocument.getInstance().
                createElement("maximum-read-count");
        
        readCountElement.setAttribute("read-count-512-byte",
                "" + calculateReads(bytes, 512));
        readCountElement.setAttribute("read-count-1-kbyte",
                "" + calculateReads(bytes, 1024));
        readCountElement.setAttribute("read-count-2-kbyte",
                "" + calculateReads(bytes, 2 * 1024));
        readCountElement.setAttribute("read-count-4-kbyte",
                "" + calculateReads(bytes, 4 * 1024));
        readCountElement.setAttribute("read-count-8-kbyte",
                "" + calculateReads(bytes, 8 * 1024));
        readCountElement.setAttribute("read-count-16-kbyte",
                "" + calculateReads(bytes, 16 * 1024));
        readCountElement.setAttribute("read-count-32-kbyte",
                "" + calculateReads(bytes, 32 * 1024));
        
        return readCountElement;
        
    }
    
    /**
     *
     * @param rowLength
     * @param blockSize
     * @return
     */
    public int calculateReads(int rowLength, int blockSize) {
        
        int readCount = 0;
        
        float div = (float) rowLength / (float) blockSize;
        readCount = Math.round(div);
        readCount = (int) div;
        
        if ((div - (int) div) > 0) {
            readCount++;
        }
        if (readCount == 0) {
            readCount = 1;
        }
        
        return readCount;
        
    }
    
}
