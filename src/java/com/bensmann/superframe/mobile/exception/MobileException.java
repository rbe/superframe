/*
 * com/bensmann/mobile/exception/MobileException.java
 *
 * MobileException.java created on 9. Februar 2007, 20:12 by rb
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

package com.bensmann.mobile.exception;

/**
 *
 * @author rb
 * @version 1.0
 */
public class MobileException extends Exception {
    
    /**
     * Creates a new instance of MobileException
     *
     * @param message 
     */
    public MobileException(String message) {
        super(message);
    }
    
}
