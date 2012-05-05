/*
 * com/bensmann/mobile/util/IMEIUtil.java
 *
 * IMEIUtil.java created on 8. Februar 2007, 20:22 by rb
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

package com.bensmann.mobile.util;

/**
 *
 * @author rb
 * @version 1.0
 */
public final class ImeiHelper {
    
    /**
     * Do not create a new instance of IMEIUtil
     */
    private ImeiHelper() {
    }
    
    /**
     * Try all known IMEI property names to find the phone's IMEI. Only a signed
     * midlet can read the IMEI, an unsigned midlet will see a null value when
     * reading IMEI properties.
     *
     * @return IMEI string of phone
     */
    public static String getIMEI() {
        
        String imei = "";
        String[] properties = {
            // Motorola
            "IMEI",
            // Nokia
            "phone.imei", "com.nokia.IMEI",
            // Nokia Series 40 only
            "com.nokia.mid.imei",
            // Siemens
            "com.siemens.IMEI",
            // Sony Ericsson
            "com.sonyericsson.imei",
            // Sony Ericsson P910
            "com.sonyericsson.IMEI"
        };
        
        // Try each of the known property names
        for (int i = 0; i < properties.length; i++) {
            
            imei = System.getProperty(properties[i]);
            
            // Found IMEI?
            if (imei != null) {
                break;
            }
            
        }
        
        return imei;
        
    }
    
}
