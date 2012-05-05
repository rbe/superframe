/*
 * com/bensmann/mobile/jsr75/FileHelper.java
 *
 * FileHelper.java created on 3. Februar 2007, 16:38 by rb
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

package com.bensmann.superframe.mobile.jsr75;

import com.bensmann.mobile.jsr135.image.ImageHelper;
import com.bensmann.mobile.exception.Jsr75Exception;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Image;

/**
 *
 * @author rb
 * @version 1.0
 */
public class FileHelper {
    
    /**
     * Creates a new instance of FileHelper
     */
    public FileHelper() {
    }
    
    /**
     *
     * @param fileConnection
     * @throws com.bensmann.mobile.exception.Jsr75Exception
     */
    private static void closeFileConnection(FileConnection fileConnection)
    throws Jsr75Exception {
        
        try {
            fileConnection.close();
            fileConnection = null;
        } catch (IOException e) {
            
            throw new Jsr75Exception("Cannot close file connection to "
                    + fileConnection.getName()
                    + ": " + e.toString());
            
        }
        
    }
    
    /**
     *
     * @param fileConnection
     * @throws com.bensmann.mobile.exception.Jsr75Exception
     * @return
     */
    private static OutputStream openOutputStream(
            FileConnection fileConnection)
            throws Jsr75Exception {
        
        OutputStream out = null;
        
        try {
            out = fileConnection.openOutputStream();
        } catch (IOException e) {
            throw new Jsr75Exception("Cannot open output stream to file "
                    + fileConnection.getName() + ": " + e.toString());
        }
        
        return out;
        
    }
    
    /**
     *
     * @param fileConnection
     * @throws com.bensmann.mobile.exception.Jsr75Exception
     * @return
     */
    private static DataOutputStream openDataOutputStream(
            FileConnection fileConnection)
            throws Jsr75Exception {
        
        DataOutputStream out = null;
        
        try {
            out = fileConnection.openDataOutputStream();
        } catch (IOException e) {
            throw new Jsr75Exception("Cannot open output stream to file "
                    + fileConnection.getName() + ": " + e.toString());
        }
        
        return out;
        
    }
    
    /**
     *
     * @param out
     * @throws com.bensmann.mobile.exception.Jsr75Exception
     */
    private static void closeOutputStream(OutputStream outputStream)
    throws Jsr75Exception {
        
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new Jsr75Exception(
                    "Cannot close output stream: " + e.toString());
        }
        
        outputStream = null;
        
    }
    
    /**
     * <pre>
     * StringBuffer sb = new StringBuffer();
     * String[] files = null;
     * int len = 0;
     * try {
     *      files = FileHelper.listFiles("file:///E:/");
     *      len = files.length;
     *      for (int i = 0; i < len; i++) {
     *          sb.append(files[i] + "##");
     *      }
     *      showAlert("FILES: " + sb.toString());
     * } catch (Exception ex) {
     *      showAlert(ex.toString());
     * }
     * </pre>
     *
     * @return
     * @param directoryName
     * @throws com.bensmann.mobile.exception.Jsr75Exception
     */
    public static String[] listFiles(String directoryName)
    throws Jsr75Exception {
        
        Vector v = new Vector();
        String[] s = null;
        FileConnection fileConnection = null;
        
        try {
            fileConnection = (FileConnection) Connector.
                    open(directoryName, Connector.READ);
        } catch (IOException e) {
            
            throw new Jsr75Exception(
                    "Cannot open directory " + directoryName
                    + ": " + e.toString());
            
        }
        
        if (fileConnection != null) {
            
            Enumeration enumeration = null;
            try {
                enumeration = fileConnection.list();
            } catch (IOException e) {
                throw new Jsr75Exception(
                        
                        "Cannot get files of directory " + directoryName
                        + ": " + e.toString());
                
            }
            
            while (enumeration.hasMoreElements()) {
                v.addElement((String) enumeration.nextElement());
            }
            
            try {
                fileConnection.close();
            } catch (IOException e) {
                
                throw new Jsr75Exception(
                        "Cannot close file connection to " + directoryName
                        + ": " + e.toString());
                
            }
            
            // Copy Vector to String[]
            s = new String[v.size()];
            v.copyInto(s);
            
            // We don't need it anymore
            v = null;
            enumeration = null;
            fileConnection = null;
            
        }
        
        return s;
        
    }
    
    /**
     *
     * @param fileName
     * @throws java.lang.SecurityException
     * @throws javax.microedition.io.ConnectionNotFoundException
     * @throws java.io.IOException
     * @return
     */
    public static FileConnection openFileForReadWrite(String fileName)
    throws Jsr75Exception {
        
        FileConnection fileConnection = null;
        
        try {
            fileConnection = (FileConnection) Connector.open(fileName, Connector.READ_WRITE);
        } catch (IOException e) {
            throw new Jsr75Exception("Cannot open file " + fileName
                    + " for read/write: " + e.toString());
        }
        
        // Check for existance
        if (fileConnection != null) {
            
            if (fileConnection.exists()) {
                
                try {
                    fileConnection.delete();
                } catch (IOException e) {
                    throw new Jsr75Exception("Cannot delete file " + fileName
                            + ": " + e.toString());
                }
                
            }
//        else {
//            fileConnection.mkdir();
//        }
            
            try {
                fileConnection.create();
            } catch (IOException e) {
                throw new Jsr75Exception("Cannot create file " + fileName
                        + ": " + e.toString());
            }
            
        }
        
        return fileConnection;
        
    }
    
    /**
     *
     * @param fileName
     * @param data
     * @throws java.lang.SecurityException
     * @throws javax.microedition.io.ConnectionNotFoundException
     * @throws java.io.IOException
     */
    public static void writeFile(String fileName, byte[] data)
    throws Jsr75Exception {
        
        FileConnection fileConnection = openFileForReadWrite(fileName);
        OutputStream out = openOutputStream(fileConnection);
        
        if (out != null) {
            
            try {
                out.write(data);
                out.flush();
            } catch (IOException e) {
                throw new Jsr75Exception(
                        "Cannot write to output stream for file " + fileName
                        + ": " + e.toString());
            }
            
        }
        
        closeOutputStream(out);
        closeFileConnection(fileConnection);
        
    }
    
    /**
     * TODO does ImageHelper.getByteArray work correctly?
     *
     * @param fileName
     * @param image
     */
    public static void writeFile(String fileName, Image image)
    throws Jsr75Exception {
        
        writeFile(fileName, ImageHelper.getByteArray(image));
        
    }
    
    /**
     *
     * @param fileName
     * @param data
     */
    public static void writeFile(String fileName, boolean[] data)
    throws Jsr75Exception {
        
        FileConnection fileConnection = openFileForReadWrite(fileName);
        DataOutputStream out = openDataOutputStream(fileConnection);
        
        if (out != null) {
            
            try {
                
                for (int i = 0; i < data.length; i++) {
                    out.writeUTF((data[i] == true) ? "t" : "f");
                }
                
                out.flush();
                
            } catch (IOException e) {
                throw new Jsr75Exception(
                        "Cannot write to output stream for file " + fileName
                        + ": " + e.toString());
            }
            
        }
        
        closeOutputStream(out);
        closeFileConnection(fileConnection);
        
    }
    
    /**
     *
     * @param fileName
     * @param data
     */
    public static void writeFile(String fileName, int[] data)
    throws Jsr75Exception {
        
        FileConnection fileConnection = openFileForReadWrite(fileName);
        DataOutputStream out = openDataOutputStream(fileConnection);
        
        for (int i = 0; i < data.length; i++) {
            
            try {
                out.writeInt(data[i]);
                out.writeChars(" ");
            } catch (IOException e) {
                throw new Jsr75Exception(
                        "Cannot write to output stream for file " + fileName
                        + ": " + e.toString());
            }
            
        }
        
        try {
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        closeOutputStream(out);
        closeFileConnection(fileConnection);
        
    }
    
    /**
     *
     * @param fileName
     * @param data
     */
    public static void writeFile(String fileName, String data)
    throws Jsr75Exception {
        
        FileConnection fileConnection = openFileForReadWrite(fileName);
        DataOutputStream out = openDataOutputStream(fileConnection);
        
        try {
            out.writeChars(data);
            out.flush();
        } catch (IOException e) {
            throw new Jsr75Exception(
                    "Cannot write to output stream for file " + fileName
                    + ": " + e.toString());
        }
        
        closeOutputStream(out);
        closeFileConnection(fileConnection);
        
    }
    
}
