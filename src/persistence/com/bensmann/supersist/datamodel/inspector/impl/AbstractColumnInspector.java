/*
 * com/bensmann/supersist/datamodel/inspector/impl/AbstractColumnInspector.java
 *
 * AbstractColumnInspector.java created on 2. Februar 2007, 12:53 by rb
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
import com.bensmann.supersist.exception.DatamodelException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rb
 * @version 1.0
 */
public class AbstractColumnInspector extends BaseInspector {
    
    /**
     * Inspector XML document
     */
    private InspectorDocument inspectorDocument;
    
    /**
     * Creates a new instance of AbstractColumnInspector
     */
    public AbstractColumnInspector() {
        inspectorDocument = InspectorDocument.getInstance();
    }
    
    /**
     * Returns the corresponding java.sql.Types data-type of a certain
     * column of a table/view.
     *
     * @param name
     * @param columnName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getColumnType(String name, String columnName)
    throws DatamodelException {
        
        String search = "column[@name='" + columnName + "']/@data-type";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve table or view element
        element = inspectorDocument.getTableOrViewElement(name);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find data-type for '"
                    + name + "/" + columnName + "'", e);
        }
        
        return node.getNodeValue();
        
    }
    
    /**
     * Returns the precision of the data-type of a certain column of a
     * table/view.
     *
     * @param name
     * @param columnName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getColumnPrecision(String name, String columnName)
    throws DatamodelException {
        
        String search = "column[@name='" + columnName + "']/@data-precision";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve table or view element
        element = inspectorDocument.getTableOrViewElement(name);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find precision for '"
                    + name + "/" + columnName + "'", e);
        }
        
        return node.getNodeValue();
        
    }
    
    /**
     * Returns the scale of the data-type of a certain column of a table/view.
     *
     * @param name
     * @param columnName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getColumnScale(String name, String columnName)
    throws DatamodelException {
        
        String search = "column[@name='" + columnName + "']/@data-scale";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve table or view element
        element = inspectorDocument.getTableOrViewElement(name);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find data-scale for '"
                    + name + "/" + columnName + "'", e);
        }
        
        return node.getNodeValue();
        
    }
    
}
