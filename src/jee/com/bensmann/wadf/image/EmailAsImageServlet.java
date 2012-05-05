/*
 * ImageQualityServlet.java
 *
 * Created on 19. Oktober 2005, 11:10
 */

package com.bensmann.wadf.image;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * $Header$
 * @author Ralf Bensmann
 * @version $Id$
 * @since $Date$
 * @log $Log$
 */
public final class EmailAsImageServlet extends HttpServlet {
    
    /**
     * HTTP request
     */
    private HttpServletRequest request;
    
    /**
     * HTTP response
     */
    private HttpServletResponse response;
    
    /**
     * Initializer that loads properties from a file.
     * If an exception occurs, it is printed out, but standard values
     * prevent this component from malfunctions
     */
    public void init() {
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest() throws ServletException, IOException {
        
        int emailAddressId = 0;
        String emailAddress = "unknown@company.com";
        String fontName = null;
        int fontSize = 0;
        int fontStyle = Font.PLAIN;
        BufferedImage image = null;
        
        // Intialize response and get writer for page
        response.setContentType("image/jpeg");
        // Use ServletOutputStream for binary output
        ServletOutputStream out = response.getOutputStream();
        
        // Retrieve email address ID from request parameters
        emailAddressId =
                Integer.valueOf(request.getParameter("e")).intValue();
        emailAddress =
                ImageProperties.get("emailobfuscate.address." + emailAddressId);
        fontName = ImageProperties.get(
                "emailobfuscate.address." + emailAddressId + ".font.name");
        fontSize = Integer.valueOf(ImageProperties.get(
                "emailobfuscate.address." + emailAddressId + ".font.size")).
                intValue();
        String fontStyleProperty = ImageProperties.get(
                "emailobfuscate.address." + emailAddressId + ".font.style");
        if (fontStyleProperty.toLowerCase().indexOf("bold") >= 0) {
            fontStyle |= Font.BOLD;
        }
        if (fontStyleProperty.toLowerCase().indexOf("italic") >= 0) {
            fontStyle |= Font.ITALIC;
        }
        
        // Create email address as image
        image = EmailAddressAsImage.drawEmailAddress(emailAddress,
                fontName, fontSize, fontStyle);

        // Create and set a cache directory for ImageIO
        File cacheDirectory = new File(getServletContext().getRealPath("/temp"));
        cacheDirectory.mkdir();
        ImageIO.setCacheDirectory(cacheDirectory);
        // ImageIO can deal with following formats: BMP, WBMP, JPEG, GIF,
        // PNG
        // Write image as JPEG to output stream (will be converted
        // automatically)
        ImageIO.write(image, "jpeg", out);
        
        out.close();
        
    }
    
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        processRequest();
        
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        processRequest();
        
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
// </editor-fold>
}
