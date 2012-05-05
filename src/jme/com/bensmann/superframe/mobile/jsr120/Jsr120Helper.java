/*
 * com/bensmann/mobile/jsr120/Jsr120Helper.java
 *
 * Jsr120Helper.java created on 8. Februar 2007, 20:14 by rb
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

package com.bensmann.superframe.mobile.jsr120;

import com.bensmann.mobile.exception.Jsr120Exception;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author rb
 * @version 1.0
 */
public final class Jsr120Helper {
    
    /**
     * Do not create a new instance of Jsr120Helper
     */
    private Jsr120Helper() {
    }
    
    /**
     * Send a SMS
     * 
     * @param number 
     * @param text 
     * @throws com.bensmann.mobile.jsr120.exception.Jsr120Exception 
     */
    public static void sendSMS(String number, String text)
    throws Jsr120Exception {
        
        // TODO Ensure that number begins with +
        if (!number.startsWith("+")) {
            number = "+" + number;
        }
        
        MessageConnection mc = null;
        TextMessage tm = null;
        try {
            
            mc = (MessageConnection)Connector.open("sms://" + number);
            tm = (TextMessage) mc.newMessage(MessageConnection.TEXT_MESSAGE);
            tm.setPayloadText(text);
            mc.send(tm);
            mc.close();
            
        } catch (IOException e) {
            throw new Jsr120Exception("Cannot send SMS: " + e.toString());
        } catch (IllegalArgumentException e) {
            throw new Jsr120Exception("Cannot send SMS: " + e.toString());
        }
        
    }
    
}
