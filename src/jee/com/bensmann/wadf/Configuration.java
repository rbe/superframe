/*
 * Created on 06.04.2005
 *
 */
package com.bensmann.wadf;


import com.bensmann.superframe.persistence.jdbc.SingleJdbcConnection;
import com.bensmann.superframe.persistence.jdbc.SqlQuery;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Singleton that hold configuration of application
 *
 * @author rb
 * @version $Id: Configuration.java,v 1.1 2005/07/19 12:03:50 rb Exp $
 */
public class Configuration {
    
    /**
     *
     */
    private static Configuration singleton;
    
    /**
     * SAXBuilder to load documents
     */
    private SAXBuilder saxBuilder = new SAXBuilder();
    
    /**
     * Was configuration file (successfully) read?
     */
    private boolean configurationRead;
    
    /**
     *
     */
    public boolean DEBUG;
    
    /**
     * Logging in Tomcat needs to show a timestamp
     */
    public boolean DEBUG_WITH_TIMESTAMP;
    
    /**
     *
     */
    private String basePath;
    
    /**
     * Root element &lt;web-application&gt;
     */
    private Element webApplicationElement;
    
    /**
     *
     */
    private List<?> optionElements;
    
    /**
     * Map for options
     */
    private Map<String, Option> options = new HashMap<String, Option>();
    
    /**
     *
     */
    private List<?> datasourceElements;
    
    /**
     * Map of all datasources
     */
    private Map<String, SingleJdbcConnection> datasources =
            new HashMap<String, SingleJdbcConnection>();
    
    /**
     *
     */
    private Map<String, SqlQuery> sqlQueries  = new HashMap<String, SqlQuery>();
    
    /**
     *
     */
    private List<?> sqlQueryElements;
    
    /**
     *
     */
    private Element sqlDmlElement;
    
    /**
     * Singleton pattern: private constructor
     * @param basePath
     */
    private Configuration(String basePath) {
        this.basePath = basePath;
    }
    
    /**
     * Singleton pattern: get instance
     *
     * @return
     */
    public static Configuration getInstance(String basePath) {
        
        if (singleton == null) {
            singleton = new Configuration(basePath);
            if (!singleton.isConfigurationRead()) {
                singleton.parseConfiguration();
            }
        }
        
        return singleton;
        
    }
    
    /**
     *
     * @return
     */
    public static Configuration getInstance() {
        
        if (singleton != null) {
            return singleton;
        }
        
        return null;
        
    }
    
    /**
     *
     * @return
     */
    public boolean isConfigurationRead() {
        return configurationRead;
    }
    
    /**
     * Read configuration for web application from XML file
     */
    private void parseConfiguration() {
        
        try {
            
            Document configDocument = saxBuilder.build(new File(basePath +
                    "/WEB-INF/waf.xml"));
            Element webApplicationElement = configDocument.getRootElement();
            
            // Get 'first level' elements
            // Options
            Element optionsElement = webApplicationElement.getChild("options");
            // options
            optionElements = (List<?>) optionsElement.getChildren("option");
            // Set debug
            if (getOption("debug").getValue().equalsIgnoreCase("true")) {
                DEBUG = true;
            }
            // Set debug-with-timestamp
            if (getOption("debug-with-timestamp").getValue().
                    equalsIgnoreCase("true")) {
                
                DEBUG_WITH_TIMESTAMP = true;
                
            }
            
            // Datasources
            Element datasourcesElement = webApplicationElement.getChild(
                    "datasources");
            // Child elements of datasources
            datasourceElements = (List<?>) datasourcesElement.
                    getChildren("datasource");
            
            // SQL Queries
            Element sqlQueriesElement = webApplicationElement.getChild(
                    "sql-queries");
            // Child elements of sqlquery
            sqlQueryElements = (List<?>) sqlQueriesElement.
                    getChildren("sqlquery");
            
            // SQL DML elements
            sqlDmlElement = webApplicationElement.getChild("sql-dml");
            
            // Everything was found, so configuration was successfully read
            configurationRead = true;
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * @return 
     */
    public Map<String, SingleJdbcConnection> getAllDatasources() {
        
        boolean dynamic = false;
        
        if (datasources.size() == 0) {
            
            Iterator datasourceElementsIterator = datasourceElements.iterator();
            while (datasourceElementsIterator.hasNext()) {
                
                Element e = (Element) datasourceElementsIterator.next();
                
                String jdbcDriver = e.getAttributeValue("class");
                String jdbcUrl = e.getAttributeValue("url");
                String jdbcUser = e.getAttributeValue("username");
                String jdbcPwd = e.getAttributeValue("password");
                String dynamicAttr = e.getAttributeValue("dynamic");
                if (dynamicAttr != null && dynamicAttr.equalsIgnoreCase("true")) {
                    dynamic = true;
                }
                
                // Create new connection
                SingleJdbcConnection sjc =
                        new SingleJdbcConnection(jdbcDriver, jdbcUrl, jdbcUser,
                        jdbcPwd);
                // Maybe it's a connection for dynamic queries
                sjc.setDynamic(dynamic);
                
                // Put new connection in Map datasources
                datasources.put(e.getAttributeValue("name"), sjc);
                
                
                
            }
            
        }
        
        return datasources;
        
    }
    
    /**
     * 
     * @param name 
     * @return 
     */
    public SingleJdbcConnection getDatasource(String name) {
        
        if (datasources.size() == 0) {
            getAllDatasources();
        }
        
        return datasources.get(name);
        
    }
    
    /**
     * Look for a datasource that is for generated, dynamic queries
     * (see attribute dynamic in tag datasource in waf.xml)
     * @return 
     * @param dynamicQueries 
     */
    public SingleJdbcConnection getDatasource(boolean dynamicQueries) {
        
        SingleJdbcConnection sjc = null;
        
        if (datasources.size() == 0) {
            getAllDatasources();
        }
        
        for (String k : datasources.keySet()) {
            
            sjc = datasources.get(k);
            if (sjc.isDynamic()) {
                break;
            }
            
        }
        
        return sjc;
        
    }
    
    /**
     *
     * @param name
     * @return
     */
    public Option getOption(String name) {
        
        // Parse options when size of map is 0
        if (options.size() == 0) {
            
            Iterator optionElementsIterator = optionElements.iterator();
            while (optionElementsIterator.hasNext()) {
                
                Element e = (Element) optionElementsIterator.next();
                
                options.put(e.getAttributeValue("name"),
                        new Option(e.getAttributeValue("name"),
                        e.getAttributeValue("value")));
                
            }
            
        }
        
        return options.get(name);
        
    }
    
    /**
     *
     * @param name
     * @return
     */
    public SqlQuery getSqlQuery(String name) {
        
        // Parse SQL queries when size of map is 0 -> we have not read any
        // SQL queries
        if (sqlQueries.size() == 0) {
            
            Iterator sqlQueryElementsIterator = sqlQueryElements.iterator();
            while (sqlQueryElementsIterator.hasNext()) {
                
                Element e = (Element) sqlQueryElementsIterator.next();
                String datasource = ((Element) e.getParent()).
                        getAttributeValue("datasource");
                
                // Create new SqlQuery
                SqlQuery sqlQuery = new SqlQuery(getDatasource(datasource),
                        e.getAttributeValue("name"),
                        e.getText());
                
                // Add parameter elements to SqlQuery
                Iterator parameterElementsIterator =
                        e.getChildren("parameter").iterator();
                int i = 1;
                while (parameterElementsIterator.hasNext()) {
                    
                    Element parameter = (Element) parameterElementsIterator.
                            next();
                    
                    sqlQuery.addPreparedStatementParameter(i++,
                            parameter.getAttributeValue("variable"));
                    
                }
                
                // Add SqlQuery to map
                sqlQueries.put(e.getAttributeValue("name"), sqlQuery);
                
            }
            
        }
        
        return sqlQueries.get(name);
        
    }
    
}