/*
 * com/bensmann/supersist/datamodel/AbstractDatamodel.java
 *
 * AbstractDatamodel.java created on 17. Januar 2007, 20:18 by rb
 *
 * Copyright (C) 2006-2007 Ralf Bensmann, java@bensmann.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA
 *
 */

package com.bensmann.supersist.datamodel.inspector.impl;

import com.bensmann.supersist.datamodel.inspector.ArgumentInspector;
import com.bensmann.supersist.datamodel.inspector.InspectorDocument;
import com.bensmann.supersist.datamodel.inspector.ProcedureInspector;
import com.bensmann.supersist.datamodel.inspector.StatementInspector;
import com.bensmann.supersist.datamodel.inspector.TableInspector;
import com.bensmann.supersist.datamodel.inspector.ViewInspector;
import com.bensmann.supersist.datamodel.transfer.DatamodelTransferObject;
import com.bensmann.supersist.exception.DatamodelException;
import java.sql.Connection;

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
 * </ul>
 * </p>
 *
 * TODO Cache datamodel at system-wide-level; e.g. store it into config.xml with timestamp included
 *
 * @author rb
 * @version 1.0
 */
public class AbstractInspector extends AbstractDatamodelInspector {
    
    /**
     * Inspector XML document
     */
    private InspectorDocument inspectorDocument;
    
    /**
     * JDBC database connection
     */
    private Connection connection;
    
    /**
     *
     */
    private TableInspector tableInspector;
    
    /**
     *
     */
    private ViewInspector viewInspector;
    
    /**
     *
     */
    private ProcedureInspector procedureInspector;
    
    /**
     *
     */
    private ArgumentInspector argumentInspector;
    
    /**
     *
     */
    private StatementInspector statementInspector;
    
    /**
     * Creates a new instance of AbstractDatamodel
     */
    public AbstractInspector() {
        inspectorDocument = InspectorDocument.getInstance();
    }
    
    /**
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Set JDBC database connection
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    /**
     *
     * @return
     */
    public TableInspector getTableInspector() {
        return tableInspector;
    }
    
    /**
     *
     * @param tableInspector
     */
    public void setTableInspector(TableInspector tableInspector) {
        this.tableInspector = tableInspector;
    }
    
    /**
     *
     * @return
     */
    public ViewInspector getViewInspector() {
        return viewInspector;
    }
    
    /**
     *
     * @param viewInspector
     */
    public void setViewInspector(ViewInspector viewInspector) {
        this.viewInspector = viewInspector;
    }
    
    /**
     *
     * @return
     */
    public ProcedureInspector getProcedureInspector() {
        return procedureInspector;
    }
    
    /**
     *
     * @param procedureInspector
     */
    public void setProcedureInspector(ProcedureInspector procedureInspector) {
        this.procedureInspector = procedureInspector;
    }
    
    /**
     *
     * @return
     */
    public ArgumentInspector getArgumentInspector() {
        return argumentInspector;
    }
    
    /**
     *
     * @param argumentInspector
     */
    public void setArgumentInspector(ArgumentInspector argumentInspector) {
        this.argumentInspector = argumentInspector;
    }
    
    /**
     *
     * @return
     */
    public StatementInspector getStatementInspector() {
        return statementInspector;
    }
    
    /**
     *
     * @param statementInspector
     */
    public void setStatementInspector(StatementInspector statementInspector) {
        this.statementInspector = statementInspector;
    }
    
    /**
     *
     * @return
     * @throws com.bensmann.supersist.exception.DatamodelException
     */
    public String[] getTableAndViewNames() throws DatamodelException {
        
        String[] t = getTableInspector().getTableNames();
        String[] v = getViewInspector().getViewNames();
        String[] tmp = new String[t.length + v.length];
        
        System.arraycopy(t, 0, tmp, 0, t.length);
        System.arraycopy(v, 0, tmp, t.length, v.length);
        
        return tmp;
        
    }
    
    public DatamodelTransferObject[] selectIntoTransferObject(String tableOrView, DatamodelTransferObject datamodelTransferObject, String whereClause) throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public void applyTransferObjectToProcedure(Object datamodelTransferObject, String packageName, String procedureName) throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public void inspectViews() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public void inspectTables() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public void inspectProcedures() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public void inspectFunctions() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public String[] getProcedureAndFunctionNames() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public DatamodelTransferObject[] getAllDatamodelTransferObjects() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public void generateDatamodelTransferObjects() throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
    public DatamodelTransferObject getDatamodelTransferObject(String name) throws DatamodelException {
        throw new UnsupportedOperationException();
    }
    
}
