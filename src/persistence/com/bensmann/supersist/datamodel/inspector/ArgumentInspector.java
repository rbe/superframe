/*
 * com/bensmann/supersist/datamodel/inspector/ArgumentInspector.java
 *
 * ArgumentInspector.java created on 2. Februar 2007, 12:12 by rb
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

/**
 *
 * @author rb
 * @version 1.0
 */
public interface ArgumentInspector {
    
    /**
     *
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    int getArgumentDataLength(
            String packageName, String procedureName, String argumentName)
            throws DatamodelException;
    
    /**
     *
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    int getArgumentDataPrecision(
            String packageName, String procedureName, String argumentName)
            throws DatamodelException;
    
    /**
     *
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    int getArgumentDataScale(
            String packageName, String procedureName, String argumentName)
            throws DatamodelException;
    
    /**
     *
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String getArgumentDataType(
            String packageName, String procedureName, String argumentName)
            throws DatamodelException;
    
    /**
     * Returns a list of parameter names for a certain procedure/function
     *
     * @param packageName
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String[] getArgumentNames(String packageName, String name)
    throws DatamodelException;
    
    /**
     *
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String getArgumentParameterType(
            String packageName, String procedureName, String argumentName)
            throws DatamodelException;
    
    /**
     *
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    int getArgumentPosition(
            String packageName, String procedureName, String argumentName)
            throws DatamodelException;
    
}
