/*
 * com/bensmann/supersist/datamodel/inspector/impl/BaseInspector.java
 *
 * BaseInspector.java created on 2. Februar 2007, 13:11 by rb
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
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 *
 * @author rb
 * @version 1.0
 */
public abstract class BaseInspector {
    
    /**
     * Logger
     */
    protected static Logger logger;
    
    /**
     *
     */
    protected static final InspectorDocument inspectorDocument;
    
    /**
     * XPath
     */
    protected static XPath xpath;

    /**
     * JDBC database connection
     */
    protected Connection connection;
    
    // Static initializer
    static {
        inspectorDocument = InspectorDocument.getInstance();
        xpath = XPathFactory.newInstance().newXPath();
    }
    
    /**
     * Creates a new instance of BaseInspector
     */
    public BaseInspector(Connection connection) {
        
        this.connection = connection;
        
        // Initialize logger and set level to ALL
        logger = Logger.getLogger(getClass().getName());
        logger.setLevel(Level.ALL);
        
    }
    
}
