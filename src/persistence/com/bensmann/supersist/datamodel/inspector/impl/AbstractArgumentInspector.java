/*
 * com/bensmann/supersist/datamodel/inspector/impl/AbstractAgrumentInspector.java
 *
 * AbstractAgrumentInspector.java created on 2. Februar 2007, 13:15 by rb
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

import com.bensmann.supersist.datamodel.inspector.ArgumentInspector;
import com.bensmann.supersist.exception.DatamodelException;
import java.sql.Connection;
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
public class AbstractArgumentInspector
        extends BaseInspector
        implements ArgumentInspector {
    
    /**
     * Creates a new instance of AbstractAgrumentInspector
     *
     * @param connection
     */
    public AbstractArgumentInspector(Connection connection) {
        super(connection);
    }
    
    /**
     * Returns a list of parameter names for a certain procedure/function
     *
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String[] getArgumentNames(String packageName, String name)
    throws DatamodelException {
        
        String[] parameterNames = null;
        NodeList nodes = null;
        String search = "argument/@name";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.
                getProcedureOrFunctionElement(packageName, name);
        
        try {
            expr = xpath.compile(search);
            nodes = (NodeList) expr.evaluate(element, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find argument names for '" + name + "'", e);
        }
        
        parameterNames = new String[nodes.getLength()];
        for (int i = 0; i < nodes.getLength(); i++) {
            parameterNames[i] = nodes.item(i).getNodeValue();
        }
        
        return parameterNames;
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getArgumentDataType(String packageName, String procedureName,
            String argumentName)
            throws DatamodelException {
        
        // TODO //datamodel/... OK when searching over 'element' below?
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-type";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.getProcedureOrFunctionElement(
                packageName, procedureName);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find argument-type for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return node.getNodeValue();
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentDataLength(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-length";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.getProcedureOrFunctionElement(
                packageName, procedureName);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find data-length for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(node.getNodeValue());
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentDataPrecision(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-precision";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.getProcedureOrFunctionElement(
                packageName, procedureName);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find data-precision for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(node.getNodeValue());
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentDataScale(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-scale";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.getProcedureOrFunctionElement(
                packageName, procedureName);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find data-precision for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(node.getNodeValue());
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentPosition(String packageName, String procedureName,
            String argumentName)
            throws DatamodelException {
        
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@position";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.getProcedureOrFunctionElement(
                packageName, procedureName);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find argument-position for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(node.getNodeValue());
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getArgumentParameterType(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@in-out";
        XPathExpression expr = null;
        Node node = null;
        Element element = null;
        
        // Retrieve procedure or function element
        element = inspectorDocument.getProcedureOrFunctionElement(
                packageName, procedureName);
        
        try {
            expr = xpath.compile(search);
            node = (Node) expr.evaluate(element, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find argument " +
                    " parameter-type for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return node.getNodeValue();
        
    }
    
}
