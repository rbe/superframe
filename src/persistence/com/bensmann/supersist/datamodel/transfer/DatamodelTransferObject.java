/*
 * DatamodelBean.java
 *
 * Created on 16. Mai 2006, 14:39
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

package com.bensmann.supersist.datamodel.transfer;

import java.io.Serializable;

/**
 *
 * @author rb
 */
public interface DatamodelTransferObject extends Serializable {
    
    /**
     *
     * @param tableOrViewName
     */
    void setTableOrView(String tableOrViewName);
    
    /**
     *
     * @param packageName
     * @param procedureName
     */
    void setProcedure(String packageName, String procedureName);
    
    /**
     * Reset all data set in the bean
     */
    void reset();

}
