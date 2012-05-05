/*
 * OracleDatamodel.java
 *
 * Created on 7. Mai 2006, 11:15
 *
 */

package com.bensmann.superframe.persistance.jdbc;

import com.bensmann.superframe.exceptions.DatamodelException;
import com.bensmann.superframe.java.ClassUtil;
import com.bensmann.superframe.java.Debug;
import com.bensmann.superframe.persistence.annotation.DatamodelZeroNotOk;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * This class reads the schema/datamodel from an exisiting JDBC connection
 * and returns a XML document.
 * <p>
 * Additionally there are methods to directly retrieve informations about
 * certain objects in the datamodel:
 * <ul>
 * <li>Element for a certain table/view or procedure/function</li>
 * <li>List of column names of a table/view</li>
 * <li>List of parameters of a procedure/function</li>
 *</ul>
 * </p>
 *
 * TODO: Cache datamodel at system-wide-level; e.g. store it
 * into config.xml with a timestamp included
 *
 * $Header$
 * @author rb
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
public final class OracleDatamodel {
    
    /**
     * Cache for OracleDatamodel objects per Connection
     */
    private static Map<Connection, OracleDatamodel> cache;
    
    /**
     * XML document that holds the datamodel description
     */
    private Document document;
    
    /**
     * Root element of XML document
     */
    private Element rootElement;
    
    /**
     * JDBC database connection
     */
    private Connection connection;
    
    /**
     * Owner is used when an user/schema other than default by connection
     * should be discovered
     */
    private String owner;
    
    // Static initializer
    static {
        cache = new Hashtable<Connection, OracleDatamodel>();
    }
    
    /**
     * Creates a new instance of OracleDatamodel
     *
     * @param connection A JDBC connection object
     */
    private OracleDatamodel(Connection connection) {
        
        this.connection = connection;
        
        refresh();
        
    }
    
    /**
     *
     * @param connection
     * @return
     */
    public static OracleDatamodel getInstance(Connection connection) {
        
        OracleDatamodel instance = null;
        
        // Lookup instance in cache
        instance = cache.get(connection);
        // If no instance was found, create one and put it into the cache
        if (instance == null) {
            
            System.out.println("Creating new OracleDatamodel instance for " +
                    connection);
            instance = new OracleDatamodel(connection);
            cache.put(connection, instance);
            
        } else {
            System.out.println("Using cached OracleDatamodel instance " +
                    connection);
        }
        
        return instance;
        
    }
    
    /**
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Determines Java type that should be used with certain type, length,
     * precision and scale.
     *
     * @deprecated
     * @param dataType
     * @param dataLength
     * @param dataPrecision
     * @param dataScale
     * @return
     */
    private String getJavaTypeName(String dataType, int dataLength,
            int dataPrecision, int dataScale) {
        
        String javaType = null;
        
        // Determine Java type corresponding to SQL data-type
        if (dataType.indexOf("CHAR") >= 0) {
            
            if (dataLength == 1) {
                javaType = "java.lang.Character";
            } else {
                javaType = "java.lang.String";
            }
            
        } else if (dataType.equals("DATE")) {
            javaType = "java.util.Date";
        } else if (dataType.equals("NUMBER")) {
            javaType = "java.lang.BigDecimal";
        } else {
            javaType = "java.lang.Object";
        }
        
        return javaType;
    }
    
    /**
     *
     * @param dataType
     * @param dataLength
     * @param dataPrecision
     * @param dataScale
     * @return
     */
    private int getJavaTypeId(String dataType, int dataLength,
            int dataPrecision, int dataScale) {
        
        int type = -1;
        
        if (dataType.equals("CHAR")) {
            type = Types.CHAR;
        } else if (dataType.equals("VARCHAR")) {
            type = Types.VARCHAR;
        } else if (dataType.equals("NUMBER")) {
            type = Types.BIGINT;
        }
        
        return type;
        
    }
    
    /**
     *
     *
     * @return
     * @param dataLength
     * @param dataType
     */
    private int correctLength(String dataType, int dataLength) {
        
        // Length of VARCHAR in Oracle is 255, VARCHAR2 has a maximum of
        // 4000 characters
        if (dataType.equals("VARCHAR") && dataLength == -1) {
            dataLength = 255;
        } else if (dataType.equals("VARCHAR2") && dataLength == -1) {
            dataLength = 4000;
        }
        
        return dataLength;
        
    }
    
    /**
     *
     *
     * @return
     * @param dataType
     * @param dataPrecision
     */
    private int correctPrecision(String dataType, int dataPrecision) {
        
        // NUMBER in Oracle is reported with 22 bytes of length and
        // unknown precision and scale, but it's 38 and 0
        if (dataType.equals("NUMBER") && dataPrecision == 0) {
            dataPrecision = 38;
        }
        
        return dataPrecision;
        
    }
    
    /**
     *
     *
     * @return
     * @param dataType
     * @param dataScale
     */
    private int correctScale(String dataType, int dataScale) {
        
        // NUMBER in Oracle is reported with 22 bytes of length and
        // unknown precision and scale, but it's 38 and 0
        if (dataType.equals("NUMBER") && dataScale == -1) {
            dataScale = 0;
        }
        
        return dataScale;
        
    }
    
    /**
     *
     * @param rowLength
     * @param blockSize
     * @return
     */
    private int calculateReads(int rowLength, int blockSize) {
        
        int readCount = 0;
        
        float div = (float) rowLength / (float) blockSize;
        readCount = Math.round(div);
        readCount = (int) div;
        
        if ((div - (int) div) > 0) {
            readCount++;
        }
        if (readCount == 0) {
            readCount = 1;
        }
        
        return readCount;
        
    }
    
    /**
     * Create element for maximum read count depending on bytes.
     *
     * @param bytes
     * @return
     */
    public Element getReadCountElement(int bytes) {
        
        Element readCountElement = new Element("maximum-read-count");
        
        readCountElement.setAttribute("read-count-512-byte",
                "" + calculateReads(bytes, 512));
        readCountElement.setAttribute("read-count-1-kbyte",
                "" + calculateReads(bytes, 1024));
        readCountElement.setAttribute("read-count-2-kbyte",
                "" + calculateReads(bytes, 2 * 1024));
        readCountElement.setAttribute("read-count-4-kbyte",
                "" + calculateReads(bytes, 4 * 1024));
        readCountElement.setAttribute("read-count-8-kbyte",
                "" + calculateReads(bytes, 8 * 1024));
        readCountElement.setAttribute("read-count-16-kbyte",
                "" + calculateReads(bytes, 16 * 1024));
        readCountElement.setAttribute("read-count-32-kbyte",
                "" + calculateReads(bytes, 32 * 1024));
        
        return readCountElement;
        
    }
    
    /**
     *
     * @param tableName
     * @throws java.sql.SQLException
     * @return
     */
    private Map<String, String[]> getMetaDataMap(String tableName)
    throws SQLException {
        
        Map<String, String[]> metaDataMap = new Hashtable<String, String[]>();
        String tmpColumnClassName = null;
        String tmpColumnName = null;
        int tmpColumnTypeId = 0;
        // Query table with dummy query to get result set metadata
        String metaDataQuery =
                "SELECT * FROM " +
                tableName +
                " WHERE 1 = 2";
        Statement metaDataStatement = connection.createStatement();
        ResultSet metaDataResultSet =
                metaDataStatement.executeQuery(metaDataQuery);
        ResultSetMetaData resultSetMetaData = metaDataResultSet.getMetaData();
        
        // Put all information about columns into a map
        for (int i = 1; i < resultSetMetaData.getColumnCount() + 1; i++) {
            
            tmpColumnName = resultSetMetaData.getColumnName(i);
            tmpColumnClassName =
                    resultSetMetaData.getColumnClassName(i);
            tmpColumnTypeId = resultSetMetaData.getColumnType(i);
            
            metaDataMap.put(tmpColumnName, new String[] {
                tmpColumnClassName, "" + tmpColumnTypeId
            });
            
        }
        // Close metadata objects
        metaDataResultSet.close();
        metaDataStatement.close();
        
        return metaDataMap;
        
    }
    
    /**
     * Read information about tables and views and store them into
     * datamodel XML document
     * @throws java.sql.SQLException
     */
    public void tablesAndViews() throws SQLException {
        
        Element tablesElement = new Element("tables");
        Element tableElement = null;
        Element columnElement = null;
        Statement dataDictionaryStatement = null;
        ResultSet dataDictionaryResultSet = null;
        String metaDataQuery = null;
        String allTables =
                "SELECT 'table' type, t.table_name, c.column_name," +
                " c.data_type, c.data_length, c.data_precision, c.data_scale," +
                " c.nullable" +
                "  FROM user_tables t, user_tab_columns c" +
                " WHERE t.table_name = c.table_name" +
                " UNION " +
                "SELECT 'view' type, v.view_name, c.column_name," +
                " c.data_type, c.data_length, c.data_precision, c.data_scale," +
                " c.nullable" +
                "  FROM user_views v, user_tab_columns c" +
                " WHERE v.view_name = c.table_name";
        String actualTableName = null;
        String tmp = "";
        String type = null;
        String tmpType = null;
        String columnName = null;
        Map<String, String[]> metaDataMap = new Hashtable<String, String[]>();
        String[] columnMetaData = null;
        String data_type = null;
        int data_length = 0;
        int data_precision = 0;
        int data_scale = 0;
        String nullable = null;
        int rowLength = 0;
        int allTablesRowLength = 0;
        int tableCount = 0;
        int viewCount = 0;
        int columnCount = 0;
        Integer tmpInteger = null;
        
        dataDictionaryStatement = connection.createStatement();
        dataDictionaryResultSet =
                dataDictionaryStatement.executeQuery(allTables);
        
        // Add tables-element to root
        rootElement.addContent(tablesElement);
        
        while (dataDictionaryResultSet.next()) {
            
            actualTableName = dataDictionaryResultSet.getString("table_name");
            // Type to determine wheter we have a table or view
            type = dataDictionaryResultSet.getString("type");
            // Column metadata
            columnName = dataDictionaryResultSet.getString("column_name");
            data_type = dataDictionaryResultSet.getString("data_type");
            data_length = dataDictionaryResultSet.getInt("data_length");
            data_precision =
                    dataDictionaryResultSet.getInt("data_precision");
            data_scale = dataDictionaryResultSet.getInt("data_scale");
            nullable = dataDictionaryResultSet.getString("nullable");
            
//            // Check length of field
//            if (data_length == 0) {
//                data_length = -1;
//            }
//            if (data_precision == 0) {
//                data_precision = -1;
//            }
//            if (data_scale == 0) {
//                data_scale = -1;
//            }
            // Correct length, precision and scale values
            data_length = correctLength(data_type, data_length);
            data_precision = correctPrecision(data_type, data_precision);
            data_scale = correctScale(data_type, data_scale);
            // Check nullable
            if (nullable.equals("Y")) {
                nullable = "yes";
            } else {
                nullable = "no";
            }
            
            // Count row length of tables
            if (data_length > 0) {
                
                rowLength += data_length;
                
                if (type.equals("table")) {
                    allTablesRowLength += rowLength;
                }
                
            }
            
            // A new table/view?
            if (!actualTableName.equals(tmp)) {
                
                // Add statistics (only if a tableElement exists; for first run)
                if (tableElement != null && tablesElement != null) {
                    tableElement.setAttribute("column-count", "" + columnCount);
                    tableElement.setAttribute("maximum-row-length",
                            "" + rowLength);
                    //
                    if (tmpType != null && tmpType.equals("table")) {
                        tableElement.addContent(getReadCountElement(rowLength));
                    }
                    // Set column-count attribute
                    tablesElement.addContent(tableElement);
                }
                
                // Create new table element
                tableElement = new Element(type);
                tableElement.setAttribute("name", actualTableName);
                
                // Get metadata for table columns
                metaDataMap = getMetaDataMap(actualTableName);
                
                // Reset counters
                rowLength = 0;
                columnCount = 0;
                if (tmpType != null) {
                    if (tmpType.equals("table")) {
                        tableCount++;
                    } else if (tmpType.equals("view")) {
                        viewCount++;
                    }
                }
                
            }
            
            tmp = actualTableName;
            tmpType = type;
            columnMetaData = metaDataMap.get(columnName);
            
            columnElement = new Element("column");
            columnElement.setAttribute("name", columnName);
            if (columnMetaData != null) {
                columnElement.setAttribute("java-type", columnMetaData[0]);
                columnElement.setAttribute("java-type-id", columnMetaData[1]);
            }
            columnElement.setAttribute("data-type", data_type);
            columnElement.setAttribute("data-length", "" + data_length);
            columnElement.setAttribute("data-precision", "" + data_precision);
            columnElement.setAttribute("data-scale", "" + data_scale);
            columnElement.setAttribute("nullable", nullable);
            tableElement.addContent(columnElement);
            
            // Increase column counters
            columnCount++;
            
        }
        
        // Set attribute table count
        tablesElement.setAttribute("table-count", "" + tableCount);
        // Set attribute view count
        tablesElement.setAttribute("view-count", "" + viewCount);
        // Set attribute maximum row length of all tables
        tablesElement.setAttribute("maximum-row-length",
                "" + allTablesRowLength);
        //
        tablesElement.addContent(getReadCountElement(allTablesRowLength));
        
        // Close data dictionary objects
        dataDictionaryResultSet.close();
        dataDictionaryStatement.close();
        
    }
    
    /**
     * Read information about packages and stored procedures and their
     * arguments and store them into datamodel XML document
     * @throws java.sql.SQLException
     */
    public void proceduresAndFunctions() throws SQLException {
        
        Element proceduresElement = new Element("procedures");
        Element procedureElement = null;
        Element argumentElement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String allArguments =
                "SELECT a.package_name, p.procedure_name," +
                " a.argument_name, a.position, a.data_type," +
                " a.data_length, a.data_precision, a.data_scale," +
                " a.in_out" +
                "  FROM user_procedures p, user_arguments a" +
                " WHERE p.procedure_name = a.object_name" +
                " ORDER BY a.package_name, p.procedure_name";
        String actualProcedureName = null;
        String tmp = "";
        String type = null;
        String argumentType = null;
        String argument_name = null;
        String position = null;
        String data_type = null;
        int data_length = 0;
        int data_precision = 0;
        int data_scale = 0;
        String in_out = null;
        String javaType = null;
        int argumentCount = 0;
        int procedureCount = 0;
        int functionCount = 0;
        
        statement = connection.createStatement();
        resultSet = statement.executeQuery(allArguments);
        
        // Add procedures-element to root
        rootElement.addContent(proceduresElement);
        
        while (resultSet.next()) {
            
            actualProcedureName = resultSet.getString("procedure_name");
            argument_name = resultSet.getString("argument_name");
            position = resultSet.getString("position");
            data_type = resultSet.getString("data_type");
            data_length = resultSet.getInt("data_length");
            data_precision = resultSet.getInt("data_precision");
            data_scale = resultSet.getInt("data_scale");
            in_out = resultSet.getString("in_out");
            javaType = getJavaTypeName(data_type, data_length, data_precision,
                    data_scale);
            
            // Check argument name and position; if name is null and
            // position == 0 then it's a function
            if (argument_name == null && position.equals("0")) {
                type = "function";
                argumentType = "return-value";
                argument_name = "";
            } else {
                type = "procedure";
                argumentType = "argument";
            }
            
//            // Check length of field
//            if (data_length == null) {
//                data_length = "unknown";
//            }
//            if (data_precision == null) {
//                data_precision = "unknown";
//            }
//            if (data_scale == null) {
//                data_scale = "unknown";
//            }
            // Correct precision and scale values
            data_length = correctLength(data_type, data_length);
            data_precision = correctPrecision(data_type, data_precision);
            data_scale = correctScale(data_type, data_scale);
            
            // If actual procedure/function name is different from
            // the last one (tmp) then it's a new procedure/function
            if (!actualProcedureName.equals(tmp)) {
                
                if (procedureElement != null && proceduresElement != null) {
                    // Add argument-count attribute
                    procedureElement.setAttribute(
                            "argument-count", "" + argumentCount);
                    proceduresElement.addContent(procedureElement);
                }
                
                procedureElement = new Element(type);
                procedureElement.setAttribute("name", actualProcedureName);
                procedureElement.setAttribute("package-name",
                        resultSet.getString("package_name"));
                
                argumentCount = 0;
                if (type.equals("procedure")) {
                    procedureCount++;
                } else if (type.equals("function")) {
                    functionCount++;
                }
                
            }
            
            // Assign actual procedure name to tmp for comparing it
            // in the next loop
            tmp = actualProcedureName;
            
            argumentElement = new Element(argumentType);
            procedureElement.addContent(argumentElement);
            // Only arguments have a name and position and can be IN or OUT
            // arguments
            // TODO: BUG? If name+position+in-out are in the if,
            // no functions are fonud in XML???
//            if (argumentType.equals("argument")) {
            argumentElement.setAttribute("name", argument_name);
//            }
            argumentElement.setAttribute("position", position);
            argumentElement.setAttribute("in-out", in_out);
            argumentElement.setAttribute("java-type", javaType);
            argumentElement.setAttribute("data-type", data_type);
            argumentElement.setAttribute("data-length", "" + data_length);
            argumentElement.setAttribute("data-precision", "" + data_precision);
            argumentElement.setAttribute("data-scale", "" + data_scale);
            
            // Increase argument counter if it's not a return-value
            if (argumentType.equals("argument")) {
                argumentCount++;
            }
            
        }
        
        proceduresElement.setAttribute("procedure-count", ""+ procedureCount);
        proceduresElement.setAttribute("function-count", "" + functionCount);
        
        resultSet.close();
        statement.close();
        
    }
    
    /**
     *
     * @return
     */
    public Document getDocument() {
        
        if (rootElement.getContentSize() == 0) {
            
            try {
                tablesAndViews();
                proceduresAndFunctions();
            } catch (Exception e) {
                // do nothing
                e.printStackTrace();
            }
            
        }
        
        return document;
        
    }
    
    /**
     * Queries number of tables from XML document using XPath
     *
     * @return Number of tables
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public int getTableCount() throws DatamodelException {
        
        String v = "-1";
        String search = "//datamodel/tables[@table-count]/text()";
        
        try {
            
            v = ((Attribute) XPath.selectSingleNode(document, search)).
                    getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot retrieve table count", e);
        }
        
        return new Integer(v);
        
    }
    
    /**
     * Queries number of views from XML document using XPath
     *
     * @return Number of views
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public int getViewCount() throws DatamodelException {
        
        String value = "-1";
        String search = "//datamodel/tables[@view-count]/text()";
        
        try {
            
            value = ((Attribute) XPath.selectSingleNode(document, search)).
                    getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot retrieve view count", e);
        }
        
        return new Integer(value);
        
    }
    
    /**
     * Queries a certain table element from XML document using XPath
     *
     * @param name
     * @return Table element
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getTableElement(String name) throws DatamodelException {
        
        Element tableElement = null;
        String search = "//datamodel/tables/table[@name='" + name + "']";
        
        try {
            tableElement = (Element) XPath.selectSingleNode(document, search);
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find table element for " +
                    "'" + name + "'", e);
        }
        
        return tableElement;
    }
    
    /**
     * Queries a certain view element from XML document using XPath
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getViewElement(String name) throws DatamodelException {
        
        Element viewElement = null;
        String search = "//datamodel/tables/view[@name='" + name + "']";
        
        try {
            viewElement = (Element) XPath.selectSingleNode(document, search);
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find view element for" +
                    "'" + name + "'", e);
        }
        
        return viewElement;
    }
    
    /**
     * Returns a certain element for a certain procedure
     *
     *
     * @return
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getProcedureElement(String packageName, String name)
    throws DatamodelException {
        
        Element procedureElement = null;
        String search =
                "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + name + "']";
        
        try {
            
            procedureElement =
                    (Element) XPath.selectSingleNode(document, search);
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find procedure element for" +
                    "'" + name + "'", e);
        }
        
        return procedureElement;
    }
    
    /**
     * Returns a certain element for a certain function
     *
     *
     * @return
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public Element getFunctionElement(String packageName, String name)
    throws DatamodelException {
        
        Element functionElement = null;
        String search =
                "//datamodel/procedures/function" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + name + "']";
        
        try {
            
            functionElement =
                    (Element) XPath.selectSingleNode(document, search);
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find view element for " +
                    "'" + name + "'", e);
        }
        
        return functionElement;
        
    }
    
    /**
     * Returns a list of column names for a certain table/view
     *
     * @return
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public String[] getColumnNames(String name) throws DatamodelException {
        
        int i = 0;
        String[] columnNames = null;
        List nodes = null;
        String search = "column/@name";
        
        // Retrieve table or view element
        Element element = getTableElement(name);
        if (element == null) {
            element = getViewElement(name);
        }
        
        // Retrieve all 'column' children and get their name attribute
        try {
            
            nodes = (List) XPath.selectNodes(element, search);
            columnNames = new String[nodes.size()];
            for (Object a : nodes) {
                columnNames[i++] = ((Attribute) a).getValue();
            }
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find column names for " +
                    "'" + name + "'", e);
        }
        
        return columnNames;
        
    }
    
    /**
     *
     * @param tableOrView
     * @param columnName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public boolean columnExists(String tableOrView, String columnName)
    throws DatamodelException {
        
        boolean exists = false;
        String[] columnNames = getColumnNames(tableOrView);
        
        for (String column : columnNames) {
            
            if (column.toUpperCase().equals(columnName.toUpperCase())) {
                exists = true;
                break;
            }
            
        }
        
        return exists;
        
    }
    
    /**
     * Returns the corresponding java.sql.Types data-type of a certain
     * column of a table/view.
     *
     * @param name
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public String getColumnType(String name, String columnName)
    throws DatamodelException {
        
        Attribute dataTypeAttribute = null;
        String dataTypeValue = null;
        String search = "column[@name='" + columnName + "']/@data-type";
        
        // Retrieve table or view element
        Element element = getTableElement(name);
        if (element == null) {
            element = getViewElement(name);
        }
        
        try {
            
            dataTypeAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            dataTypeValue = dataTypeAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find data-type for " +
                    "'" + name + "/" + columnName + "'", e);
        }
        
        return dataTypeValue;
        
    }
    /**
     * Returns the precision of the data-type of a certain column of a
     * table/view.
     *
     * @param name
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public String getColumnPrecision(String name, String columnName)
    throws DatamodelException {
        
        Attribute dataPrecisionAttribute = null;
        String dataPrecisionValue = null;
        String search = "column[@name='" + columnName + "']/@data-precision";
        
        // Retrieve table or view element
        Element element = getTableElement(name);
        if (element == null) {
            element = getViewElement(name);
        }
        
        try {
            
            dataPrecisionAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            dataPrecisionValue = dataPrecisionAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find precision for " +
                    "'" + name + "/" + columnName + "'", e);
        }
        
        return dataPrecisionValue;
        
    }
    
    /**
     * Returns the scale of the data-type of a certain column of a table/view.
     *
     * @param name
     * @param columnName
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public String getColumnScale(String name, String columnName)
    throws DatamodelException {
        
        Attribute dataScaleAttribute = null;
        String dataScaleValue = null;
        String search = "column[@name='" + columnName + "']/@data-scale";
        
        // Retrieve table or view element
        Element element = getTableElement(name);
        if (element == null) {
            element = getViewElement(name);
        }
        
        try {
            
            dataScaleAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            dataScaleValue = dataScaleAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find data-scale for " +
                    "'" + name + "/" + columnName + "'", e);
        }
        
        return dataScaleValue;
        
    }
    
    /**
     * Returns a list of parameter names for a certain procedure/function
     *
     *
     * @return
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public String[] getArgumentNames(String packageName, String name)
    throws DatamodelException {
        
        int i = 0;
        String[] parameterNames = null;
        List nodes = null;
        
        // Retrieve table or view element
        Element element = getProcedureElement(packageName, name);
        if (element == null) {
            element = getFunctionElement(packageName, name);
        }
        
        try {
            
            // Retrieve all 'column' children and get their name attribute
            nodes = (List) XPath.selectNodes(element, "argument/@name");
            parameterNames = new String[nodes.size()];
            for (Object a : nodes) {
                parameterNames[i++] = ((Attribute) a).getValue();
            }
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find argument names for " +
                    "'" + name + "'", e);
        }
        
        return parameterNames;
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getArgumentDataType(String packageName, String procedureName,
            String argumentName)
            throws DatamodelException {
        
        Element element = null;
        Attribute argumentTypeAttribute = null;
        String argumentType = null;
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-type";
        
        // Retrieve procedure or function element
        element = getProcedureElement(packageName, procedureName);
        if (element == null) {
            element = getFunctionElement(packageName, procedureName);
        }
        
        try {
            
            argumentTypeAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            argumentType = argumentTypeAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find argument-type for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return argumentType;
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentDataLength(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        Element element = null;
        Attribute argumentLengthAttribute = null;
        String argumentLength = null;
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-length";
        
        // Retrieve procedure or function element
        element = getProcedureElement(packageName, procedureName);
        if (element == null) {
            element = getFunctionElement(packageName, procedureName);
        }
        
        try {
            
            argumentLengthAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            argumentLength = argumentLengthAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find data-length for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(argumentLength);
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentDataPrecision(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        Element element = null;
        Attribute argumentPrecisionAttribute = null;
        String argumentPrecision = null;
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-precision";
        
        // Retrieve procedure or function element
        element = getProcedureElement(packageName, procedureName);
        if (element == null) {
            element = getFunctionElement(packageName, procedureName);
        }
        
        try {
            
            argumentPrecisionAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            argumentPrecision = argumentPrecisionAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find data-precision for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(argumentPrecision);
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentDataScale(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        Element element = null;
        Attribute argumentScaleAttribute = null;
        String argumentScale = null;
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@data-scale";
        
        // Retrieve procedure or function element
        element = getProcedureElement(packageName, procedureName);
        if (element == null) {
            element = getFunctionElement(packageName, procedureName);
        }
        
        try {
            
            argumentScaleAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            argumentScale = argumentScaleAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find data-precision for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return Integer.valueOf(argumentScale);
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public int getArgumentPosition(String packageName, String procedureName,
            String argumentName)
            throws DatamodelException {
        
        Element element = null;
        Attribute argumentPositionAttribute = null;
        String argumentPosition = null;
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@position";
        
        // Retrieve procedure or function element
        element = getProcedureElement(packageName, procedureName);
        if (element == null) {
            element = getFunctionElement(packageName, procedureName);
        }
        
        try {
            
            argumentPositionAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            argumentPosition = argumentPositionAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find argument-position for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return new Integer(argumentPosition);
        
    }
    
    /**
     *
     * @param packageName
     * @param procedureName
     * @param argumentName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public String getArgumentParameterType(String packageName,
            String procedureName, String argumentName)
            throws DatamodelException {
        
        Element element = null;
        Attribute argumentParameterTypeAttribute = null;
        String argumentParameterType = null;
        String search = "//datamodel/procedures/procedure" +
                "[@package-name='" + packageName + "']" +
                "[@name='" + procedureName + "']" +
                "/argument[@name='" + argumentName + "']/@in-out";
        
        // Retrieve procedure or function element
        element = getProcedureElement(packageName, procedureName);
        if (element == null) {
            element = getFunctionElement(packageName, procedureName);
        }
        
        try {
            
            argumentParameterTypeAttribute =
                    (Attribute) XPath.selectSingleNode(element, search);
            argumentParameterType = argumentParameterTypeAttribute.getValue();
            
        } catch (JDOMException e) {
            throw new DatamodelException("Cannot find argument " +
                    " parameter-type for " +
                    "'" + packageName + "." + procedureName + "." +
                    argumentName + "'", e);
        }
        
        return argumentParameterType;
        
    }
    
    /**
     * Creates a prepared statement for a select query against a certain
     * table or view
     *
     * @param name
     * @param columnNames
     * @param whereClause
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public PreparedStatement getPreparedSelectStatement(
            String name, String[] columnNames, String whereClause)
            throws DatamodelException {
        
        try {
            System.out.println("getPreparedStatement connection.isClosed()=" +
                    connection.isClosed());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        int i = 1;
        int length = 0;
        StringBuffer statement = new StringBuffer();
        PreparedStatement preparedStatement = null;
        
        // Retrieve column names for table/view
        if (columnNames == null) {
            columnNames = getColumnNames(name);
        }
        length = columnNames.length;
        
        // Create SELECT query
        statement.append("SELECT ");
        for (String s : columnNames) {
            
            // Check for null-value ...
            // (see selectIntoBean: checking for transient fields...)
            // TODO: method to purge null-values from array
            if (s != null) {
                
                statement.append(s);
                
                if (i++ < length) {
                    statement.append(", ");
                }
                
            }
            
        }
        
        // Append FROM-clause
        statement.append(" FROM ").append(name);
        
        // Purge ", FROM"
        i = statement.indexOf(",  FROM");
        if (i > 5) {
            statement.delete(i, i + 2);
        }
        
        // Append WHERE-clause
        if (whereClause != null) {
            statement.append(" WHERE ").append(whereClause);
        }
        
        try {
            preparedStatement =
                    connection.prepareStatement(statement.toString());
        } catch (SQLException e) {
            throw new DatamodelException("Cannot get prepared statement", e);
        }
        
        System.out.println("" + statement.toString());
        return preparedStatement;
        
    }
    
    /**
     *
     * @param name
     * @return
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public PreparedStatement getPreparedSelectStatement(String name)
    throws DatamodelException {
        
        return getPreparedSelectStatement(name, null, null);
        
    }
    
    /**
     * Creates a prepared statement for a select query against a certain
     * table or view. If parameter columnNames is null all columns will
     * be included in SELECT statment.
     *
     *
     * @return
     * @param name
     * @param columnNames
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public PreparedStatement getPreparedInsertStatement(
            String name, String[] columnNames)
            throws DatamodelException {
        
        int i = 1;
        int length = 0;
        StringBuffer statement = new StringBuffer();
        PreparedStatement preparedStatement = null;
        
        // Retrieve column names for table/view if no columnNames
        // were given
        if (columnNames == null) {
            columnNames = getColumnNames(name);
        }
        length = columnNames.length;
        
        // Create INSERT query
        statement.append("INSERT INTO ").append(name).append(" (");
        for (String s : columnNames) {
            
            statement.append(s);
            
            if (i++ < length) {
                statement.append(", ");
            }
            
        }
        statement.append(")");
        
        // Append placeholder for values
        statement.append(" VALUES (");
        for (i = 1; i < length; i++) {
            
            statement.append("?");
            
            if (i < length) {
                statement.append(", ");
            }
            
        }
        statement.append(")");
        
        try {
            preparedStatement =
                    connection.prepareStatement(statement.toString());
        } catch (SQLException e) {
            throw new DatamodelException("Cannot get prepared statement", e);
        }
        
        return preparedStatement;
        
    }
    
    /**
     *
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     * @return
     */
    public PreparedStatement getPreparedInsertStatement(String name)
    throws DatamodelException {
        
        return getPreparedInsertStatement(name, null);
        
    }
    
    /**
     *
     *
     * @return
     * @param whereClause
     * @param name
     * @param columnNames
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public PreparedStatement getPreparedUpdateStatement(String name,
            String[] columnNames, String whereClause)
            throws DatamodelException {
        
        int i = 1;
        int length = 0;
        StringBuffer statement = new StringBuffer();
        PreparedStatement preparedStatement = null;
        
        // Retrieve column names for table/view if no columnNames
        // were given
        if (columnNames == null) {
            columnNames = getColumnNames(name);
        }
        length = columnNames.length;
        
        // Create UPDATE query
        statement.append("UPDATE ").append(name).append(" SET ");
        for (String s : columnNames) {
            
            statement.append(s + " = ?");
            
            if (i++ < length) {
                statement.append(", ");
            }
            
        }
        
        // Append WHERE-clause
        if (whereClause != null)  {
            statement.append(whereClause);
        }
        
        try {
            
            preparedStatement =
                    connection.prepareStatement(statement.toString());
            
        } catch (SQLException e) {
            throw new DatamodelException("Cannot get prepared statement", e);
        }
        
        return preparedStatement;
        
    }
    
    /**
     *
     *
     * @return
     * @param whereClause
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public PreparedStatement getPreparedUpdateStatement(String name,
            String whereClause)
            throws DatamodelException {
        
        return getPreparedUpdateStatement(name, whereClause);
        
    }
    
    /**
     *
     *
     * @return
     * @param packageName
     * @param name
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public CallableStatement getCallableStatement(String packageName,
            String name)
            throws DatamodelException {
        
        StringBuffer statement = new StringBuffer();
        int argumentCount = 0;
        CallableStatement callableStatement = null;
        
        // Initialize statement
        statement.append("{call " + packageName + "." + name).append("(");
        // Append parameters
        argumentCount = getArgumentNames(packageName, name).length;
        for (int i = 0; i < argumentCount; i++) {
            
            statement.append("?");
            
            if (i < argumentCount - 1) {
                statement.append(", ");
            }
            
        }
        // Close statement
        statement.append(")").append("}");
        
        try {
            callableStatement = connection.prepareCall(statement.toString());
        } catch (SQLException e) {
            throw new DatamodelException("Cannot prepare call for " +
                    "'" + name + "'", e);
        }
        
        System.out.println("callableStatement=" + statement.toString());
        return callableStatement;
        
    }
    
    /**
     * Creates a name for a method for a certain column or argument name.
     * Used with Java Reflection API for communication with Java Beans.
     *
     * @param name Column or argument name
     * @return
     */
    private String getMethodNameForColumnOrArgument(String name) {
        
        StringBuffer tmp = new StringBuffer(name);
        int i = 0;
        char d = '\0';
        
        // Replace Oracle prefixes
        i = tmp.indexOf("P_");
        if (i == 0) {
            tmp.replace(0, 2, "");
        }
        i = tmp.indexOf("V_");
        if (i == 0) {
            tmp.replace(0, 2, "");
        }
        
        // First character must be upper case and the rest lower case
        for (i = 0; i < tmp.length(); i++) {
            
            if (i == 0) {
                d = Character.toUpperCase(tmp.charAt(i));
            } else {
                d = Character.toLowerCase(tmp.charAt(i));
            }
            
            tmp.setCharAt(i, d);
            
        }
        
        return tmp.toString();
        
    }
    
    /**
     * Lookup a method by a column or argument name and ignore case
     */
    private Method lookupMethodForColumnOrArgument(Class clazz, String name) {
        return ClassUtil.findMethodByName(clazz, name, true);
    }
    
    /**
     *
     * @param callableStatement
     * @param argumentPosition
     * @param argumentDataType
     * @param argumentDataLength
     * @param argumentDataPrecision
     * @param argumentDataScale
     * @param value
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public void setCallableStatementArgument(
            CallableStatement callableStatement, int argumentPosition,
            String argumentDataType, int argumentDataLength,
            int argumentDataPrecision, int argumentDataScale,
            Object value)
            throws DatamodelException {
        
//        System.out.println(
//                " argumentDataType=" + argumentDataType +
//                " argumentLength=" + argumentDataLength +
//                " argumentPrecision=" + argumentDataPrecision +
//                " argumentScale=" + argumentDataScale +
//                " value=" + value);
        
        // TODO: Use setObject()
        
        try {
            
            if (argumentDataType.indexOf("CHAR") >= 0) {
                
                callableStatement.
                        setString(argumentPosition, (String) value);
                
            } else if (argumentDataType.equals("NUMBER")) {
                
                if (argumentDataScale == 0) {
                    
                    if (argumentDataPrecision <= 10) {
                        
                        callableStatement.setInt(argumentPosition,
                                Integer.valueOf("" + value));
                        
                    } else {
                        
                        callableStatement.setLong(argumentPosition,
                                Long.valueOf("" + value));
                        
                    }
                    
                } else {
                    
                    callableStatement.setDouble(argumentPosition,
                            Double.valueOf("" + value));
                    
                }
                
            } else if (argumentDataType.equals("DATE")) {
                
                callableStatement.setDate(argumentPosition,
                        new java.sql.Date(
                        ((java.util.Date) value).getTime()));
                
            }
            
        } catch (SQLException e) {
            throw new DatamodelException("", e);
        }
        
    }
    
    /**
     * Retrieves all neccessary data from a Java bean and executes a stored
     * procedure.
     *
     * @param bean
     * @param packageName
     * @param procedureName
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public void beanToProcedure(Object bean, String packageName,
            String procedureName)
            throws DatamodelException {
        
        CallableStatement callableStatement = null;
        String[] argumentNames = null;
        String argumentName = null;
        String argumentDataType = null;
        int argumentDataLength = 0;
        int argumentDataPrecision = 0;
        int argumentDataScale = 0;
        int argumentPosition = 0;
        String argumentParameterType = null;
        int type = 0;
        Types argumentJavaSqlType = null;
        String methodName = null;
        Class[] emptyClassArray = new Class[] {};
        Object[] emptyObjectArray = new Object[] {};
        Method method = null;
        Object value = null;
        
        // Get callable statement for stored procedure
        callableStatement = getCallableStatement(packageName, procedureName);
        
        // Get arguments for stored procedure
        argumentNames = getArgumentNames(packageName, procedureName);
        
        // For every argument get data from the bean and give it to the
        // callable statement
        for (int i = 0; i < argumentNames.length; i++) {
            
            // Get argument name, its type and its position in the callable
            // statement
            argumentName = argumentNames[i];
            argumentDataType =
                    getArgumentDataType(packageName, procedureName,
                    argumentName);
            argumentDataLength =
                    getArgumentDataLength(packageName, procedureName,
                    argumentName);
            argumentDataPrecision =
                    getArgumentDataPrecision(packageName, procedureName,
                    argumentName);
            argumentDataScale =
                    getArgumentDataScale(packageName, procedureName,
                    argumentName);
            argumentPosition =
                    getArgumentPosition(packageName, procedureName,
                    argumentName);
            argumentParameterType =
                    getArgumentParameterType(packageName, procedureName,
                    argumentName);
            
            type = getJavaTypeId(
                    argumentDataType, argumentDataLength,
                    argumentDataPrecision, argumentDataScale);
            
            // IN/OUT-parameters:
            if (argumentParameterType.equals("IN")) {
                
                methodName = "get" +
                        getMethodNameForColumnOrArgument(argumentName);
                
                // Call methodName on bean and set value in callable statement
                try {
                    
                    method = bean.getClass().getMethod(
                            methodName, emptyClassArray);
                    value = method.invoke(bean, emptyObjectArray);
                    
                    if (value == null) {
                        callableStatement.setNull(argumentPosition, type);
                    } else {
                        
                        setCallableStatementArgument(callableStatement,
                                argumentPosition, argumentDataType,
                                argumentDataLength, argumentDataPrecision,
                                argumentDataScale, value);
                        
                    }
                    
                } catch (SecurityException e) {
                    throw new DatamodelException("", e);
                } catch (NoSuchMethodException e) {
                    //throw new DatamodelException("", e);
                    Debug.log("Cannot access: " + methodName);
                } catch (IllegalAccessException e) {
                    throw new DatamodelException("", e);
                } catch (InvocationTargetException e) {
                    throw new DatamodelException("", e);
                } catch (SQLException e) {
                    throw new DatamodelException(
                            "Cannot apply data from bean to " +
                            "stored procedure: " + e.getMessage(), e);
                }
                
            }
            // An OUT-parameter must be registered with the callable statement
            // and later set the value in the bean using a setter-method
            else if (argumentParameterType.equals("OUT")) {
                
                try {
                    
                    callableStatement.
                            registerOutParameter(argumentPosition, type);
                    
                } catch (SQLException e) {
                    throw new DatamodelException("", e);
                }
                
            }
            
        }
        
        // Execute callable statement
        try {
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatamodelException("Cannot execute stored procedure: " +
                    e.getMessage(), e);
        }
        
        // Set OUT-parameters in bean
        for (int i = 0; i < argumentNames.length; i++) {
            
            // Get argument name, its type and its position in the callable
            // statement
            argumentName = argumentNames[i];
            argumentDataType =
                    getArgumentDataType(packageName, procedureName,
                    argumentName);
            argumentDataLength =
                    getArgumentDataLength(packageName, procedureName,
                    argumentName);
            argumentDataPrecision =
                    getArgumentDataPrecision(packageName, procedureName,
                    argumentName);
            argumentDataScale =
                    getArgumentDataScale(packageName, procedureName,
                    argumentName);
            argumentPosition =
                    getArgumentPosition(packageName, procedureName,
                    argumentName);
            argumentParameterType =
                    getArgumentParameterType(packageName, procedureName,
                    argumentName);
            
            type = getJavaTypeId(
                    argumentDataType, argumentDataLength,
                    argumentDataPrecision, argumentDataScale);
            
            if (argumentParameterType.equals("OUT")) {
                
                methodName = "set" +
                        getMethodNameForColumnOrArgument(argumentName);
                
                try {
                    
                    // TODO: Different types...
                    method = bean.getClass().getMethod(methodName,
                            new Class[] { Long.TYPE });
                    value = callableStatement.getObject(argumentPosition);
                    method.invoke(bean, new Object[] { (Long) value });
                    
                } catch (SecurityException e) {
                    throw new DatamodelException("", e);
                } catch (NoSuchMethodException e) {
                    //throw new DatamodelException("", e);
                    Debug.log("Cannot access: " + methodName);
                } catch (IllegalAccessException e) {
                    throw new DatamodelException("", e);
                } catch (InvocationTargetException e) {
                    throw new DatamodelException("", e);
                } catch (SQLException e) {
                    throw new DatamodelException(
                            "Cannot apply data from stored procedure to " +
                            "bean: " + e.getMessage(), e);
                }
                
            }
            
        }
        
    }
    
    /**
     *
     *
     * @return
     * @param tableOrView
     * @param datamodelBean
     * @param whereClause
     * @throws com.bensmann.superframe.exceptions.DatamodelException
     */
    public DatamodelBean[] selectIntoBean(
            String tableOrView, DatamodelBean datamodelBean, String whereClause)
            throws DatamodelException {
        
        try {
            System.out.println("connection.isClosed()=" + connection.isClosed());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//        System.out.println("datamodelBean=" + datamodelBean + " " +
//                datamodelBean.getClass());
        
        int i = 0;
        //
        DatamodelBean[] datamodelBeans = new DatamodelBean[0];
        DatamodelBean[] tmpDatamodelBeans = null;
        Object obj = null;
        Class clazz = null;
        Class[] emptyClassArray = new Class[] {};
        Class[] stringClassArray = new Class[] { String.class };
        Class[] longClassArray = new Class[] { Long.TYPE };
        Class[] dateClassArray = new Class[] { Date.class };
        //
        String genericString = null;
        Field[] fields = null;
        String methodName = null;
        Method method = null;
        // Counter for bean properties
        int beanPropertyCount = 0;
        int beanPropertyWithValueCount = 0;
        // Field[] with properties that will be used in the WHERE-Clause
        Field[] beanWhereProperties = null;
        // Arrays holding every non-transient field from bean
        String[] beanPropertyNames = null;
        Object[] beanPropertyValues = null;
        Class[] beanPropertyTypes = null;
        // Query operator is used for = or LIKE queries
        String queryOperator = null;
        // WHERE-clause made up by values from the bean
        StringBuffer beanWhereClause = null;
        // Temporary where clause; used to tie beanWhereClause and
        // whereClause together
        StringBuffer tmpWhereClause = null;
        //
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        int rowCount = 1;
        // Get column names for table/view
//        String[] columnNames = getColumnNames(tableOrView);
        String columnName = null;
        String columnType = null;
        // Helper for analysing types or values
        Class hClass = null;
        Date hDate = null;
        Object value = null;
        //
        PreparedStatement prepStatement = null;
        
        // Get class from DatamodelBean
        clazz = datamodelBean.getClass();
        
        // Get a list of all non-transient fields (and their values)
        // from the bean
        fields = clazz.getDeclaredFields();
        beanPropertyNames = new String[fields.length];
        beanPropertyValues = new Object[fields.length];
        beanPropertyTypes = new Class[fields.length];
        for (Field field : fields) {
            
            genericString = field.toGenericString();
            
            // Check if field is not transient and if it is a column
            // in the table/view
            if (!genericString.contains("transient") &&
                    columnExists(tableOrView, field.getName())) {
                
                beanPropertyNames[beanPropertyCount] = field.getName();
                beanPropertyTypes[beanPropertyCount] = field.getType();
                beanPropertyValues[beanPropertyCount] =
                        ClassUtil.getValueFromField(field, datamodelBean);
                
                if (beanPropertyValues[beanPropertyCount] != null) {
                    beanPropertyWithValueCount++;
                }
                
//                System.out.println("bean " + field.getName() + "/" +
//                        beanPropertyNames[beanPropertyCount] + " = " +
//                        beanPropertyValues[beanPropertyCount] + " type= " +
//                        beanPropertyTypes[beanPropertyCount] +
//                        " withValueCount=" + beanPropertyWithValueCount);
                
                beanPropertyCount++;
                
            }
            
        }
        
        // All properties with a non-null value will be used in WHERE-clause
        // Build up Field[] for field with a value
        beanWhereProperties = new Field[beanPropertyWithValueCount];
        i = 0;
        for (Field field : fields) {
            
            try {
                
                if (!genericString.contains("transient") &&
                        columnExists(tableOrView, field.getName()) &&
                        ClassUtil.getValueFromField(field, datamodelBean) != null) {
                    beanWhereProperties[i++] = field;
                }
                
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            
        }
        
        // Build WHERE-clause based on values from the bean
        // Only include non-null values in the query
        beanWhereClause = new StringBuffer();
        for (Field field : beanWhereProperties) {
            
            // Standard query operator
            queryOperator = "=";
            
            // Need AND because there are conditions yet
            if (beanWhereClause.length() > 0) {
                beanWhereClause.append(" AND ");
            }
            
            // Check for wildcars/LIKE-query in String-values
            if (field.getType() == String.class) {
                
                value = (String) ClassUtil.getValueFromField(
                        field, datamodelBean);
                
                if (((String) value).indexOf("%") >= 0) {
                    queryOperator = "LIKE";
                }
                
            }
            
            beanWhereClause.append(
                    field.getName() + " " + queryOperator + " ?");
            
        }
        
        // Create WHERE-clause
        if (beanWhereClause != null || whereClause != null) {
            tmpWhereClause = new StringBuffer();
        }
        if (beanWhereClause != null) {
            tmpWhereClause.append(beanWhereClause);
        }
        if (whereClause != null) {
            tmpWhereClause.append(" AND ").append(whereClause);
        }
        System.out.println("beanWhereClause: " + beanWhereClause.toString());
        System.out.println("where clause:" + tmpWhereClause.toString());
        // Set tmpWhereClause to null when no conditions exist
        if (tmpWhereClause.toString().length() == 0) {
            tmpWhereClause = null;
        }
        
        // Retrieve prepared statement for SELECT-query
        // TODO: For better optimization; which comes first:
        // beanWhereClause or whereClause? Are they AND- or OR-related?
        prepStatement = getPreparedSelectStatement(tableOrView,
                beanPropertyNames,
                tmpWhereClause != null ? tmpWhereClause.toString() : null);
        
        // Create DatamodelBean for every row from ResultSet and put it into
        // array datamodelBeans
        try {
            
            // Put non-null values from DatamodelBean into
            // prepared SELECT statement
            // Set values from bean properties in prepared statement
            i = 1;
            for (Field field : beanWhereProperties) {
                
                hClass = field.getType();
//                System.out.println("field type=" + hClass + "/" +
//                        hClass.getName());
                
                value = ClassUtil.getValueFromField(field, datamodelBean);
                
                if (hClass == String.class) {
                    prepStatement.setString(i, (String) value);
                } else if (hClass == Long.TYPE || hClass == Long.class) {
                    prepStatement.setLong(i, (Long) value);
                } else if (hClass == Integer.TYPE || hClass == Integer.class) {
                    prepStatement.setInt(i, (Integer) value);
                } else if (hClass == Float.TYPE || hClass == Float.class) {
                    prepStatement.setFloat(i, (Float) value);
                }  else if (hClass == Double.TYPE || hClass == Double.class) {
                    prepStatement.setDouble(i, (Double  ) value);
                } else if (hClass == Date.class) {
                    hDate = (java.util.Date) value;
                    prepStatement.setDate(i, new java.sql.Date(hDate.getTime()));
                }
                
                i++;
                
            }
            
            // Exceute query
            resultSet = prepStatement.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            
            // Convert result set into array of DatamodelBeans
            while(resultSet.next()) {
                
                // Create DatamodelBean with data from row
                try {
                    
                    obj = clazz.newInstance();
                    
                    for (beanPropertyCount = 1;
                    beanPropertyCount < resultSetMetaData.getColumnCount() + 1;
                    beanPropertyCount++) {
                        
                        columnName = resultSetMetaData.
                                getColumnName(beanPropertyCount);
                        methodName = "set" +
                                getMethodNameForColumnOrArgument(columnName);
                        
                        columnType = getColumnType(tableOrView, columnName);
                        
                        try {
                            
                            if (columnType.startsWith("VARCHAR")) {
                                method = clazz.
                                        getMethod(methodName, stringClassArray);
                                method.invoke(
                                        obj, resultSet.getString(beanPropertyCount));
                            } else if (columnType.equals("NUMBER")) {
                                // TODO !!! int,long,float,double?
                                method = clazz.
                                        getMethod(methodName, longClassArray);
                                method.invoke(obj,
                                        resultSet.getLong(beanPropertyCount));
                            } else if (columnType.equals("DATE")) {
                                method = clazz.
                                        getMethod(methodName, dateClassArray);
                                method.invoke(
                                        obj, resultSet.getDate(beanPropertyCount));
                            }
                            
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            //e.printStackTrace();
                            Debug.log("No such method: " + methodName);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        
                    }
                    
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                
                // Save datamodelBeans array
                tmpDatamodelBeans = new DatamodelBean[datamodelBeans.length];
                System.arraycopy(datamodelBeans, 0,
                        tmpDatamodelBeans, 0,
                        datamodelBeans.length);
                // Create new array with one additional slot
                datamodelBeans = new DatamodelBean[datamodelBeans.length + 1];
                // Copy array back
                System.arraycopy(tmpDatamodelBeans, 0,
                        datamodelBeans, 0,
                        tmpDatamodelBeans.length);
                // Put bean into array
                datamodelBeans[datamodelBeans.length - 1] = (DatamodelBean) obj;
                
                // Increase row counter
                rowCount++;
                
            }
            
        } catch (SQLException e) {
            throw new DatamodelException("", e);
        }
        
        return datamodelBeans;
        
    }
    
    /**
     * Gather datamodel (again). A new XML document is created and so
     * the datamodel will be read by getDocument()
     *
     * @return
     */
    public Document refresh() {
        
        rootElement = new Element("datamodel");
        document = new Document(rootElement);
        
        return getDocument();
        
    }
    
    /**
     *
     * @param d
     * @throws java.io.IOException
     */
    public void debug(Document d) throws IOException {
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(d, System.out);
    }
    
    /**
     *
     *
     * @param e
     * @throws java.io.IOException
     */
    public void debug(Element e) throws IOException {
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        outputter.output(e, System.out);
    }
    
    /**
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        long start = 0l;
        long stop = 0l;
        Connection c = null;
        OracleDatamodel gdm = null;
        Document d = null;
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
//        c = DriverManager.getConnection(
//                "jdbc:oracle:thin:@localhost:1521:xe", "tim", "tim");
        c = DriverManager.getConnection(
                "jdbc:oracle:thin:@gandalf-oracle:1521:pmbs", "tim", "tim");
        
        start = System.currentTimeMillis();
        gdm = OracleDatamodel.getInstance(c);
        d = gdm.getDocument();
//        stop = System.currentTimeMillis();
//        System.out.println("duration=" + ((stop-start)/1000.0d));
//        gdm.debug(d);
//
//        Element table = gdm.getTableElement("MITARBEITER");
//        System.out.println("getTableElement: " + table.getChildren().size());
//        for (String s : gdm.getColumnNames("MITARBEITER")) {
//            System.out.println("" + s);
//        }
//
//        Element procedure =
//                gdm.getProcedureElement("TIM_USER", "MITARBEITER_HINZU");
//        System.out.println("getProcedureElement: " +
//                procedure.getChildren().size());
//        for (String s :
//        gdm.getArgumentNames("TIM_USER", "MITARBEITER_HINZU")) {
//            System.out.println("" + s);
//        }
//
//        PreparedStatement pstmt = gdm.getPreparedSelectStatement(
//                "V_MITARBEITER",
//                new String[] { "MITARBEITERID", "NACHNAME" },
//                "MITARBEITERID = ?");
//        pstmt.setInt(1, 1);
//        ResultSet rs = pstmt.executeQuery();
//        while (rs.next()) {
//            System.out.println(
//                    rs.getString("MITARBEITERID") +
//                    " " +
//                    rs.getString("NACHNAME"));
//        }
//
//        PreparedStatement pstmt = gdm.getPreparedInsertStatement(
//                "MITARBEITER");
//
//        System.out.println("" + gdm.getColumnType("MITARBEITER", "ID"));
//
        gdm = OracleDatamodel.getInstance(c);
        
        System.out.println("V_MITARBEITER.EMAIL=" +
                gdm.columnExists("V_MITARBEITER", "EMAIL"));
        
//        // Test bean with procedure
//        TestBean t = new TestBean();
//        t.anschriftprivat="meine anschrift 12";
//        t.ansprechpartner="Caroline2 Bensmann";
//        t.email="caroline@bensmann.de";
//        t.endzeit = new java.util.Date();
//        t.startzeit = new java.util.Date();
//        t.erstelltMitarbeiterId = 1;
//        t.fax = "2";
//        t.telefon = "3";
//        t.geburtsdatum = new java.util.Date();
//        t.jahresurlaubTage=30;
//        t.sollstundenMonat=160;
//        t.nachname="Bensmann";
//        t.vorname="Caroline2";
//        t.plzprivat="48351";
//        t.ortprivat="Blabla";
//        t.niederlassungid=1;
//        gdm.beanToProcedure(t, "TIM_USER", "MITARBEITER_HINZU");
//        System.out.println("ID="+t.id);
        
        //
        class MyBean implements DatamodelBean {
            @DatamodelZeroNotOk()
            private long mitarbeiterid;
            @DatamodelZeroNotOk()
            private String vorname;
            private String nachname;
            private Date geburtsdatum;
            private transient String istEgal;
            private String gibbetNicht;
            public void setMitarbeiterid(long mitarbeiterid) {
                this.mitarbeiterid = mitarbeiterid;
            }
//            public void setMitarbeiterid(Long mitarbeiterid) {
//                this.mitarbeiterid = mitarbeiterid;
//            }
            public long getMitarbeiterid() {
                return mitarbeiterid;
            }
            public void setVorname(String vorname) {
                this.vorname = vorname;
            }
            public String getVorname() {
                return vorname;
            }
            public void setNachname(String nachname) {
                this.nachname = nachname;
            }
            public String getNachname() {
                return nachname;
            }
            public void setGeburtsdatum(Date geburtsdatum) {
                this.geburtsdatum = geburtsdatum;
            }
            public Date getGeburtsdatum() {
                return geburtsdatum;
            }
            public void setGibbetNicht(String gibbetNicht) {
                this.gibbetNicht = gibbetNicht;
            }
            public String getGibbetNicht() {
                return gibbetNicht;
            }
            public void reset() {
            }
        }
        MyBean myBean = new MyBean();
        myBean.setVorname("");
//        myBean.setMitarbeiterid(1);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1978);
        cal.set(Calendar.MONTH, 9 - 1);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        myBean.setGeburtsdatum(cal.getTime());
        DatamodelBean[] beans =
                gdm.selectIntoBean("V_MITARBEITER", myBean, null);
        for (DatamodelBean b : beans) {
            if (b != null) {
                System.out.println("id: " +
                        ((MyBean) b).getMitarbeiterid());
                System.out.println("vorname: " +
                        ((MyBean) b).getVorname());
                System.out.println("nachname: " +
                        ((MyBean) b).getNachname());
                System.out.println("geburtsdatum: " +
                        ((MyBean) b).getGeburtsdatum());
                System.out.println("\n\n");
            }
        }
        
    }
    
}
