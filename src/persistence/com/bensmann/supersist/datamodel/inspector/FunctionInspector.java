/*
 * com/bensmann/supersist/datamodel/inspector/FunctionInspector.java
 *
 * FunctionInspector.java created on 5. Februar 2007, 14:28 by rb
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
 */
public interface FunctionInspector {
    
    /**
     *
     * @throws com.bensmann.supersist.exception.DatamodelException
     * @return
     */
    int getFunctionCount() throws DatamodelException;
    
    /**
     *
     * @return
     * @param packageName
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    int getFunctionCount(String packageName) throws DatamodelException;
    
    /**
     * Returns list of names of all functions
     * 
     * 
     * @return 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    String[] getFunctionNames() throws DatamodelException;
    
    /**
     * Returns list of names of all functions of a certain package
     * 
     * @return 
     * @param packageName 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    String[] getFunctionNames(String packageName) throws DatamodelException;
    
}
