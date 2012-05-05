/*
 * com/bensmann/mobile/jsr135/AbstractPlatformPlayer.java
 *
 * AbstractPlatformPlayer.java created on 3. Februar 2007, 23:40 by rb
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
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;

/**
 *
 * @author rb
 * @version 1.0
 */
public abstract class AbstractPlatformPlayer implements PlatformPlayer {
    
    /**
     * Player
     */
    protected Player player;
    
    /**
     * VideoControl
     */
    protected VideoControl videoControl;
    
    /**
     * Encoding of image; device-specific
     */
    protected String encoding;
    
    /**
     * Width image to be captured; device-specific
     */
    protected int width;
    
    /**
     * Height of image to be captured; device-specific
     */
    protected int height;
    
    /**
     * Creates a new instance of AbstractPlatformPlayer
     */
    public AbstractPlatformPlayer() {
    }
    
    /**
     *
     * @param encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
    /**
     *
     * @return
     */
    public String getEncoding() {
        return encoding;
    }
    
    /**
     *
     * @param width
     * @param height
     */
    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     */
    public abstract void initializeCamera() throws Jsr135Exception;
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     */
    public void startPlayer() throws Jsr135Exception {
        
        try {
            player.start();
        } catch (MediaException e) {
            throw new Jsr135Exception(e.toString());
        }
        
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     * @return
     */
    public abstract byte[] getSnapshot() throws Jsr135Exception;
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     * @return
     */
    public Image getSnapshotAsImage() throws Jsr135Exception {
        byte[] b = getSnapshot();
        return Image.createImage(b, 0, b.length);
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     */
    public void stopPlayer() throws Jsr135Exception {
        
        try {
            player.stop();
        } catch (MediaException e) {
            throw new Jsr135Exception(e.toString());
        }
        
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     */
    public void closePlayer() throws Jsr135Exception {
        player.close();
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     * @return
     */
    public String getEncodingString() throws Jsr135Exception {
        return "encoding=" + encoding
                + "width=" + width
                + "height=" + height;
    }
    
}
