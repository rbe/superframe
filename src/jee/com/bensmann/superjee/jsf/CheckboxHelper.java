/*
 * com/bensmann/superjee/jsf/CheckboxHelper.java
 *
 * CheckboxHelper.java created on 12. Februar 2007, 11:58 by rb
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

package com.bensmann.superjee.jsf;

import com.sun.webui.jsf.component.CheckboxGroup;

/**
 *
 * @author rb
 * @version 1.0
 */
public final class CheckboxHelper {
    
    /**
     * Do not create a new instance of CheckboxHelper
     */
    private CheckboxHelper() {
    }
    
    /**
     *
     * @param checkboxGroup
     * @return
     */
    public static String[] checkboxToStringArray(CheckboxGroup checkboxGroup) {
        
        String[] arr = null;
        
        int i = 0;
        Object[] obj = (Object[]) checkboxGroup.getValue();
        arr = new String[obj.length];
        for (Object o : obj) {
            arr[i++] = (String) o;
        }
        
        obj = null;
        
        return arr;
        
    }
    
}
