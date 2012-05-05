/*
 * com/bensmann/supersist/datamodel/ViewInspector.java
 *
 * ViewInspector.java created on 2. Februar 2007, 11:37 by rb
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
import org.w3c.dom.Element;

/**
 *
 * @author rb
 */
public interface ViewInspector {
    
    /**
     *
     *
     * @param tableOrView
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    boolean viewColumnExists(String tableOrView, String columnName)
    throws DatamodelException;
    
    /**
     * Returns a list of column names for a certain table/view
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String[] getViewColumnNames(String name) throws DatamodelException;
    
    /**
     * Queries number of views from XML document using XPath
     *
     * @return Number of views
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    int getViewCount() throws DatamodelException;
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    String[] getViewNames() throws DatamodelException;
    
}
