/*
 * Created on 09.02.2004
 *
 */
package com.bensmann.superframe.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Easy create a ZipUtil file:
 *
 * <p>
 *
 * <pre>
 * ZipUtil z = new ZipUtil("file.zip");
 * z.addFile(String, File);
 * z.create();
 * </pre>
 * </p>
 *
 * @author rb
 * @version $Id: ZipUtil.java,v 1.1 2005/07/19 15:51:39 rb Exp $
 */
public class ZipUtil {
    
    /**
     *
     */
    private String zipFileName;
    /**
     *
     */
    private File zipFile;
    /**
     *
     */
    HashMap files = new HashMap();
    
    /**
     * Constructor
     *
     * @param zipFileName
     *            Name of the resulting zip archive
     */
    public ZipUtil(String zipFileName) {
        this.zipFileName = zipFileName;
    }
    
    /**
     * Constructor
     *
     * @param zipFile
     */
    public ZipUtil(File zipFile) {
        this.zipFile = zipFile;
    }
    
    /**
     * Add a file to archive
     *
     * @param fileName
     * @param file
     */
    public void addFile(String fileName, File file) {
        files.put(fileName, file);
    }
    
    /**
     * Add a file to archive
     *
     * @param fileName
     * @param fin
     * @throws IOException
     */
    public void addFile(String fileName, FileInputStream fin)
    throws IOException {
        
        files.put(fileName, fin);
        
    }
    
    /**
     * Create the archive
     *
     * @throws IOException
     */
    public void create() throws IOException {
        
        ZipOutputStream zout =
                new ZipOutputStream(new FileOutputStream(new File(zipFileName)));
        FileInputStream fin;
        
        Iterator i = files.entrySet().iterator();
        while (i.hasNext()) {
            
            Map.Entry e = (Map.Entry) i.next();
            String fn = (String) e.getKey();
            Object fc = (Object) e.getValue();
            
            zout.putNextEntry(new ZipEntry(fn));
            
            if (fc instanceof FileInputStream)
                fin = (FileInputStream) fc;
            else if (fc instanceof File)
                fin = new FileInputStream(fn);
            else
                continue;
            
            byte[] b = new byte[512];
            int len = 0;
            
            while ((len = fin.read(b)) > 0)
                zout.write(b, 0, len);
            
            fin.close();
            zout.closeEntry();
            
        }
        
        zout.close();
        
    }
    
    /**
     * Get a file from Zip archive and save it into the filesystem
     *
     * @param fileName
     * @return @throws
     *         IOException
     */
    public File getFile(String fileName) throws IOException {
        
        int len = 512;
        byte[] b = new byte[len];
        int bytesRead;
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        File file = new File(fileName);
        FileOutputStream fout = new FileOutputStream(file);
        
        while (zin.available() > 0) {
            
            ZipEntry ze = zin.getNextEntry();
            if (ze.getName().equals(fileName)) {
                while (true) {
                    bytesRead = zin.read(b, 0, len);
                    if (bytesRead == -1)
                        break;
                    fout.write(b, 0, bytesRead);
                }
            }
            
            fout.close();
            
        }
        
        return file;
        
    }
    
    /**
     * Remove file from file list
     *
     * @param fileName
     */
    public void removeFile(String fileName) {
        files.remove(fileName);
    }
    
    /**
     *
     * @return
     * @param filename
     * @throws java.io.IOException
     */
    public ByteArrayOutputStream getFileAsByteArrayOutputStream(File file)
    throws IOException {
        
        InputStream in = null;
        String tmpFilename = null;
        OutputStream out = null;
        byte[] buf = new byte[1024 * 8];
        int len = 0;
        
        try {
            
            // Open the file
            in = new FileInputStream(file);
            
            // Open the output byte array
            out = new ByteArrayOutputStream();
            
            // Transfer bytes from the ZIP file to the output file
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            
            // Close the streams
            out.close();
            in.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return (ByteArrayOutputStream) out;
        
    }
    
    /**
     *
     * @param filename
     * @return
     */
    public byte[] getFileAsByteArray(File file)
    throws IOException {
        
        return getFileAsByteArrayOutputStream(file).toByteArray();
        
    }
    
    /**
     *
     * @return
     * @param filename
     * @throws java.io.IOException
     */
    public ByteArrayOutputStream getFileAsByteArrayOutputStreamFromZip(
            File zipFile, String filename)
            throws IOException {
        
        ZipInputStream in = null;
        String tmpFilename = null;
        OutputStream out = null;
        byte[] buf = new byte[1024 * 8];
        int len = 0;
        
        try {
            
            // Open the ZIP file
            in = new ZipInputStream(new FileInputStream(zipFile));
            
            // Look for ZIP entry
            tmpFilename = in.getNextEntry().getName();
            while (tmpFilename != null &&
                    !tmpFilename.toLowerCase().equals(filename.toLowerCase())) {
                
                tmpFilename = in.getNextEntry().getName();
                
            }
            
            // Open the output byte array
            out = new ByteArrayOutputStream();
            
            // Transfer bytes from the ZIP file to the output file
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            
            // Close the streams
            out.close();
            in.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return (ByteArrayOutputStream) out;
        
    }
    
    /**
     *
     * @param filename
     * @return
     */
    public byte[] getFileAsByteArrayFromZip(File zipFile, String filename)
    throws IOException {
        
        return getFileAsByteArrayOutputStreamFromZip(zipFile, filename).
                toByteArray();
        
    }
    
    /**
     *
     * @param filename
     * @throws java.io.IOException
     * @return
     */
    public char[] getFileAsCharArrayFromZip(File zipFile, String filename)
    throws IOException {
        
        return new String(
                getFileAsByteArrayFromZip(zipFile, filename)).toCharArray();
        
    }

}