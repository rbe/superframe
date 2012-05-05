/*
 * ResourceFinder2.java
 *
 * Created on 13. Dezember 2005, 16:09
 *
 */

package com.bensmann.superjee.servicelocator;

import com.bensmann.superframe.exception.ResourceLoadException;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 * This class is responsible for loading resources (files) for us.  It avoids
 * the complexity of having to figure out exactly where a file lives.  Resources
 * can be retrievd as URLs, Files, InputStreams, and OutputStreams.
 * <p>
 * In the context of a web-application, resources will get loaded relative
 * to the directory in which the servlet context is deployed.  For example,
 * if the web app is deployed in /usr/local/tomcat/webapps/foobar, then
 * getResource("/WEB-INF/conf/foo.conf") will return a handle to
 * /usr/local/tomcat/webapps/foobar/WEB-INF/conf/foo.conf.
 * <p>
 * In the context of a non-web-application, resources will be loaded relative
 * to the classpath.
 * <p>
 * In the context of EJB, resource loading will be delegated to the EJB
 * container (or at least that's what I'm thinking..)
 * <p>
 * During application (or servlet engine) startup, the method
 * setServletContext() must be called to bootstrap the ResourceLoader with
 * the ServletContext.
 */
public final class ResourceFinder2 {
    
    private ServletContext servletContext;
    
    public ResourceFinder2() {
    }
    
    public void setServletContext(ServletContext context) {
        servletContext = context;
    }
    
    /**
     * Get the specified resource as a URL
     */
    public URL getResourceAsUrl(String name) throws ResourceLoadException {
        try {
            URL url = null;
            if (servletContext != null) {
                url = servletContext.getResource(name);
            } else {
                url = getClass().getResource(name);
            }
            if (url == null) {
                throw new ResourceLoadException("No such file or directory: " + name);
            } else {
                return url;
            }
        } catch (MalformedURLException e) {
            throw new ResourceLoadException("Could not load " + name + " for reading", e);
        }
    }
    
    /**
     * Get the specified resource as a File
     */
    public File getResourceAsFile(String name) throws ResourceLoadException {
        return getResourceAsFile(name, false);
    }
    
    public boolean exists(String name) {
        try {
            File f = getResourceAsFile(name, false);
            return (f.exists());
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get the specified resource as a File and possibly ignore a
     * file not found error (such as in the case when you're getting
     * a log file which may not yet exist)
     */
    public File getResourceAsFile(String name, boolean ignoreNotFoundError)
    throws ResourceLoadException {
        ResourceLoadException throwMe = null;
        try {
            URL url = getResourceAsUrl(name);
            if (url != null) {
                File f = new File(url.getFile());
                if (f.exists() || ignoreNotFoundError) {
                    return f;
                } else {
                    throwMe = new ResourceLoadException("No such file or directory: " + name);
                }
            } else {
                throwMe = new ResourceLoadException("No such file or directory: " + name);
            }
        } catch (ResourceLoadException ex) {
            // try another way
            File path = new File(name);
            File f = getResourceAsFile(path.getParent());
            File target = new File(f, path.getName());
            if (target.exists() || ignoreNotFoundError) {
                return target;
            } else {
                throwMe = new ResourceLoadException("No such file or directory: " + name);
            }
        }
        if (throwMe != null) {
            throw throwMe;
        }
        return null; // never reached
    }
    
    /**
     * Get the specified resource as an InputStream.  Caller is responsible for closing the
     * stream.
     */
    public InputStream getResourceAsInputStream(String name) throws ResourceLoadException {
        InputStream is = null;
        if (servletContext != null) {
            is = servletContext.getResourceAsStream(name);
        } else {
            is = getClass().getResourceAsStream(name);
        }
        if (is == null) {
            throw new ResourceLoadException("No such file or directory: " + name);
        } else {
            return is;
        }
    }
    
    public OutputStream getResourceAsOutputStream(String name)
    throws ResourceLoadException {
        return getResourceAsOutputStream(name, true);
    }
    
    /**
     * Get the specified resource as an OutputStream.  Caller is responsible for closing the
     * stream.
     */
    public OutputStream getResourceAsOutputStream(String name, boolean create)
    throws ResourceLoadException {
        try {
            File f = getResourceAsFile(name, create);
            if (f != null) {
                return new FileOutputStream(f);
            } else {
                throw new ResourceLoadException("No such file or directory: " + name);
            }
        } catch (IOException e) {
            throw new ResourceLoadException("Could not load " + name + " for writing", e);
        }
    }
    
    /**
     * Get the specified resource as a Java Properties object.
     */
    public Properties getResourceAsProperties(String name)
    throws ResourceLoadException {
        Properties p = new Properties();
        InputStream is = null;
        boolean success = false;
        try {
            is = getResourceAsInputStream(name);
            p.load(is);
            success = true;
            return p;
        } catch (IOException e) {
            throw new ResourceLoadException("Could not load " + name + " as properties", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    if (!success) {
                        // throw this exception up only if we don't have the properties
                        // file loaded.  otherwise, let's just be silent about it.
                        throw new ResourceLoadException("While closing stream", e);
                    }
                }
            }
        }
    }
}