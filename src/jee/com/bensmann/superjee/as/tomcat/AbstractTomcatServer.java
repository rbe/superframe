/*
 * AbstractTomcatServer.java
 *
 * Created on 15. Dezember 2006, 13:44
 *
 */

package com.bensmann.superjee.as.tomcat;

import com.bensmann.superjee.as.tomcat.exception.DeployException;
import com.bensmann.superjee.as.tomcat.exception.TomcatException;
import java.io.File;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.realm.MemoryRealm;
import org.apache.catalina.startup.Embedded;

/**
 *
 * @author rb
 */
public class AbstractTomcatServer implements TomcatServer {
    
    private File tomcatHome;
    
    private int port;
    
    private InetAddress[] inetAddress;
    
    private File webApplicationHome;
    
    private Embedded embedded;
    
    private MemoryRealm memoryRealm;
    
    private Engine engine;
    
    private Host defaultVirtualHost;
    
    private List<Host> virtualHost;
    
    private Context rootContext;
    
    private Context managerContext;
    
    /**
     * Creates a new instance of AbstractTomcatServer
     */
    public AbstractTomcatServer() {
        virtualHost = new LinkedList<Host>();
    }
    
    /**
     *
     * @return
     */
    public Context activateDefaultManagerContext(Host host) {
        
        createManagerContext("/webapps/manager");
        host.addChild(managerContext);
        
        return managerContext;
        
    }
    
    
    /**
     *
     * @return
     */
    public Context activateDefaultRootContext(Host host) {
        
        rootContext = createDefaultRootContext();
        host.addChild(rootContext);
        
        return rootContext;
        
    }
    
    
    /**
     *
     * @return
     */
    public Host activateDefaultVirtualHost() {
        
        defaultVirtualHost = createDefaultVirtualHost("localhost");
        engine.addChild(defaultVirtualHost);
        
        return defaultVirtualHost;
        
    }
    
    
    /**
     *
     * @return
     */
    protected Context createDefaultRootContext() {
        return createRootContext("/webapps/ROOT");
    }
    
    
    /**
     *
     * @param hostName
     * @return
     */
    public Host createDefaultVirtualHost(String hostName) {
        return createVirtualHost(hostName, "/webapps");
    }
    
    
    /**
     * Create "Tomcat manager" application Context
     *
     * @param relativePath
     * @return
     */
    public Context createManagerContext(String relativePath) {
        
        managerContext = embedded.createContext(
                "/manager", tomcatHome.getAbsolutePath()  + relativePath);
        managerContext.setPrivileged(true);
        
        return managerContext;
        
    }
    
    
    /**
     *
     * @param relativePath
     * @return
     */
    public Context createRootContext(String relativePath) {
        
        Context rootContext = null;
        
        rootContext = embedded.createContext(
                "", tomcatHome.getAbsolutePath()  + relativePath);
        rootContext.setReloadable(false);
        rootContext.addWelcomeFile("index.jsp");
        
        return rootContext;
        
    }
    
    
    /**
     * Create a virtual host
     *
     * @param Host
     * @return
     */
    public Host createVirtualHost(String hostName, String relativePath) {
        
        Host host = null;
        
        host = embedded.createHost(
                hostName, tomcatHome.getAbsolutePath()  + relativePath);
        virtualHost.add(host);
        
        return host;
        
    }
    
    /**
     *
     * @return
     */
    public Host getDefaultVirtualHost() {
        return defaultVirtualHost;
    }
    
    /**
     *
     * @param hostName
     * @return
     */
    public Host getVirtualHost(String hostName) {
        
        Host foundHost = null;
        
        if (defaultVirtualHost.getName().equals(hostName)) {
            foundHost = defaultVirtualHost;
        }  else {
            
            for(Host host : virtualHost) {
                
                if (host.getName().equals(hostName)) {
                    foundHost = host;
                    break;
                }
                
            }
            
        }
        
        return foundHost;
    }
    
    
    /**
     *
     */
    public void initializeTomcat() {
        
        // Create an embedded server
        embedded = new Embedded();
        embedded.setCatalinaHome(tomcatHome.getAbsolutePath());
        
        // Set the memory realm
        memoryRealm = new MemoryRealm();
        embedded.setRealm(memoryRealm);
        
        // Create an engine
        engine = embedded.createEngine();
        engine.setDefaultHost("localhost");
        
        // Install the assembled container hierarchy
        embedded.addEngine(engine);
        
    }
    
    
    /**
     * Registers a WAR with the container.
     *
     * @param host
     * @param file
     * @param contextPath the context path under which the application will be registered
     * @return
     */
    public Context registerContext(Host host, String contextPath, File file)
    throws DeployException {
        
        Context context = null;
        
        context = embedded.createContext(contextPath, file.getAbsolutePath());
        context.setReloadable(false);
        
        try {
            host.addChild(context);
        } catch (IllegalArgumentException e) {
            throw new DeployException( "Cannot register context '" +
                    contextPath + "' (already deployed?)", e);
        }
        
        return context;
        
    }
    
    /**
     * Registers a WAR with the container.
     *
     * @param host
     * @param file
     * @param contextPath the context path under which the application will be registered
     * @return
     */
    public Context reregisterContext(Host host, String contextPath, File file)
    throws DeployException {
        
        Context context = null;
        
        unregisterContext(host, contextPath);
        context = registerContext(host, contextPath, file);
        
        return context;
        
    }
    
    /**
     *
     * @return
     * @param host
     * @param file
     * @throws com.bensmann.jas.tomcat.exception.DeployException
     */
    public Context registerRootContext(Host host, File file)
    throws DeployException {
        
        return registerContext(host, "", file);
        
    }
    
    /**
     *
     * @param host
     * @throws com.bensmann.jas.tomcat.exception.DeployException
     */
    public void unregisterRootContext(Host host) throws DeployException {
        unregisterContext(host, "");
    }
    
    /**
     * Unregisters a WAR from the web server.
     *
     * @param contextPath the context path to be removed
     */
    public void unregisterContext(Host host, String contextPath)
    throws DeployException {
        
        Context context = host.map(contextPath);
        
        if (context != null) {
            embedded.removeContext(context);
        }  else {
            throw new DeployException("Context " + contextPath + " does not exist");
        }
        
    }
    
    /**
     * Set Session scope variable
     *
     * @param name
     * Session variable name
     * @param obj
     * Session variable value
     */
    public void setRootContextSessionAttribute(String name, Object obj) {
        
        Session sessions[] = rootContext.getManager().findSessions();
        
        for (int i = 0,  size = sessions.length; i < size; i++) {
            sessions[i].getSession().setAttribute(name, obj);
        }
        
    }
    
    /**
     * Set Application scope variable
     *
     * @param name
     * Application variable name
     * @param obj
     * Application variable value
     */
    public void setRootContextAttribute(String name, Object obj) {
        rootContext.getServletContext().setAttribute(name, obj);
    }
    
    /**
     * Get Application scope variable
     *
     * @param name
     * Application variable name
     * @return Application variable value
     */
    public Object getRootContextAttribute(String name) {
        return rootContext.getServletContext().getAttribute(name);
    }
    
    /**
     * Remove Application scope variable
     *
     * @param name
     * Application variable name
     */
    public void removeRootContextAttribute(String name) {
        rootContext.getServletContext().removeAttribute(name);
    }
    
    /**
     *
     * @param inetAddress
     */
    public void setInetAddress(InetAddress[] inetAddress) {
        this.inetAddress = inetAddress;
    }
    
    /**
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /**
     *
     * @param tomcatHome
     */
    public void setTomcatHome(File tomcatHome) {
        this.tomcatHome = tomcatHome;
    }
    
    /**
     *
     * @param webApplicationHome
     */
    public void setWebApplicationHome(File webApplicationHome) {
        this.webApplicationHome = webApplicationHome;
    }
    
    /**
     *
     * @param inetAddress
     * @param port
     * @throws com.bensmann.jas.tomcat.exception.TomcatException
     * @return
     */
    public Connector createHttpConnector(InetAddress inetAddress, int port)
    throws TomcatException {
        
        Connector connector = null;
        
        //        embedded.createConnector(...)
        //        seems to be broken.. it always returns a null connector.
        //        see work around below
        //        embedded.createConnector(addr, port, false);
        
        try {
            
            connector = new Connector();
            connector.setScheme("http");
            connector.setSecure(false);
            connector.setProperty("address", inetAddress.getHostAddress());
            connector.setProperty("port", "" + port);
            connector.setEnableLookups(false);
            
        }  catch (Exception ex) {
            ex.printStackTrace();
            throw new TomcatException(ex.getMessage(), ex);
        }
        
        return connector;
        
    }
    
    /**
     * This method Starts the Tomcat server.
     * @throws com.bensmann.jas.tomcat.exception.TomcatException
     */
    public void startTomcat() throws TomcatException {
        
        Connector connector = createHttpConnector(inetAddress[0], port);
        
        try {
            // Add connector to embedded server
// TODO           embedded.addConnector(connector);
            // Start the embedded server
            embedded.start();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
            throw new TomcatException("Could not start Tomcat", ex);
        }
        
    }
    
    /**
     * This method stops the Tomcat server.
     */
    public void stopTomcat() throws TomcatException {
        
        try {
            embedded.stop();
        }  catch (LifecycleException ex) {
            ex.printStackTrace();
            throw new TomcatException(ex.getMessage(), ex);
        }
        
    }

}
