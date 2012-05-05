/*
 * com/bensmann/supersist/datamodel/inspector/impl/AbstractProcedureInspector.java
 *
 * AbstractProcedureInspector.java created on 5. Februar 2007, 14:24 by rb
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

import com.bensmann.supersist.datamodel.inspector.ProcedureInspector;
import com.bensmann.supersist.exception.DatamodelException;
import org.w3c.dom.Element;

/**
 *
 * @author rb
 * @version 1.0
 */
public class AbstractProcedureInspector
        extends BaseInspector
        implements ProcedureInspector {
    
    /**
     * Creates a new instance of AbstractProcedureInspector
     */
    public AbstractProcedureInspector() {
    }
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     * @return 
     */
    public int getPackageCount() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     * @return 
     */
    public int getProcedureCount() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @param packageName 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     * @return 
     */
    public int getProcedureCount(String packageName) throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     * @return 
     */
    public String[] getPackageNames() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     * @return 
     */
    public String[] getProcedureNames() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @param packageName 
     * @throws com.bensmann.supersist.exception.DatamodelException 
     * @return 
     */
    public String[] getProcedureNames(String packageName)
    throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
}
