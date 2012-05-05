/*
 * ColoredBullet.java
 *
 * Created on 19. Oktober 2005, 14:13
 *
 */

package com.bensmann.wadf.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Generate an email address as an image using Java 2D
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public final class EmailAddressAsImage {
    
    /**
     * Do not create a new instance of EmailAddressAsImage
     */
    private EmailAddressAsImage() {
    }
    
    /**
     * Generate a colored bullet on a BufferedImage with 'pixel' width and
     * height and color 'stoplight' (lookep up in class Stoplight)
     *
     * @param pixel
     * @param stoplight
     * @return
     */
    public static final BufferedImage drawEmailAddress(String emailAddress,
            String fontName, int fontSize, int fontStyle) {
        
        // Create BufferedImage and Graphics objects
        BufferedImage bi =
                new BufferedImage(500, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        
        // Use antialiasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // Initialize graphic with white background
        g.setColor(Color.WHITE);
        //
        g.fillRect(0, 0, 500, 200);
        
        // Set font
        Font font = new Font(fontName, fontStyle, fontSize);
        g.setFont(font);
        
        // Determine width and height of email address and modify width and
        // height of image
        FontMetrics metrics = g.getFontMetrics();
        int emailAddressWidth = metrics.stringWidth(emailAddress);
        int emailAddressHeight = metrics.getHeight();
        int ascent = metrics.getAscent();
        int descent = metrics.getDescent();
        
        // Draw string with email address
        g.setColor(Color.BLACK);
        g.drawString(emailAddress, 0, ascent);
        
        g.dispose();
        
        BufferedImage bufferedImage =
                new BufferedImage(emailAddressWidth, emailAddressHeight,
                BufferedImage.TYPE_INT_RGB);
        g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, emailAddressWidth, emailAddressHeight);
        g.drawImage(bi, 0, 0, null);        
        g.dispose();
        
        return bufferedImage;
        
    }
    
}
