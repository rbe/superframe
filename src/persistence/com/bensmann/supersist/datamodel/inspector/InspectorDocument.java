/*
 * com/bensmann/supersist/datamodel/inspector/InspectorDocument.java
 *
 * InspectorDocument.java created on 2. Februar 2007, 13:29 by rb
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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Initialization On Demand Holder Idiom
 *
 * @author rb
 * @version 1.0
 */
public final class InspectorDocument {
    
    /**
     * XML document that holds the datamodel description
     */
    private Document document;
    
    /**
     * Root element of XML document
     */
    private Element rootElement;
    
    /**
     * XPath
     */
    private static XPath xpath;
    
    // Static initializer
    static {
        xpath = XPathFactory.newInstance().newXPath();
    }
    
    /**
     * Do not create a new instance of InspectorDocument
     */
    private InspectorDocument() {
    }
    
    /**
     * The lazy holder for InspectorDocument
     */
    private static class LazyHolder {
        
        /**
         * The one-and-only instance
         */
        static final InspectorDocument inspectorDocument;
        
        static {
            // Initialize instance of InspectorDocument
            inspectorDocument = new InspectorDocument();
        }
        
    }
    
    /**
     *
     * @return
     */
    public static InspectorDocument getInstance() {
        return LazyHolder.inspectorDocument;
    }
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public void createDocument() throws DatamodelException {
        
        try {
            
            document = DocumentBuilderFactory.
                    newInstance().newDocumentBuilder().newDocument();
            
            rootElement = document.createElement("datamodel");
            document.appendChild(rootElement);
            
        } catch (ParserConfigurationException e) {
            throw new DatamodelException("Cannot create XML document", e);
        }
        
    }
    
    /**
     * Used by XPath
     *
     * @return
     */
    public Document getDocument() {
        return document;
    }
    
    /**
     * Delegate
     * @return
     */
    public Element getRootElement() {
//        return rootElement;
        return document.getDocumentElement();
    }
    
    /**
     * Delegate
     *
     * @param tagName
     * @return
     */
    public Element createElement(String tagName) {
        return document.createElement(tagName);
    }
    
    /**
     *
     * @param expr
     * @throws javax.xml.xpath.XPathExpressionException
     * @return
     */
    public Element lookForElement(String expr)
    throws DatamodelException {
        
        try {
            
            return (Element) xpath.compile(expr).
                    evaluate(document, XPathConstants.NODE);
            
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot query XPath expression: "
                    + expr + ": " + e.getMessage(), e);
        }
        
    }
    
    /**
     *
     * @param expr
     * @throws javax.xml.xpath.XPathExpressionException
     * @return
     */
    public Element lookForElement(XPathExpression expr)
    throws DatamodelException {
        
        try {
            return (Element) expr.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException("Cannot find element", e);
        }
        
    }
    
    /**
     *
     * @param expr
     * @throws javax.xml.xpath.XPathExpressionException
     * @return
     */
    public NodeList lookForElements(String expr)
    throws DatamodelException {
        
        try {
            
            return (NodeList) xpath.compile(expr).
                    evaluate(document, XPathConstants.NODESET);
            
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot query XPath expression: " +
                    expr + ": " + e.getMessage(), e);
        }
        
    }
    
    /**
     *
     * @param expr
     * @throws javax.xml.xpath.XPathExpressionException
     * @return
     */
    public NodeList lookForElements(XPathExpression expr)
    throws DatamodelException {
        
        try {
            return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                     "Cannot query XPath expression: " + e.getMessage(), e);
        }
        
    }
    
    /**
     * Queries a certain table element from XML document using XPath
     *
     * @param name
     * @return Table element
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getTableElement(String name) throws DatamodelException {
        
        String search = "//datamodel/tables/table[@name='" + name + "']";
        XPathExpression expr = null;
        Element element = null;
        
        try {
            expr = xpath.compile(search);
            element = (Element) expr.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find table element for '" + name + "'", e);
        }
        
        return element;
        
    }
    
    /**
     * Queries a certain view element from XML document using XPath
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getViewElement(String name) throws DatamodelException {
        
        String search = "//datamodel/tables/view[@name='" + name + "']";
        XPathExpression expr = null;
        Element element = null;
        
        try {
            expr = xpath.compile(search);
            element = (Element) expr.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find view element for '" + name + "'", e);
        }
        
        return element;
        
    }
    
    /**
     *
     * @param name
     * @return
     */
    public Element getTableOrViewElement(String name)
    throws DatamodelException {
        
        Element element = null;
        
        // Retrieve table or view element
        element = getTableElement(name);
        if (element == null) {
            element = getViewElement(name);
        }
        
        if (element == null) {
            throw new DatamodelException(
                    "Cannot find table or view named " + name, null);
        }
        
        return element;
        
    }
    
    /**
     * Returns a certain element for a certain procedure
     *
     *
     * @return
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getProcedureElement(String packageName, String name)
    throws DatamodelException {
        
        String search =
                "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + name + "']";
        XPathExpression expr = null;
        Element element = null;
        
        try {
            expr = xpath.compile(search);
            element = (Element) expr.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find procedure element for '"
                    + packageName
                    + "."
                    + name
                    + "'", e);
        }
        
        return element;
        
    }
    
    /**
     * Returns a certain element for a certain function
     *
     * @return
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getFunctionElement(String packageName, String name)
    throws DatamodelException {
        
        String search =
                "//datamodel/procedures/function" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + name + "']";
        XPathExpression expr = null;
        Element element = null;
        
        try {
            expr = xpath.compile(search);
            element = (Element) expr.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new DatamodelException(
                    "Cannot find view element for '"
                    + packageName
                    + "."
                    + name
                    + "'", e);
        }
        
        return element;
        
    }
    
    /**
     *
     * @param packageName
     * @param name
     * @return
     */
    public Element getProcedureOrFunctionElement(
            String packageName, String name)
            throws DatamodelException {
        
        Element element = null;
        
        // Retrieve table or view element
        element = getProcedureElement(packageName, name);
        if (element == null) {
            element = getFunctionElement(packageName, name);
        }
        
        if (element == null) {
            throw new DatamodelException(
                    "Cannot find procedure or function named "
                    + packageName
                    + "."
                    + name, null);
        }
        
        return element;
        
    }
    
}
