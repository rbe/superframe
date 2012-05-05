/*
 * com/bensmann/supersist/datamodel/SQLHelper.java
 *
 * SQLHelper.java created on 5. Februar 2007, 14:25 by rb
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

package com.bensmann.supersist.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author rb
 * @version 1.0
 */
public class SQLHelper {
    
    /**
     * Creates a new instance of SQLHelper
     */
    public SQLHelper() {
    }
    
    /**
     *
     * @param tableName
     * @throws java.sql.SQLException
     * @return
     */
    public static Map<String, String[]> getMetaDataMap(
            Statement statement, String tableOrViewName)
            throws SQLException {
        
        Map<String, String[]> metaDataMap = new Hashtable<String, String[]>();
        String tmpColumnClassName = null;
        String tmpColumnName = null;
        int tmpColumnTypeId = 0;
        // Query table with dummy query to get result set metadata
        String metaDataQuery =
                "SELECT * FROM " + tableOrViewName + " WHERE 1 = 2";
        
        ResultSet resultSet =
                statement.executeQuery(metaDataQuery);
        ResultSetMetaData metaData =
                resultSet.getMetaData();
        
        // Put all information about columns into a map
        for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
            
            tmpColumnName = metaData.getColumnName(i);
            tmpColumnClassName = metaData.getColumnClassName(i);
            tmpColumnTypeId = metaData.getColumnType(i);
            
            metaDataMap.put(tmpColumnName, new String[] {
                tmpColumnClassName, "" + tmpColumnTypeId
            });
            
        }
        
        // Close metadata objects
        resultSet.close();
        statement.close();
        
        return metaDataMap;
        
    }
    
}
