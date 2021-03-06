/*
 * com/bensmann/supersist/datamodel/bean/AbstractDatamodelBean.java
 *
 * AbstractDatamodelBean.java created on 17. Januar 2007, 19:46 by rb
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

import com.bensmann.supersist.exception.DatamodelException;

/**
 *
 * @author rb
 * @version 1.0
 */
public abstract class AbstractDatamodelHandler  implements DatamodelHandler {

    /**
     *
     */
    protected DatamodelTransferObject datamodelTransferObject;
    
    /**
     * Creates a new instance of AbstractDatamodelBean
     * 
     * @param datamodelBean 
     */
    public AbstractDatamodelHandler(DatamodelTransferObject datamodelTransferObject) {
        this.datamodelTransferObject = datamodelTransferObject;
    }
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    public void load() throws DatamodelException {
    }
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     */
    public void save() throws DatamodelException {
    }
    
}
