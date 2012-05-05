/*
 * com/bensmann/supersist/datamodel/inspector/impl/AbstractViewInspector.java
 *
 * AbstractViewInspector.java created on 2. Februar 2007, 14:49 by rb
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

import com.bensmann.supersist.datamodel.inspector.InspectorDocument;
import com.bensmann.supersist.datamodel.inspector.ViewInspector;
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
public class AbstractViewInspector extends BaseInspector implements ViewInspector {
    
    /**
     * Inspector XML document
     */
    private InspectorDocument inspectorDocument;
    
    /**
     * XML document
     */
    private Document document;
    
    /**
     * View name cache
     */
    private String[] viewNameCache;
    
    /**
     * Creates a new instance of AbstractViewInspector
     */
    public AbstractViewInspector() {
        inspectorDocument = InspectorDocument.getInstance();
        document = inspectorDocument.getDocument();
    }
    
    /**
     *
     * @return
     */
    public String[] getViewNames() throws DatamodelException {
        
        String[] viewNames = null;
        String search = "//datamodel/views/@name";
        XPathExpression expr = null;
        NodeList nodes = null;
        
        if (viewNameCache == null || viewNameCache.length == 0) {
            
            try {
                
                expr = xpath.compile(search);
                nodes = (NodeList) expr.evaluate(
                        document, XPathConstants.NODESET);
                
                viewNames = new String[nodes.getLength()];
                for (int i = 0; i < nodes.getLength(); i++) {
                    viewNames[i] = nodes.item(i).getNodeValue();
                }
                
            } catch (XPathExpressionException e) {
                throw new DatamodelException(
                        "Cannot retrieve view names: " + e.getMessage(), e);
            }
            
        }
        
        return viewNames;
        
    }
    
    /**
     * Queries number of views from XML document using XPath
     *
     * @return Number of views
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public int getViewCount() throws DatamodelException {
        
        String search = "//datamodel/tables[@view-count]/text()";
        XPathExpression expr = null;
        Element element = null;
        
        try {
            expr = xpath.compile(search);
            element = (Element) expr.evaluate(document, XPathConstants.NODE);
        }   catch (XPathExpressionException e) {
            throw new DatamodelException("Cannot retrieve view count", e);
        }
        
        return new Integer(element.getTextContent());
        
    }
    
    /**
     *
     * @param tableOrView
     * @param columnName
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    public boolean viewColumnExists(String tableOrView, String columnName)
    throws DatamodelException {
        
        boolean exists = false;
        String[] columnNames = getViewColumnNames(tableOrView);
        
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
    public String[] getViewColumnNames(String name) throws DatamodelException {
        
        String[] columnNames = null;
        String search = "column/@name";
        NodeList nodes = null;
        Element element = null;
        XPathExpression expr = null;
        
        // Retrieve table or view element
        element = inspectorDocument.getViewElement(name);
        
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
    
}
