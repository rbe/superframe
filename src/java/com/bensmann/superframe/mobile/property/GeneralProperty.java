/*
 * com/bensmann/mobile/util/PropertyHelper.java
 *
 * PropertyHelper.java created on 9. Februar 2007, 14:16 by rb
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

package com.bensmann.mobile.property;

/**
 *
 * @author rb
 * @version 1.0
 */
public final class GeneralProperty implements Property {
    
    /**
     * Initialize attributes when this class is loaded
     */
    static {
        
    }
    
    /**
     * Creates a new instance of PropertyHelper
     */
    public GeneralProperty() {
    }
    
    /**
     *
     * @return
     */
    public static String getAudioEncodings() {
        return System.getProperty("audio.encodings");
    }
    
    /**
     *
     * @return
     */
    public static String isAudioCaptureSupported() {
        return System.getProperty("supports.audio.capture");
    }
    
    /**
     *
     * @return
     */
    public static String isVideoCaptureSupported() {
        return System.getProperty("supports.video.capture");
    }
    
    /**
     *
     * @return
     */
    public static String isRecordingSupported() {
        return System.getProperty("supports.recording");
    }
    
    /**
     *
     * @return
     */
    public static String getVideoEncodings() {
        return System.getProperty("video.encodings");
    }
    
    /**
     *
     * @return
     */
    public static String getVideoSnapshotEncodings() {
        return System.getProperty("video.snapshot.encodings");
    }
    
    /**
     * JSR-75 PDA Optional Packages for the Java ME Platform (File and PIM API)
     *
     * @return
     */
    public static boolean isJsr75Supported() {
        
        boolean b = false;
        // TODO
        String v = getJsr75MediaVersion();
        
        if (v != null && v.length() > 0) {
            b = true;
        }
        
        return b;
        
    }
    
    /**
     * JSR 257 - Contactless API
     *
     * @return
     */
    public static String getJsr257Version() {
        return System.getProperty("microedition.contactless.version");
    }
    
    /**
     * JSR 211 - CHAPI
     *
     * @return
     */
    public static String getJsr211Version() {
        // CHAPI-Version
        return System.getProperty("microedition.chapi.version");
    }
    
    /**
     * JSR 205 - MMS API (Multimedia Message Service)
     *
     * @return
     */
    public static String getJsr205NumberOfMmsCenter() {
        return System.getProperty("wireless.messaging.mms.mmsc");
    }
    
    /**
     * JSR 185 - JTWI API
     *
     * @return
     */
    public static String getJsr185Version() {
        return System.getProperty("microedition.jtwi.version");
    }
    
    /**
     * JSR 184 - M3G API
     *
     * @return
     */
    public static String getJsr184Version() {
        return System.getProperty("microedition.m3g.version");
    }
    
    /**
     * JSR 180 - SIP API (Session Initiation Protocol)
     *
     * @return
     */
    public static String getJsr180Version() {
        return System.getProperty("microedition.sip.version");
    }
    
    /**
     * JSR-75 PDA Optional Packages for the Java ME Platform (File and PIM API)
     *
     * @return Support version string
     */
    public static String getJsr75MediaVersion() {
        return System.getProperty("microedition.media.version");
    }
    
    /**
     * JSR-75 PDA Optional Packages for the Java ME Platform (File and PIM API)
     *
     * @return Support version string
     */
    public static String getJsr75PIMVersion() {
        return System.getProperty("microedition.pim.version");
    }
    
    /**
     * JSR-75 PDA Optional Packages for the Java ME Platform (File and PIM API)
     *
     * @return Support version string
     */
    public static String getJsr75FileConnectionVersion() {
        return System.getProperty("microedition.io.file.FileConnection.version");
    }
    
    /**
     * JSR 82 - Java APIs for Bluetooth
     * Is Bluetooth API supported (tests by loading class javax.bluetooth)
     *
     * @return
     */
    public static boolean isJsr82Supported() {
        
        boolean b = false;
        
        try {
            Class.forName("javax.bluetooth");
            b = true;
        } catch(ClassNotFoundException e) {
        }
        
        return b;
        
    }
    
    /**
     * JSR 82 - Java APIs for Bluetooth
     *
     * @return Version string
     */
    public static String getJsr82Version() {
        return System.getProperty("Bluetooth.api.version");
    }
    
    /**
     * JSR 179 - Location API
     *
     * @return Support version string
     */
    public static String getJsr179Version() {
        return System.getProperty("microedition.location.version");
    }
    
    /**
     * JSR 177 - Smartcards
     *
     * @return
     */
    public static String getJsr177SmartcardSlots() {
        return System.getProperty("microedition.smartcardslots");
    }
    
    /**
     * JSR 135 - MMAPI - MultiMedia API
     *
     * @return
     */
    public static String getJsr135Version() {
        return System.getProperty("microedition.media.version");
    }
    
    /**
     * JSR 120 - Wireless Messaging API
     *
     * @return
     */
    public static String getJsr120NumberOfSmsCenter() {
        return System.getProperty("wireless.messaging.sms.smsc");
    }
    
    /**
     * Supported locale
     *
     * @return
     */
    public static String getLocale() {
        return System.getProperty("microedition.locale");
    }
    
    /**
     * Supported configuration (e.g. CLDC 1.0/1.1)
     *
     * @return
     */
    public static String getCldcVersion() {
        return System.getProperty("microedition.configuration");
    }
    
    /**
     * Supported profiles (e.g. MIDP 1.0/2.0/3.0)
     *
     * @return
     */
    public static String getMidpVersion() {
        return System.getProperty("microedition.profiles");
    }
    
    /**
     * JSR 139 - Connected Limited Device Configuration 1.1
     *
     * @return Platform string: Nokia, Sony Ericsson, Siemens, ...
     */
    public static String getPlatform() {
        return System.getProperty("microedition.platform");
    }
    
    /**
     *
     * @return
     */
    public static String getVendor() {
        
        String v = getPlatform();
        
        try {
            return v.substring(0, v.indexOf("/"));
        }
        // NullPointerException possible with indexOf()
        catch (Exception e) {
            return v;
        }
        
    }
    
    /**
     *
     * @return
     */
    public static String getSoftwareVersion() {
        
        String v = getPlatform();
        
        try {
            return v.substring(v.indexOf("/") + 1);
        }
        // NullPointerException possible with indexOf()
        catch (Exception e) {
            return v;
        }
        
    }
    
    /**
     * Are we running on Nokia?
     *
     * @return
     */
    public static boolean isNokiaPlatform() {
        
        boolean b = false;
        
        if (getPlatform().toUpperCase().indexOf("NOKIA") >= 0) {
            b = true;
        }
        
        return b;
        
    }
    
    /**
     * Are we running on Nokia S40?
     *
     * @return
     */
    public static boolean isNokiaS40Platform() {
        boolean b = false;
        return b;
    }
    
    /**
     * Are we running on Nokia S60?
     *
     * @return
     */
    public static boolean isNokiaS60Platform() {
        boolean b = false;
        return b;
    }
    
    /**
     * Are we running on Nokia S80?
     *
     * @return
     */
    public static boolean isNokiaS80Platform() {
        boolean b = false;
        return b;
    }
    
    /**
     * Are we running on Sony Ericsson?
     *
     * @return
     */
    public static boolean isSonyEricssonPlatform() {
        
        boolean b = false;
        
        if (getPlatform().toUpperCase().indexOf("SONY") >= 0) {
            b = true;
        }
        
        return b;
        
    }
    
    public static String getEncoding() {
        return System.getProperty("microedition.encoding");
    }
    
    /**
     * How many memory in bytes is totally available in our JVM?
     *
     * @return
     */
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }
    
    /**
     * How many memory in bytes is available for use in our JVM?
     *
     * @return
     */
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }
    
}
