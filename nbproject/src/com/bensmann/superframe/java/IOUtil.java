/*
 * Created on May 30, 2003
 *
 */
package com.bensmann.superframe.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

/**
 *
 * @author rb
 * @version $Id: IOUtil.java,v 1.1 2005/07/19 15:51:38 rb Exp $
 */
public class IOUtil {
    
    // TODO: Use Enums
    public static final int LEFT = 1;
    
    public static final int RIGHT = 2;
    
    /** You cannot create an instance */
    private IOUtil() {
    }
    
    /**
     * @param inFileName
     * @param outFileName
     * @throws IOException
     */
    public static void copyFile(String inFileName, String outFileName)
    throws IOException {
        
        File inputFile = new File(inFileName);
        File outputFile = new File(outFileName);
        
        FileReader in = new FileReader(inputFile);
        FileWriter out = new FileWriter(outputFile);
        int c;
        
        while ((c = in.read()) != -1)
            out.write(c);
        
        in.close();
        out.close();
        
    }
    
    /**
     * copy data, streams will not be closed
     */
    public final static void copyStream(InputStream in, OutputStream out,
            int bufSize) throws IOException {
        
        byte buf[] = new byte[bufSize];
        int r;
        while ((r = in.read(buf)) != -1) {
            out.write(buf, 0, r);
        }
    }
    
    /**
     * copy data, streams will be closed after successful copy process
     */
    public final static void copyStream(InputStream in, OutputStream out,
            int bufSize, boolean closeStreams) throws IOException {
        
        byte buf[] = new byte[bufSize];
        int r;
        while ((r = in.read(buf)) != -1) {
            out.write(buf, 0, r);
        }
        
        in.close();
        out.close();
    }
    
    /**
     * Copy an InputStream into a byte[] using a certain buffer size for the
     * copy process.
     *
     * @param in
     *            InputStream that should be read
     * @param bufferSize
     *            Size of buffer for copy process
     */
    public final static byte[] copyStream(InputStream in, int bufferSize)
    throws IOException {
        
        // Buffer for reading InputStream
        byte[] buffer = new byte[bufferSize];
        // Result (all bytes read from InputStream)
        byte[] result = new byte[0];
        byte[] temp = null;
        // Count of read bytes
        int i = 0;
        
        while ((i = in.read(buffer, 0, bufferSize)) != -1) {
            
            // Create new buffer that content is 'existing result + read bytes'
            temp = new byte[result.length + buffer.length];
            if (result.length > 0) {
                System.arraycopy(result, 0, temp, 0, result.length);
            }
            System.arraycopy(buffer, 0, temp, result.length, buffer.length);
            
            // Reassign result
            result = temp;
            
        }
        
        return result;
        
    }
    
    /**
     * Write the content of a byte array into a file at a given URL.
     *
     * @param b
     * @param url
     */
    public final static void writeByteArrayToURL(byte[] b, URL url)
    throws IOException {
        
        File file = null;
        
        try {
            file = new File(new URI("file://" + url.getFile()));
        }
        // URISyntaxException, IllegalArgumentException
        catch (Exception e) {
            file = null;
        }
        
        // Write byte array to file
        if (file != null) {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b);
            fos.close();
        }
        
    }
    
    /**
     * @param input
     * @param orientation
     * @param length
     * @param fillWith
     * @return
     */
    public static String justify(String input, int orientation, int length,
            String fillWith) {
        
        String output = input;
        int lengthOfInput = input.length();
        int lengthDelta = length - lengthOfInput;
        
        String fill = "";
        if (lengthDelta > 0) {
            
            for (int i = 0; i < lengthDelta; i++) {
                fill += fillWith;
            }
            
            if (orientation == LEFT) {
                output += fill;
            } else if (orientation == RIGHT) {
                output = fill + input;
            }
            
        } else {
            output = input.substring(0, length);
        }
        
        return output;
        
    }
    
    /**
     * Write contents of a buffer into a file
     *
     * @param br
     * @param f
     * @throws IOException
     */
    public static void writeBufferToFile(BufferedReader br, File f)
    throws IOException {
        
        String line;
        FileWriter fw = new FileWriter(f);
        
        while ((line = br.readLine()) != null) {
            fw.write(line + "\n");
        }
        
        fw.close();
        
    }
    
}