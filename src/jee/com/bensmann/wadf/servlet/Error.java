/*
 * Created on 03.11.2003
 *
 */
package com.bensmann.wadf.servlet;

import com.bensmann.superframe.java.Debug;
import com.bensmann.wadf.Configuration;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bensmann.wadf.Option;

/**
 * @author rb
 * @version $Id: Error.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *
 *
 */
public class Error extends HttpServlet {
    
    private HttpServletRequest request;
    
    private HttpServletResponse response;
    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        processRequest();
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        processRequest();
        
    }
    
    /**
     * when using an error-page to catch ServletException thrown from a servlet
     * the implicit JSP object 'exception' is not initialised because it is
     * looking for an attribute called 'javax.servlet.jsp.jspException' which
     * has not been set (the attribute 'javax.servlet.error.exception' is set,
     * however, but is not checked).
     *
     * this scenario does not happen if the exception is thrown from a JSP -
     * 'javax.servlet.jsp.jspException' is set as expected, as is
     * 'javax.servlet.error.exception'.
     *
     * for example, these are the attributes available in the error-page when an
     * exception is thrown from a servlet:
     * <p> javax.servlet.error.message = dang
     * <p> javax.servlet.error.exception = javax.servlet.ServletException: dang
     * <p> javax.servlet.error.servlet_name = S1
     * <p> javax.servlet.error.request_uri = /test/servlet/S1
     * <p> javax.servlet.error.exception_type = class
     * <p> javax.servlet.ServletException
     *
     * while these are the attributes when the exception is thrown from a JSP:
     * <p> javax.servlet.error.message = damn
     * <p> javax.servlet.error.exception = javax.servlet.ServletException: damn
     * <p> javax.servlet.jsp.jspException = javax.servlet.ServletException: damn
     * <p> javax.servlet.error.servlet_name = jsp
     * <p> javax.servlet.error.request_uri = /test/damn.jsp
     * <p> javax.servlet.error.exception_type = class
     * <p> javax.servlet.ServletException
     *
     * my work-around is to explicitly check for the
     * 'javax.servlet.error.exception' in my error-page. the real fix should be
     * done in 'jasper/src/share/org/apache/jasper/runtime/PageContextImpl.java'
     */
    public void processRequest() {
        
        // Get exception from request
        Throwable t = (Throwable) request.getAttribute(
                "javax.servlet.error.exception");
        
        // Initialize string with error message from exception
        String s = t.getMessage();
        
//        // Display request URI
//        if (t != null) {
//            
//            s += " (" +
//                    (String) request.
//                    getAttribute("javax.servlet.error.request_uri") +
//                    ")";
//            
//            //s += " Type: " +
//            // (String) request.getAttribute("javax.servlet.error.exception");
//            
//        }
        
        // Try to display the root cause (the initial error)
        boolean rootcausefound = false;
        Option cd = Configuration.getInstance().getOption("coredebug");
        ServletException e = null;
        
        try {
            e = (ServletException) request.getAttribute(
                    "javax.servlet.error.exception");
        } catch (Exception e2) {
        }
        
        if (cd != null && cd.getValue().equalsIgnoreCase("true")) {
            
            if (e != null) {
                
                if (e.getRootCause() != null)
                    t = e.getRootCause();
                
                s += "<h3>StackTrace (RootCause)</h3>";
                s += "<pre>";
                
                StackTraceElement[] stes = t.getStackTrace();
                for (int i = 0; i < stes.length; i++) {
                    StackTraceElement ste = stes[i];
                    s += ste.toString() + "\n";
                }
                
                s += "</pre>";
                
                rootcausefound = true;
                
            }
            
            // if no initial error present, display the normal throwable error
            if (t != null && !rootcausefound) {
                
                while (t.getCause() != null) {
                    t = t.getCause();
                }
                
                s += "<h3>StackTrace</h3>";
                s += "<pre>";
                
                StackTraceElement[] stes = t.getStackTrace();
                for (int i = 0; i < stes.length; i++) {
                    StackTraceElement ste = stes[i];
                    s += ste.toString() + "\n";
                }
                
                s += "</pre>";
                
            }
            
        }
        
        // Forward to error page
        errorPage(s);
        
    }
    
    /**
     * Forward to error page or print generated error message when no error page
     * does exist
     *
     * @param request
     * @param response
     * @param message
     */
    public void errorPage(String message) {
        
        // Debug
        Debug.log("Setting message: " + message);
        
        request.getSession().setAttribute("qMessage", message);
        
        try {
            
            // Debug
            Debug.log("Forwarding to error page /error.jsp");
            
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            
            Debug.log("No /error.jsp found. Using generated error message");
            
            try {
                response.getWriter().print(message);
            } catch (IOException e2) {
                
                // Debug
                Debug.log("Sorry, output of generated error message failed: " +
                        e2.getMessage());
                
            }
            
        }
        
    }
    
}