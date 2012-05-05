/*
 * com/bensmann/mobile/jsr135/PlatformPlayer.java
 *
 * ThePlayer.java created on 3. Februar 2007, 23:24 by rb
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

package com.bensmann.superframe.mobile.jsr135.player;

import com.bensmann.mobile.exception.Jsr135Exception;
import javax.microedition.lcdui.Image;

/**
 *
 * @author rb
 */
public interface PlatformPlayer {
    
    /**
     * 
     * @param encoding 
     */
    void setEncoding(String encoding);
    
    /**
     * 
     * @return 
     */
    String getEncoding();
    
    /**
     * 
     * @param width 
     * @param height 
     */
    void setResolution(int width, int height);
    
    /**
     * 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     */
    void initializeCamera() throws Jsr135Exception;
    
    /**
     * 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     */
    void startPlayer() throws Jsr135Exception;
    
    /**
     * 
     * 
     * @return 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     */
    byte[] getSnapshot() throws Jsr135Exception;
    
    /**
     * 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     * @return 
     */
    Image getSnapshotAsImage() throws Jsr135Exception;
    
    /**
     * 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     */
    void stopPlayer() throws Jsr135Exception;
    
    /**
     * 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     */
    void closePlayer() throws Jsr135Exception;
    
    /**
     * 
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception 
     * @return 
     */
    String getEncodingString() throws Jsr135Exception;
    
}
