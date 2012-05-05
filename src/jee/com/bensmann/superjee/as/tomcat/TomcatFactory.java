/*
 * TomcatFactory.java
 *
 * Created on 13. Dezember 2006, 17:26
 *
 */

package com.bensmann.superjee.as.tomcat;

import com.bensmann.superjee.as.tomcat.exception.TomcatException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.deploy.ErrorPage;

/**
 *
 * @author rb
 */
public class TomcatFactory {
    
    /**
     * Home directory of Tomcat installation
     */
    private static File tomcatHome;
    
    /**
     *
     */
    private static InetAddress inetAddress;
    
    /**
     *
     */
    private static int port;
    
    /**
     *
     */
    static {
        
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        port = 8080;
        
    }
    
    /**
     * Creates a new instance of TomcatFactory
     */
    private TomcatFactory() {
    }
    
    /**
     *
     * @param tomcatHome
     */
    public static void setTomcatHome(File tomcatHome) {
        TomcatFactory.tomcatHome = tomcatHome;
    }
    
    /**
     *
     * @param tomcatServerClass
     * @param inetAddress
     * @param tomcatHome
     * @throws java.net.UnknownHostException
     * @return
     */
    public static TomcatServer createMinimalTomcatServer(
            Class tomcatServerClass, File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        TomcatServer tomcatServer = null;
        
        try {
            
            tomcatServer = (TomcatServer) tomcatServerClass.newInstance();
            
            tomcatServer.setTomcatHome(tomcatHome);
            tomcatServer.setWebApplicationHome(new File(tomcatHome, "/webapps"));
            tomcatServer.setInetAddress(new InetAddress[] { inetAddress });
            tomcatServer.setPort(port);
            
            tomcatServer.initializeTomcat();
            
            tomcatServer.activateDefaultVirtualHost();
            
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        return tomcatServer;
        
    }
    
    /**
     *
     * @param tomcatServerClass
     * @param inetAddress
     * @param tomcatHome
     * @throws java.net.UnknownHostException
     * @return
     */
    public static TomcatServer createMinimalTomcatServer(
            Class tomcatServerClass,
            InetAddress inetAddress, File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        TomcatFactory.inetAddress = inetAddress;
        
        return createMinimalTomcatServer(tomcatServerClass, tomcatHome);
        
    }
    
    /**
     *
     * @param tomcatServerClass
     * @param inetAddress
     * @param tomcatHome
     * @throws java.net.UnknownHostException
     * @return
     */
    public static TomcatServer createMinimalTomcatServer(
            Class tomcatServerClass,
            InetAddress inetAddress, int port,
            File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        TomcatServer tomcatServer = null;
        
        TomcatFactory.inetAddress = inetAddress;
        TomcatFactory.port = port;
        
        return createMinimalTomcatServer(tomcatServerClass, tomcatHome);
        
    }
    
    /**
     *
     * @return
     * @param inetAddress
     * @param tomcatServerClass
     * @param tomcatHome
     * @throws com.bensmann.jas.tomcat.exception.TomcatException
     * @throws java.net.UnknownHostException
     */
    public static TomcatServer createStandardTomcatServer(
            Class tomcatServerClass,
            InetAddress inetAddress, File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        TomcatServer tomcatServer = null;
        Host defaultVirtualHost = null;
        
        TomcatFactory.inetAddress = inetAddress;
        
        tomcatServer =
                createMinimalTomcatServer(tomcatServerClass, tomcatHome);
        
        defaultVirtualHost = tomcatServer.getDefaultVirtualHost();
        
        tomcatServer.activateDefaultRootContext(defaultVirtualHost);
        tomcatServer.activateDefaultManagerContext(defaultVirtualHost);
        
        return tomcatServer;
        
    }
    
    /**
     *
     * @return
     * @param inetAddress
     * @param tomcatServerClass
     * @param tomcatHome
     * @throws com.bensmann.jas.tomcat.exception.TomcatException
     * @throws java.net.UnknownHostException
     */
    public static TomcatServer createStandardTomcatServer(
            Class tomcatServerClass,
            InetAddress inetAddress, int port,
            File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        TomcatServer tomcatServer = null;
        Host defaultVirtualHost = null;
        
        TomcatFactory.inetAddress = inetAddress;
        TomcatFactory.port = port;
        
        tomcatServer =
                createMinimalTomcatServer(tomcatServerClass, tomcatHome);
        
        defaultVirtualHost = tomcatServer.activateDefaultVirtualHost();
        tomcatServer.activateDefaultRootContext(defaultVirtualHost);
        tomcatServer.activateDefaultManagerContext(defaultVirtualHost);
        
        return tomcatServer;
        
    }
    
    /**
     *
     * @param tomcatHome
     * @return
     */
    public static TomcatServer createMinimalTomcat55ServerOnLocalhost(
            int port, File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        return createMinimalTomcatServer(
                Tomcat55.class, InetAddress.getLocalHost(), port, tomcatHome);
        
    }
    
    /**
     *
     * @param tomcatHome
     * @return
     */
    public static TomcatServer createMinimalTomcat55ServerOnLocalhost(
            File tomcatHome)
            throws UnknownHostException, TomcatException {
        
        return createMinimalTomcatServer(Tomcat55.class, tomcatHome);
        
    }
    
    /**
     *
     * @param args
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args)
    throws UnknownHostException, TomcatException {
        
        File tomcatHome = null;
        File rootFile = null;
        TomcatServer t = null;
        Host h = null;
        Context ctx = null;
        ErrorPage error404 = null;
        
        tomcatHome = new File("C:/workspace/netbeans/Apache/apache-tomcat-5.5.20-embed");
        rootFile = new File(tomcatHome, "/webapps/ROOT");
        
        t = TomcatFactory.createMinimalTomcat55ServerOnLocalhost(tomcatHome);
        h = t.getDefaultVirtualHost();
        
        t.startTomcat();
        ctx = t.registerRootContext(h, rootFile);
//        error404 = new ErrorPage();
//        error404.setErrorCode("404");
//        error404.setErrorCode(404);
//        error404.setLocation("/blabla");
//        ctx.addErrorPage(error404);
        
    }
    
}
