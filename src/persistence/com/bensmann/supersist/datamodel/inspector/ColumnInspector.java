/*
 * com/bensmann/supersist/datamodel/ColumnInspector.java
 *
 * ColumnInspector.java created on 2. Februar 2007, 11:46 by rb
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
public interface ColumnInspector {
    
    /**
     * Returns the precision of the data-type of a certain column of a
     * table/view.
     *
     * @param name
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String getColumnPrecision(String name, String columnName)
    throws DatamodelException;
    
    /**
     * Returns the scale of the data-type of a certain column of a table/view.
     *
     *
     * @param name
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String getColumnScale(String name, String columnName)
    throws DatamodelException;
    
    /**
     * Returns the corresponding java.sql.Types data-type of a certain
     * column of a table/view.
     *
     * @param name
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    String getColumnType(String name, String columnName)
    throws DatamodelException;
    
}
