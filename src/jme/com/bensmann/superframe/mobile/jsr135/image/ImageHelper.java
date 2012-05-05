/*
 * com/bensmann/mobile/jsr135/image/ImageHelper.java
 *
 * ImageHelper.java created on 4. Februar 2007, 00:07 by rb
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

package com.bensmann.superframe.mobile.jsr135.image;

import javax.microedition.lcdui.Image;

/**
 *
 * @author rb
 * @version 1.0
 */
public class ImageHelper {
    
    /**
     * Creates a new instance of ImageHelper
     */
    public ImageHelper() {
    }
    
    /**
     * Create byte[] from Image
     *
     * @param image
     * @return
     */
    public static byte[] getByteArray(Image image) {
        
        int raw[] = new int[image.getWidth() * image.getHeight()];
        image.getRGB(raw, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        byte rawByte[] = new byte[image.getWidth() * image.getHeight() * 4];
        int n = 0;
        
        int ARGB = 0;
        int a = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        
        for(int i = 0; i < raw.length; i++) {
            
            ARGB = raw[i];
            
            a = (ARGB & 0xff000000) >> 24;
            r = (ARGB & 0xff0000) >> 16;
            g = (ARGB & 0xff00) >> 8;
            b = ARGB & 0xff;
            
            rawByte[n] = (byte)b;
            rawByte[n + 1] = (byte)g;
            rawByte[n + 2] = (byte)r;
            rawByte[n + 3] = (byte)a;
            
            n += 4;
            
        }
        
        raw = null;
        
        return rawByte;
        
    }
    
}
