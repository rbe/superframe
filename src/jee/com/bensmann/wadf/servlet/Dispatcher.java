/*
 * Created on Feb 22, 2005
 *
 */
package com.bensmann.wadf.servlet;

import com.bensmann.superframe.java.Debug;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bensmann.superframe.persistence.jdbc.DmlMetaData;
import com.bensmann.superframe.persistence.jdbc.JdbcConnectionManager;
import com.bensmann.superframe.persistence.jdbc.SingleJdbcConnection;
import com.bensmann.superframe.persistence.jdbc.StoredProcTypeInfo;
import com.bensmann.wadf.Configuration;

/**
 * @author rb
 * @version $Id: Dispatcher.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *
 * Dispatcher and datasource modification handler servlet
 *
 */
public class Dispatcher extends HttpServlet {
    
    /**
     *
     */
    private HttpServletRequest request;
    
    /**
     *
     */
    private HttpServletResponse response;
    
    /**
     *
     */
    private HttpSession session;
    
    /**
     * URI
     */
    private String uri;
    
    /**
     *
     */
    private SingleJdbcConnection sjc;
    
    /**
     * Output stream für das Generieren von HTML
     */
    private PrintWriter out = null;
    
    /**
     * Class field: Instance counter
     */
    public static int counter;
    
    /**
     *
     */
    public Dispatcher() {
        // Increase instance counter
        counter++;
    }
    
    /**
     * Initialize servlet (done one time at deployment of application)
     */
    public void init() {
        // Debug
        Debug.log("Initialization complete for instance #" + counter);
    }
    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Servlet#destroy()
     */
    public void destroy() {
        
        // Decrease instance counter
        counter--;
        
        // Debug
        Debug.log("Decreased instance counter: " + counter);
        
        super.destroy();
    }
    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        processRequest(request, response);
        
    }
    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        processRequest(request, response);
        
    }
    
    /**
     * Save all request parameters in session
     *
     */
    private void saveRequestParameterForUri() {
        
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            
            String p = (String) e.nextElement();
            String v = request.getParameter(p);
            
            // Debug
            Debug.log("Saving request parameter '" + p + "' with value '" + v +
                    "'");
            
            // Save parameter in session
            session.setAttribute(p, v);
            
        }
        
    }
    
    /**
     *
     * @param request
     * @param response
     */
    private void processRequest(HttpServletRequest request,
            HttpServletResponse response) {
        
        // Save request and response
        this.request = request;
        this.response = response;
        
        // Retrieve session
        session = request.getSession();
        
        // Retrieve URI from request
        uri = request.getRequestURI();
        // Save parameters submitted by URI
        saveRequestParameterForUri();
        
        // Set content type to text/html
        response.setContentType("text/html");
        
        try {
            
            // Get output stream; throws IOException
            out = response.getWriter();
            
            // JDBC connection test
            if (uri.indexOf("/jdbcConnectionTest") >= 0) {
                doJdbcTest();
            }
            // Show request parameters
            else if (uri.indexOf("/showRequestParameter") >= 0) {
                doShowRequestParameter();
            }
            // Show request
            else if (uri.indexOf("/showRequest") >= 0) {
                doShowRequest();
            }
            // Show session
            else if (uri.indexOf("/showSession") >= 0) {
                doShowSession();
            }
            // Invalidate current session
            else if (uri.indexOf("/invalidateSession") >= 0) {
                doInvalidateSession();
            }
            // Forward to URL
            // TODO: Implement counter for links
            else if (uri.indexOf("/forward") >= 0) {
                maskUrl(request.getParameter("url"));
            }
            // Special case: process SQL DELETE request
            // For testing with .indexOf this URI must be tested before /sql
            else if (uri.indexOf("/sqldelete") >= 0) {
                doSqlDelete();
            }
            // Process SQL
            else if (uri.indexOf("/sql") >= 0) {
                doSql();
            }
            // Process login of a user
            else if (uri.indexOf("/login") >= 0) {
                
                //processLogin();
                return;
                
            }
            // Process logout of a user
            else if (uri.indexOf("/logout") >= 0) {
                
                //processLogout();
                return;
                
            }
            // Go to ...
            else if (uri.indexOf("/go") >= 0) {
                
                // ... next template (set by maskUrl)
                String nextTemplate = (String) session.getAttribute(
                        "nexttemplate");
                
                if (nextTemplate != null) {
                    // Debug
                    Debug.log("GOing to next template='" + nextTemplate + "'");
                } else {
                    
                    // Debug
                    Debug.log("No next template found in session. Going to /");
                    
                    nextTemplate = "/";
                    
                }
                
                forwardTo(nextTemplate);
                
                return;
                
            }
            // Go to a JSP template
            else if (uri.indexOf(".go") >= 0) {
                
                String nextTemplate = uri.replaceAll(
                        request.getContextPath(), "").replaceAll(".go", ".jsp");
                
                forwardTo(nextTemplate);
                
                return;
                
            } else {
                out.println("Operation in request URI '"
                        + request.getRequestURI() + "' not supported!");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Mask URL by storing next/destination template in session and redirect to
     * /go.do
     *
     * @param nextTemplate
     * @throws IOException
     */
    private void maskUrl(String nextTemplate) throws IOException {
        
        session.setAttribute("nexttemplate", nextTemplate);
        forwardTo(request.getContextPath() + "/go.do");
        
    }
    
    /**
     * Leitet zu beliebieger Seite um. Wird kein 'http://' im String 'url'
     * angegeben, dann wird eine JSP im aktuellen Context der Applikation
     * angenommen
     *
     * @param url
     */
    private void forwardTo(String url) {
        
        try {
            
            if (url.startsWith("http://")) {
                
                // Debug
                Debug.log("Sending redirect to external URL: '" + url + "'");
                
                response.sendRedirect(url);
                
            } else {
                
                // Ensure that URL starts with /
                if (!url.startsWith("/")) {
                    url = "/" + url;
                }
                
                // Debug
                Debug.log("Forwarding to interal resource: '" + url + "'");
                
                /*
                RequestDispatcher rd = request.getRequestDispatcher(url);
                if (rd != null)
                    rd.forward(request, response);
                 */
                
                response.sendRedirect(response.encodeRedirectURL(
                        request.getContextPath() + url));
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
//    /**
//     * Error page: fill session attributes with values from exception and
//     * redirect to error page
//     *
//     * @param request
//     * @param response
//     */
//    private void errorPage(Throwable t) {
//
//        Throwable pt = t;
//        while (pt.getCause() != null) {
//            pt = pt.getCause();
//        }
//
//        request.getSession().setAttribute("qMessage", pt.getMessage());
//        request.getSession().setAttribute("qException", t);
//        request.getSession().setAttribute("qRootCause", pt);
//
//        try {
//            response.sendRedirect(request.getContextPath() + "/error.jsp");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * Error page: fill session attribute "message" and redirect to error page
//     *
//     * @param request
//     * @param response
//     * @param message
//     */
//    public void errorPage(String message) {
//
//        request.getSession().setAttribute("qMessage", message);
//
//        try {
//            response.sendRedirect(request.getContextPath() + "/error.jsp");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    
    /**
     * Look up all columns in request parameters for a certain table. For better
     * access the parameter names are converted to upper case and the prefix
     * sqlTable_ is removed
     *
     * @param sqlTable
     * @return
     */
    private Map<String, String> getColumnsAndValuesForTable(String sqlTable) {
        
        // TODO: Use semaphores
        Map<String, String> sqlTableParams = Collections.synchronizedMap(
                new HashMap<String, String>());
        
        Enumeration requestParamEnum = request.getParameterNames();
        while (requestParamEnum.hasMoreElements()) {
            
            String param = (String) requestParamEnum.nextElement();
            if (param.indexOf(sqlTable + "_") == 0) {
                
                // Debug
                Debug.log("Add column '" + param + "' to column list for " +
                        "table '" + sqlTable + "'");
                
                // Add request parameter to list holding all parameters that
                // should be used to modify database
                sqlTableParams.put(param.replaceAll(sqlTable + "_", "").
                        toUpperCase(), (String) request.getParameter(param));
                
            }
            
        }
        
        return sqlTableParams;
        
    }
    
    /**
     * Look up a certain column in request parameters for a certain table. The
     * prefix sqlTable_ is removed
     *
     * @param sqlTable
     * @return
     */
    private Map<String, String> findColumnAndValuesForTable(String sqlTable,
            String column) {
        
        Map<String, String> sqlTableParams = Collections.synchronizedMap(
                new HashMap<String, String>());
        
        Enumeration requestParamEnum = request.getParameterNames();
        while (requestParamEnum.hasMoreElements()) {
            
            String param = (String) requestParamEnum.nextElement();
            if (param.indexOf(sqlTable + "_") == 0 && param.indexOf(column) > 0) {
                
                // Debug
                Debug.log("Found column '" + param + "' for table '" +
                        sqlTable + "'");
                
                // Add request parameter to list holding all parameters that
                // should be used to modify database
                sqlTableParams.put(param.replaceAll(sqlTable + "_", ""),
                        (String) request.getParameter(param));
                
            }
            
        }
        
        return sqlTableParams;
        
    }
    
    /**
     * Set parameter of prepared statement to its correct value got from request
     * parameters. Returns set index that was set.
     *
     * @param sqlTable
     * @param dmlMetaData
     * @param column
     * @param p
     * @return
     * @throws SQLException
     */
    private int setPreparedParameter(String sqlTable, DmlMetaData dmlMetaData,
            String column, PreparedStatement p) throws SQLException {
        
        // Upper case: Oracle
        String cu = column.replaceAll(sqlTable + "_", "").toUpperCase();
        String value = request.getParameter(column);
        int parameterIndex = dmlMetaData.getParameterIndex(column);
        String type = dmlMetaData.getColumnDescription(cu).getColumnType();
        int precision = dmlMetaData.getColumnDescription(cu).
                getColumnPrecision();
        int scale = dmlMetaData.getColumnDescription(cu).getColumnScale();
        
        // Debug
        Debug.log("Parameter index for column '" + column + "' is "
                + parameterIndex + " value='" + value + "'");
        
        //
        // Set value for column in prepared statement
        //
        // CHAR, VARCHAR, VARCHAR2
        //
        if (type.toUpperCase().indexOf("CHAR") >= 0) {
            
            // Debug
            Debug.log("setString for '" + value + "' at parameter index "
                    + parameterIndex);
            
            p.setString(parameterIndex, value);
            
        }
        //
        // NUMBER
        //
        else if (type.toUpperCase().indexOf("NUMBER") >= 0) {
            
            // Check precision and scale
            // Oracle:
            // NUMBER -> P=0 S=-127
            // NUMBER(10) -> P=10, S=0
            // FLOAT -> P=126, S=-127
            
            if (precision == 0 && scale == -127) {
                
                long l = Long.valueOf(value).longValue();
                
                // Debug
                Debug.log("setLong for value '" + value + "' -> '" + l
                        + "' at parameter index " + parameterIndex);
                
                p.setLong(parameterIndex, l);
                
            } else if (precision > 0 && scale == 0) {
                
                long l = Long.valueOf(value).longValue();
                
                // Debug
                Debug.log("setLong for value '" + value + "' -> '" + l
                        + "' at parameter index " + parameterIndex);
                
                p.setLong(parameterIndex, l);
                
            } else if (precision == 126 && scale == -127) {
                
                float f = Float.valueOf(value).floatValue();
                
                // Debug
                Debug.log("setFloat for '" + value + "' -> '" + f
                        + "' at parameter index " + parameterIndex);
                
                p.setFloat(parameterIndex, f);
                
            }
            
        }
        //
        // DATE
        //
        else if (type.toUpperCase().indexOf("DATE") >= 0) {
            
            java.util.Date d;
            String dateFormat = "dd.MM.yyyy";
            
            // German date representation
            if (value.indexOf('.') > 0) {
                dateFormat = "dd.MM.yyyy";
            }
            // English/american representation
            else if (value.indexOf('/') > 0) {
                dateFormat = "MM/dd/yyyy";
            }
            // International representation
            else if (value.indexOf('-') > 0) {
                dateFormat = "yyyy-MM-dd";
            }
            
            // Determine given date format (from request parameter)
            try {
                
                // Parse request parameter into java.util.Date using
                // SimpleDateFormat
                d = new SimpleDateFormat(dateFormat).parse(value);
                
                // Debug
                Debug.log("Parsed request parameter '" + value +
                        "' into java.util.Date=" + d);
                
                // Convert java.util.Date to java.sql.Date
                // create a Calendar object and use .getTimeInMillis() for
                // constructor of java.sql.Date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                
                p.setDate(parameterIndex, new java.sql.Date(calendar.
                        getTimeInMillis()));
                
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        }
        
        return parameterIndex;
        
    }
    
    /**
     * Process SQL DELETE modifications
     *
     * First we have to ensure that DELETE operation is permitted. Therefore we
     * need to check some points:
     * <p>-
     *
     * @throws SQLException
     */
    private void processSqlDelete() throws SQLException {
        
        // Get table to delete from
        String sqlTable = (String) session.getAttribute("sqltable");
        
        // Debug
        Debug.log("DELETEing from table '" + sqlTable + "'");
        
        if (sqlTable != null) {
            
            // Look for ID column for that table
            long id = Long.valueOf(
                    (String) getColumnsAndValuesForTable(sqlTable).get("ID")).
                    longValue();
            
            if (id > 0) {
                
                // Unique identifier for DELETE statement
                String prepStmtIdent = "prepstmt-delete-" + sqlTable;
                
                // Check if prepared statement exists in session
                PreparedStatement p = (PreparedStatement) session.
                        getAttribute(prepStmtIdent);
                // If not, create new prepared DELETE statement and store it in
                // session
                if (p == null) {
                    
                    // Set SQL query
                    String sqlQuery = "DELETE FROM " + sqlTable
                            + " WHERE id = ?";
                    
                    // Debug
                    Debug.log("Generated DELETE statement: " + sqlQuery);
                    
                    // Get JDBC connection from session
                    sjc = (SingleJdbcConnection) session
                            .getAttribute("mainjdbc");
                    
                    p = sjc.getPreparedStatement(sqlQuery);
                    session.setAttribute(prepStmtIdent, p);
                    
                } else {
                    
                    // Debug
                    Debug.log("Re-using prepared DELETE statement from session");
                    
                }
                
                // Execute prepared statement
                p.setLong(1, id);
                p.execute();
                
                // Debug
                Debug.log("Successfully deleted ID '" + id + "' from table '"
                        + sqlTable + "'");
                
            } else {
                
                // Debug
                Debug.log("Cannot DELETE from table '" + sqlTable
                        + "' because ID has an insufficient value: '"
                        + id + "'");
                
            }
            
        } else {
            
            // Debug
            Debug.log("Cannot DELETE: table has an insufficient value: '"
                    + sqlTable + "'");
            
        }
        
    }
    
    /**
     * Return list of parameters from request, that are columns for a certain
     * table
     * @param sqlTable
     * @return
     */
    private Vector getSqlTableParams(String sqlTable) {
        
        Vector<String> sqlTableParams = new Vector<String>();
        
        // Fetch all parameters that name begins with value of sqltable
        Enumeration requestParamEnum = request.getParameterNames();
        while (requestParamEnum.hasMoreElements()) {
            
            String param = (String) requestParamEnum.nextElement();
            if (param.indexOf(sqlTable + "_") == 0) {
                
                // Debug
                Debug.log("Add column '" + param + "' to column list for " +
                        "table '" + sqlTable + "'");
                
                // Add request parameter to list holding all parameters that
                // should be used to modify database
                sqlTableParams.add(param);
                
            }
            
        }
        
        return sqlTableParams;
        
    }
    
    /**
     * Process SQL INSERT/UPDATE modifications
     *
     * Generated statements are stored as prepared statements in session
     *
     * Rules:
     * <p>- If column ID is present, we assume an UPDATE to an existing row
     * that is uniqely identified by the ID
     * <p>- If no ID is present, we assume an INSERT using a sequence to
     * determine next/highest value for ID
     *
     * @param sqlTable
     */
    private void processSqlTable(String sqlTable) throws SQLException {
        
        // Debug
        Debug.log("Processing SQL table '" + sqlTable + "'");
        
        // Get ID
        String idColumnValue = (String) session.getAttribute(sqlTable + "_id");
        // Get JDBC connection from session
        // TODO: whats the name of and how to get "default connection"
        sjc = JdbcConnectionManager.getInstance().getConnection(
                session.getId() + "_" +
                Configuration.getInstance().getDatasource(true).getJdbcUrl());
        
        // Get table parameters from request
        Vector sqlTableParams = getSqlTableParams(sqlTable);
        // Get (exisiting) DmlMetaData object
        DmlMetaData dmlMetaData = DmlMetaData.getInstance(sjc, sqlTable,
                sqlTableParams);
        // Debug
        Debug.log("Got DmlMetaData object for table '" + sqlTable + "' -> " +
                dmlMetaData);
        
        //
        // UPDATE: column ID was given as request parameter
        //
        if (idColumnValue != null && idColumnValue.length() > 0) {
            
            // Debug
            Debug.log("UPDATing existing row");
            
            try {
                
                // Set values for columns
                PreparedStatement p = dmlMetaData.getUpdateStatement();
                
                // Parameter index
                int parameterIndex = 0;
                
                Iterator i = sqlTableParams.iterator();
                while (i.hasNext()) {
                    
                    String column = (String) i.next();
                    
                    parameterIndex = setPreparedParameter(sqlTable,
                            dmlMetaData, column, p);
                    
                }
                
                // Add ID column using increased parameterIndex
                p.setLong(++parameterIndex, Long.valueOf(idColumnValue).
                        longValue());
                
                // Execute prepared UPDATE statement
                p.execute();
                
                // Debug
                Debug.log("Executed SQL statement successfully");
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }
        //
        // INSERT: column ID was NOT given as request parameter
        //
        else {
            
            // Debug
            Debug.log("INSERTing new row");
            
            try {
                
                // INSERT: set values for columns
                PreparedStatement p = dmlMetaData.getInsertStatement();
                
                Iterator i = sqlTableParams.iterator();
                while (i.hasNext()) {
                    
                    String column = (String) i.next();
                    
                    // Debug
                    Debug.log("Parameter index for colunm '" + column +
                            "' is " + dmlMetaData.getParameterIndex(column));
                    
                    // Set value for column in prepared statement
                    setPreparedParameter(sqlTable, dmlMetaData, column, p);
                    
                }
                
                // Execute prepared INSERT statement
                p.execute();
                
            } catch (SQLException e) {
                
                e.printStackTrace();
                throw new SQLException(e.getMessage());
                
            }
            
        }
        
    }
    
    /**
     *
     * @param sqlProc
     * @throws java.sql.SQLException
     */
    private void processSqlProc(String sqlProc) throws SQLException {
        
        // Get JDBC connection from session
        sjc = JdbcConnectionManager.getInstance().getConnection(
                session.getId() + "_" +
                Configuration.getInstance().getDatasource(true).getJdbcUrl());
        
        // Debug
        Debug.log("Processing SQL procedure '" + sqlProc + "'");
        
        // Prefix for parmeter counter for stored procedure in session
        String parameterCountPrefix = "procparamcounter-" + sqlProc;
        
        String parameterPrefix = "procparam-" + sqlProc;
        
        // Get parameter count
        int parameterCount;
        try {
            parameterCount = ((Integer) session
                    .getAttribute(parameterCountPrefix)).intValue();
        } catch (NullPointerException e) {
            parameterCount = 0;
        }
        
        // Debug
        Debug.log("Parameter count for stored procedure '" + sqlProc +
                "' is '" + parameterCount + "'");
        
        StringBuffer sqlQuery = new StringBuffer("{call " + sqlProc + "(");
        
        // Get parameters for stored procedure from session
        for (int i = 1; i < parameterCount + 1; i++) {
            
            sqlQuery.append("?");
            
            if (i < parameterCount - 1) {
                sqlQuery.append(", ");
            }
            
        }
        
        sqlQuery.append(")}");
        
        CallableStatement c = sjc.getCallableStatement(sqlQuery.toString());
        
        // Debug
        Debug.log("Generated callable statement: " + sqlQuery.toString() +
                " CallableStatement=" + c);
        
        // Set parameters in callable statement
        for (int i = 1; i < parameterCount + 1; i++) {
            
            String ident = parameterPrefix + "-" + i;
            
            // Debug
            Debug.log("Retrieving StoredProcTypeInfo for '" + ident +
                    "' from session");
            
            StoredProcTypeInfo spti = (StoredProcTypeInfo) session
                    .getAttribute(ident);
            
            Object o = spti.getParameterValue();
            
            // Debug
            Debug.log("Adding parameter index " + i +
                    " to callable statement, type[" +
                    spti.getParameterType() + "] value[" + o + "]");
            
            if (spti.getParameterType().equalsIgnoreCase("string")) {
                c.setString(i, (String) o);
            } else if (spti.getParameterType().equalsIgnoreCase("int")) {
                c.setLong(i, Long.valueOf((String) o).longValue());
            }
            
            // Remove parameter info from session
            session.removeAttribute(ident);
            
        }
        
        // Debug
        Debug.log("Executing callable statement");
        
        c.execute();
        sjc.commit();
        
        // Debug
        Debug.log("Executed callable statement");
        
        // Debug
        Debug.log("Removing parameter counter for stored procedure '" +
                sqlProc + "' from session");
        
        // Remove parameter counter from session
        session.removeAttribute(parameterCountPrefix);
        
    }
    
    /**
     *
     * @throws SQLException
     */
    private void processSql() throws SQLException {
        
        // Get table to modify
        String sqlTable = (String) session.getAttribute("sqltable");
        // Get stored procedure to call
        String sqlProc = (String) session.getAttribute("sqlproc");
        
        if (sqlTable != null) {
            processSqlTable(sqlTable);
        } else if (sqlProc != null) {
            processSqlProc(sqlProc);
        }
        
    }
    
    /**
     *
     * @throws java.io.IOException
     */
    private void doSqlDelete() throws IOException {
        
        // Check referer against local server name
        String referer = request.getHeader("Referer");
        String serverName = request.getServerName();
        
        try {
            processSqlDelete();
            maskUrl((String) request.getParameter("successpage"));
        } catch (SQLException e) {
            e.printStackTrace();
            maskUrl((String) request.getParameter("errorpage"));
        }
        
    }
    
    /**
     *
     * @throws java.io.IOException
     */
    private void doSql() throws IOException {
        
        try {
            processSql();
            maskUrl((String) session.getAttribute("successpage"));
        } catch (SQLException e) {
            e.printStackTrace();
            maskUrl((String) session.getAttribute("errorpage"));
        }
        
    }
    
    /**
     * JDBC Connection Test
     *
     * Es müssen folgende Request-Parameter übergeben werden:
     * <p>- jdbcDriver = JDBC Treiber
     * <p>- jdbcHost = Host auf dem die Datenbank läuft
     * <p>- jdbcDbname = Datenbankname
     * <p>- jdbcUser = Benutzer
     * <p>- jdbcPwd = Passwort
     *
     * Wird noch der Parameter "show" übergeben, so wird ein Status des Tests
     * angezeigt, anstatt auf andere URLs weiterzuleiten
     *
     */
    private void doJdbcTest() {
        
        String jdbcDriver = request.getParameter("jdbcDriver");
        String jdbcHost = request.getParameter("jdbcHost");
        String jdbcDbname = request.getParameter("jdbcDbname");
        String jdbcUser = request.getParameter("jdbcUser");
        String jdbcPwd = request.getParameter("jdbcPwd");
        
        String show = request.getParameter("show");
        
        String jdbcUrl = null;
        
        if (jdbcHost == null)
            jdbcHost = "localhost";
        
        if (jdbcDriver != null && jdbcDriver.indexOf("mysql") >= 0) {
            jdbcUrl = "jdbc:mysql://" + jdbcHost + "/" + jdbcDbname + "?user="
                    + jdbcUser
                    + (jdbcPwd != null ? "&password=" + jdbcPwd : "");
        }
        
        out.println("jdbcDriver = " + jdbcDriver + "<br>");
        out.println("jdbcHost = " + jdbcHost + "<br>");
        out.println("jdbcDbname = " + jdbcDbname + "<br>");
        out.println("jdbcUser = " + jdbcUser + "<br>");
        out.println("jdbcPwd = " + jdbcPwd + "<br>");
        out.println("jdbcUrl = " + jdbcUrl + "<br>");
        
        if (jdbcDriver == null | jdbcHost == null | jdbcDbname == null
                | jdbcUser == null) {
            
            out.println("Cannot test JDBC connection. Missing parameters.");
            
        } else {
            
            // Load driver
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                out.println(e.getMessage());
            }
            
            // Connect to database
            try {
                
                Connection conn = DriverManager.getConnection(jdbcUrl);
                out.println("Connection successful!");
                conn.close();
                
            } catch (SQLException e) {
                out.println(e.getMessage());
            }
            
        }
        
    }
    
    /**
     * Zeigt alle übergebenen Parameter an
     *
     */
    private void doShowRequestParameter() {
        
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>showRequestParameter</h1>");
        
        int c = 0;
        Map parameterMap = request.getParameterMap();
        Iterator parameterIterator = parameterMap.entrySet().iterator();
        while (parameterIterator.hasNext()) {
            
            c++;
            
            Map.Entry e = (Map.Entry) parameterIterator.next();
            String p = (String) e.getKey();
            out.println("Parameter '" + p + "' = '" + request.getParameter(p)
            + "'<br>");
            
        }
        
        out.println("We have " + c + " variables<br>");
        
        out.println("<br>");
        
        out.println("Encoded URL: <a href=\""
                + response.encodeURL(request.getContextPath()
                + "/showRequestParameter.do") + "\">test</a>");
        
        out.println("</body>");
        out.println("</html>");
        
    }
    
    /**
     * Zeigt alle wichtigen Informationen aus dem Request an
     *
     */
    private void doShowRequest() {
        
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>showRequest</h1>");
        
        out.println("Session Id: " + request.getSession().getId() + "<br>");
        
        out.println("<br>");
        
        out.println("Method: " + request.getMethod() + "<br>");
        out.println("Request URL: " + request.getRequestURL() + "<br>");
        
        out.println("<br>");
        
        out.println("Content type: " + request.getContentType() + "<br>");
        out.println("Content length: " + request.getContentLength() + "<br>");
        
        out.println("<br>");
        
        out.println("<h2>All request attributes:</h2>");
        Enumeration requestAttributes = request.getAttributeNames();
        while (requestAttributes.hasMoreElements()) {
            out.println((String) requestAttributes.nextElement() + "<br>");
        }
        
        out.println("<br>");
        
        out.println("<h2>All request headers:</h2>");
        Enumeration requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            String header = (String) requestHeaders.nextElement();
            out.println(header + ": " + request.getHeader(header) + "<br>");
        }
        
        out.println("</body>");
        out.println("</html>");
        
    }
    
    /**
     * Show all details of current session
     *
     */
    private void doShowSession() {
        
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>showSession</h1>");
        
        out.println("Session Id: " + request.getSession().getId() + "<br>");
        
        out.println("<br>");
        
        out.println("<h2>All session attributes:</h2>");
        Enumeration sessionAttributes = session.getAttributeNames();
        while (sessionAttributes.hasMoreElements()) {
            out.println((String) sessionAttributes.nextElement() + "<br>");
        }
        
        out.println("</body>");
        out.println("</html>");
        
    }
    
    /**
     *
     */
    private void doInvalidateSession() {
        request.getSession().invalidate();
    }
    
}