/*
 * com/bensmann/mobile/jsr135/SonyEricssonPlatformPlayer.java
 *
 * SonyEricssonPlatformPlayer.java created on 3. Februar 2007, 23:52 by rb
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
import java.io.IOException;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.control.VideoControl;

/**
 *
 * @author rb
 * @version 1.0
 */
public class SonyEricssonPlatformPlayer extends AbstractPlatformPlayer {
    
    /**
     * Creates a new instance of SonyEricssonPlatformPlayer
     */
    public SonyEricssonPlatformPlayer() {
        encoding = "jpeg";
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     */
    public void initializeCamera() throws Jsr135Exception {
        
        try {
            
            player = Manager.createPlayer("capture://video?encoding=jpeg");
            player.realize();
            
            videoControl = (VideoControl) player.getControl("VideoControl");
            
        } catch (MediaException e) {
            throw new Jsr135Exception(e.toString());
        } catch (IOException e) {
            throw new Jsr135Exception(e.toString());
        }
        
    }
    
    /**
     *
     * @throws com.bensmann.mobile.jsr135.exception.Jsr135Exception
     * @return
     */
    public byte[] getSnapshot() throws Jsr135Exception {
        
        try {
            return videoControl.getSnapshot(null);
        } catch (MediaException e) {
            throw new Jsr135Exception(e.toString());
        }
        
    }
    
}
