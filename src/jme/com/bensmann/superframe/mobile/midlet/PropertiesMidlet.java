/*
 * com/bensmann/mobile/midlet/PropertiesMidlet.java
 *
 * PropertiesMidlet.java created on 11. Februar 2007, 14:28 by rb
 *
 * Copyright (C) 2006 Ralf Bensmann, java@bensmann.com
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

package com.bensmann.superframe.mobile.midlet;

import com.bensmann.mobile.util.ImeiHelper;
import com.bensmann.mobile.property.GeneralProperty;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author rb
 */
public class PropertiesMidlet extends MIDlet {
    
    /** Creates a new instance of PropertiesMidlet */
    public PropertiesMidlet() {
        initialize();
    }
    
    private Form ShowPropertyForm;//GEN-BEGIN:MVDFields
    private StringItem imeiStringItem;
    private StringItem jsr205stringItem;
    private StringItem jsr75StringItem;
    private StringItem midpStringItem;
    private StringItem cldcStringItem;
    private StringItem encodingStringItem;
    private StringItem audioCaptureSupportedStringItem;
    private StringItem videoCaptureSupportedStringItem;
    private StringItem audioEncodingsStringItem;
    private StringItem videoEncodingsStringItem;
    private StringItem snapshotEncodingsStringItem;
    private StringItem vendorStringItem;
    private StringItem swVersionStringItem;
    private StringItem jsr120StringItem;//GEN-END:MVDFields
    
//GEN-LINE:MVDMethods

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        getDisplay().setCurrent(get_ShowPropertyForm());//GEN-LINE:MVDInitInit
        // Insert post-init code here
    }//GEN-LINE:MVDInitEnd
    
    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay() {//GEN-FIRST:MVDGetDisplay
        return Display.getDisplay(this);
    }//GEN-LAST:MVDGetDisplay
    
    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet() {//GEN-FIRST:MVDExitMidlet
        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }//GEN-LAST:MVDExitMidlet

    /** This method returns instance for ShowPropertyForm component and should be called instead of accessing ShowPropertyForm field directly.//GEN-BEGIN:MVDGetBegin2
     * @return Instance for ShowPropertyForm component
     */
    public Form get_ShowPropertyForm() {
        if (ShowPropertyForm == null) {//GEN-END:MVDGetBegin2
            // Insert pre-init code here
            ShowPropertyForm = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit2
                get_vendorStringItem(),
                get_swVersionStringItem(),
                get_midpStringItem(),
                get_cldcStringItem(),
                get_encodingStringItem(),
                get_audioCaptureSupportedStringItem(),
                get_audioEncodingsStringItem(),
                get_videoCaptureSupportedStringItem(),
                get_videoEncodingsStringItem(),
                get_snapshotEncodingsStringItem(),
                get_jsr75StringItem(),
                get_jsr120StringItem(),
                get_jsr205stringItem(),
                get_imeiStringItem()
            });//GEN-END:MVDGetInit2
            
            // Vendor
            vendorStringItem.setText(GeneralProperty.getVendor());
            // SW version
            swVersionStringItem.setText(GeneralProperty.getSoftwareVersion());
            // MIDP
            midpStringItem.setText(GeneralProperty.getMidpVersion());
            // CLDC
            cldcStringItem.setText(GeneralProperty.getCldcVersion());
            // Encoding
            encodingStringItem.setText(GeneralProperty.getEncoding());
            // Audio capture supported?
            audioCaptureSupportedStringItem.setText(GeneralProperty.isAudioCaptureSupported());
            // Audio encodings
            audioEncodingsStringItem.setText(GeneralProperty.getAudioEncodings());
            // Video capture supported?
            videoCaptureSupportedStringItem.setText(GeneralProperty.isVideoCaptureSupported());
            // Video encodings
            videoEncodingsStringItem.setText(GeneralProperty.getVideoEncodings());
            // Snapshot encodings
            snapshotEncodingsStringItem.setText(GeneralProperty.getVideoSnapshotEncodings());
            // JSR 75
            jsr75StringItem.setText(GeneralProperty.isJsr75Supported() + ": "
                    + GeneralProperty.getJsr75MediaVersion()
                    + "/" + GeneralProperty.getJsr75PIMVersion()
                    + "/" + GeneralProperty.getJsr75FileConnectionVersion());
            // JSR 120
            jsr120StringItem.setText(GeneralProperty.getJsr120NumberOfSmsCenter());
            // JSR 205
            jsr205stringItem.setText(GeneralProperty.getJsr205NumberOfMmsCenter());
            
        }//GEN-BEGIN:MVDGetEnd2
        return ShowPropertyForm;
    }//GEN-END:MVDGetEnd2

    /** This method returns instance for imeiStringItem component and should be called instead of accessing imeiStringItem field directly.//GEN-BEGIN:MVDGetBegin3
     * @return Instance for imeiStringItem component
     */
    public StringItem get_imeiStringItem() {
        if (imeiStringItem == null) {//GEN-END:MVDGetBegin3
            // Insert pre-init code here
            imeiStringItem = new StringItem("IMEI", "");//GEN-LINE:MVDGetInit3
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd3
        return imeiStringItem;
    }//GEN-END:MVDGetEnd3

    /** This method returns instance for jsr205stringItem component and should be called instead of accessing jsr205stringItem field directly.//GEN-BEGIN:MVDGetBegin4
     * @return Instance for jsr205stringItem component
     */
    public StringItem get_jsr205stringItem() {
        if (jsr205stringItem == null) {//GEN-END:MVDGetBegin4
            // Insert pre-init code here
            jsr205stringItem = new StringItem("JSR 205 - MMS", "");//GEN-LINE:MVDGetInit4
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd4
        return jsr205stringItem;
    }//GEN-END:MVDGetEnd4

    /** This method returns instance for jsr75StringItem component and should be called instead of accessing jsr75StringItem field directly.//GEN-BEGIN:MVDGetBegin5
     * @return Instance for jsr75StringItem component
     */
    public StringItem get_jsr75StringItem() {
        if (jsr75StringItem == null) {//GEN-END:MVDGetBegin5
            // Insert pre-init code here
            jsr75StringItem = new StringItem("JSR 75 - File and PIM", "");//GEN-LINE:MVDGetInit5
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd5
        return jsr75StringItem;
    }//GEN-END:MVDGetEnd5
    /** This method returns instance for midpStringItem component and should be called instead of accessing midpStringItem field directly.//GEN-BEGIN:MVDGetBegin7
     * @return Instance for midpStringItem component
     */
    public StringItem get_midpStringItem() {
        if (midpStringItem == null) {//GEN-END:MVDGetBegin7
            // Insert pre-init code here
            midpStringItem = new StringItem("MIDP", "");//GEN-LINE:MVDGetInit7
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd7
        return midpStringItem;
    }//GEN-END:MVDGetEnd7

    /** This method returns instance for cldcStringItem component and should be called instead of accessing cldcStringItem field directly.//GEN-BEGIN:MVDGetBegin8
     * @return Instance for cldcStringItem component
     */
    public StringItem get_cldcStringItem() {
        if (cldcStringItem == null) {//GEN-END:MVDGetBegin8
            // Insert pre-init code here
            cldcStringItem = new StringItem("CLDC", "");//GEN-LINE:MVDGetInit8
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd8
        return cldcStringItem;
    }//GEN-END:MVDGetEnd8

    /** This method returns instance for encodingStringItem component and should be called instead of accessing encodingStringItem field directly.//GEN-BEGIN:MVDGetBegin9
     * @return Instance for encodingStringItem component
     */
    public StringItem get_encodingStringItem() {
        if (encodingStringItem == null) {//GEN-END:MVDGetBegin9
            // Insert pre-init code here
            encodingStringItem = new StringItem("Encoding", "");//GEN-LINE:MVDGetInit9
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd9
        return encodingStringItem;
    }//GEN-END:MVDGetEnd9

    /** This method returns instance for audioCaptureSupportedStringItem component and should be called instead of accessing audioCaptureSupportedStringItem field directly.//GEN-BEGIN:MVDGetBegin10
     * @return Instance for audioCaptureSupportedStringItem component
     */
    public StringItem get_audioCaptureSupportedStringItem() {
        if (audioCaptureSupportedStringItem == null) {//GEN-END:MVDGetBegin10
            // Insert pre-init code here
            audioCaptureSupportedStringItem = new StringItem("Audio Capture supported?", "");//GEN-LINE:MVDGetInit10
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd10
        return audioCaptureSupportedStringItem;
    }//GEN-END:MVDGetEnd10

    /** This method returns instance for videoCaptureSupportedStringItem component and should be called instead of accessing videoCaptureSupportedStringItem field directly.//GEN-BEGIN:MVDGetBegin11
     * @return Instance for videoCaptureSupportedStringItem component
     */
    public StringItem get_videoCaptureSupportedStringItem() {
        if (videoCaptureSupportedStringItem == null) {//GEN-END:MVDGetBegin11
            // Insert pre-init code here
            videoCaptureSupportedStringItem = new StringItem("Video capture supported?", "");//GEN-LINE:MVDGetInit11
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd11
        return videoCaptureSupportedStringItem;
    }//GEN-END:MVDGetEnd11

    /** This method returns instance for audioEncodingsStringItem component and should be called instead of accessing audioEncodingsStringItem field directly.//GEN-BEGIN:MVDGetBegin12
     * @return Instance for audioEncodingsStringItem component
     */
    public StringItem get_audioEncodingsStringItem() {
        if (audioEncodingsStringItem == null) {//GEN-END:MVDGetBegin12
            // Insert pre-init code here
            audioEncodingsStringItem = new StringItem("Audio encodings", "");//GEN-LINE:MVDGetInit12
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd12
        return audioEncodingsStringItem;
    }//GEN-END:MVDGetEnd12

    /** This method returns instance for videoEncodingsStringItem component and should be called instead of accessing videoEncodingsStringItem field directly.//GEN-BEGIN:MVDGetBegin13
     * @return Instance for videoEncodingsStringItem component
     */
    public StringItem get_videoEncodingsStringItem() {
        if (videoEncodingsStringItem == null) {//GEN-END:MVDGetBegin13
            // Insert pre-init code here
            videoEncodingsStringItem = new StringItem("Video encodings", "");//GEN-LINE:MVDGetInit13
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd13
        return videoEncodingsStringItem;
    }//GEN-END:MVDGetEnd13

    /** This method returns instance for snapshotEncodingsStringItem component and should be called instead of accessing snapshotEncodingsStringItem field directly.//GEN-BEGIN:MVDGetBegin14
     * @return Instance for snapshotEncodingsStringItem component
     */
    public StringItem get_snapshotEncodingsStringItem() {
        if (snapshotEncodingsStringItem == null) {//GEN-END:MVDGetBegin14
            // Insert pre-init code here
            snapshotEncodingsStringItem = new StringItem("Snapshot encodings", "");//GEN-LINE:MVDGetInit14
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd14
        return snapshotEncodingsStringItem;
    }//GEN-END:MVDGetEnd14

    /** This method returns instance for vendorStringItem component and should be called instead of accessing vendorStringItem field directly.//GEN-BEGIN:MVDGetBegin15
     * @return Instance for vendorStringItem component
     */
    public StringItem get_vendorStringItem() {
        if (vendorStringItem == null) {//GEN-END:MVDGetBegin15
            // Insert pre-init code here
            vendorStringItem = new StringItem("Vendor", "");//GEN-LINE:MVDGetInit15
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd15
        return vendorStringItem;
    }//GEN-END:MVDGetEnd15

    /** This method returns instance for swVersionStringItem component and should be called instead of accessing swVersionStringItem field directly.//GEN-BEGIN:MVDGetBegin16
     * @return Instance for swVersionStringItem component
     */
    public StringItem get_swVersionStringItem() {
        if (swVersionStringItem == null) {//GEN-END:MVDGetBegin16
            // Insert pre-init code here
            swVersionStringItem = new StringItem("Software version", "");//GEN-LINE:MVDGetInit16
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd16
        return swVersionStringItem;
    }//GEN-END:MVDGetEnd16

    /** This method returns instance for jsr120StringItem component and should be called instead of accessing jsr120StringItem field directly.//GEN-BEGIN:MVDGetBegin17
     * @return Instance for jsr120StringItem component
     */
    public StringItem get_jsr120StringItem() {
        if (jsr120StringItem == null) {//GEN-END:MVDGetBegin17
            // Insert pre-init code here
            jsr120StringItem = new StringItem("JSR 120 - WMA", "");//GEN-LINE:MVDGetInit17
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd17
        return jsr120StringItem;
    }//GEN-END:MVDGetEnd17
    
    public void startApp() {
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
}
