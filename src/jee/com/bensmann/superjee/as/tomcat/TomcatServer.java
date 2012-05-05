/*
 * TomcatServer.java
 *
 * Created on 13. Dezember 2006, 17:26
 *
 */

package com.bensmann.superjee.as.tomcat;

import com.bensmann.superjee.as.tomcat.exception.DeployException;
import com.bensmann.superjee.as.tomcat.exception.TomcatException;
import java.io.File;
import java.net.InetAddress;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Host;

/**
 *
 * @author rb
 */
public interface TomcatServer {
    
    /**
     * 
     * @param tomcatHome 
     */
    public void setTomcatHome(File tomcatHome);
    
    /**
     * 
     * @param webApplicationHome 
     */
    public void setWebApplicationHome(File webApplicationHome);
    
    /**
     * 
     * @param inetAdress 
     */
    public void setInetAddress(InetAddress[] inetAdress);
    
    /**
     * 
     * @param port 
     */
    public void setPort(int port);
    
    /**
     * 
     * @param hostName 
     * @param relativePath 
     * @return 
     */
    public Host createVirtualHost(String hostName, String relativePath);
    
    /**
     * 
     * @param hostName 
     * @return 
     */
    public Host createDefaultVirtualHost(String hostName);
    
    /**
     * 
     * @return 
     */
    public Host activateDefaultVirtualHost();
    
    /**
     * 
     * @return 
     */
    public Host getDefaultVirtualHost();
    
    /**
     * 
     * @param hostName 
     * @return 
     */
    public Host getVirtualHost(String hostName);
    
    /**
     * 
     * @param relativePath 
     * @return 
     */
    public Context createRootContext(String relativePath);
    
    /**
     * 
     * @param host 
     * @return 
     */
    public Context activateDefaultRootContext(Host host);
    
    /**
     * 
     * @param relativePath 
     * @return 
     */
    public Context createManagerContext(String relativePath);
    
    /**
     * 
     * @param host 
     * @return 
     */
    public Context activateDefaultManagerContext(Host host);
    
    /**
     * 
     * @param inetAddress 
     * @param port 
     * @throws com.bensmann.jas.tomcat.exception.TomcatException 
     * @return 
     */
    public Connector createHttpConnector(InetAddress inetAddress, int port)
    throws TomcatException;
    
    public void initializeTomcat();
    
    /**
     * 
     * @throws com.bensmann.jas.tomcat.exception.TomcatException 
     */
    public void startTomcat() throws TomcatException;
    
    /**
     * 
     * @throws com.bensmann.jas.tomcat.exception.TomcatException 
     */
    public void stopTomcat() throws TomcatException;
    
    /**
     * 
     * @param host 
     * @param tomcatContextRoot 
     * @param file 
     * @throws com.bensmann.jas.tomcat.exception.DeployException 
     * @return 
     */
    public Context registerContext(Host host, String tomcatContextRoot,
            File file)
            throws DeployException;
    
    /**
     * 
     * @param host 
     * @param tomcatContextRoot 
     * @param file 
     * @throws com.bensmann.jas.tomcat.exception.DeployException 
     * @return 
     */
    public Context reregisterContext(Host host, String tomcatContextRoot,
            File file)
            throws DeployException;
    
    /**
     * 
     * @param host 
     * @param file 
     * @throws com.bensmann.jas.tomcat.exception.DeployException 
     * @return 
     */
    public Context registerRootContext(Host host, File file)
    throws DeployException;
    
    /**
     * 
     * @param host 
     * @param tomcatContextRoot 
     * @throws com.bensmann.jas.tomcat.exception.DeployException 
     */
    public void unregisterContext(Host host, String tomcatContextRoot)
    throws DeployException;
    
    /**
     * 
     * @param host 
     * @throws com.bensmann.jas.tomcat.exception.DeployException 
     */
    public void unregisterRootContext(Host host) throws DeployException;
    
}
