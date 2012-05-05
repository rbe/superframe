/*
 * com/bensmann/supersist/datamodel/oracle/MyBean.java
 *
 * MyBean.java created on 16. Januar 2007, 12:15 by rb
 *
 * Copyright (C) 2006 Ralf Bensmann, java@bensmann.com
 *
 */

package com.bensmann.supersist.datamodel;

import com.bensmann.supersist.datamodel.annotation.ColumnValueNotZero;
import com.bensmann.supersist.datamodel.transfer.DatamodelTransferObject;
import java.util.Date;

/**
 *
 * @author rb
 * @version 1.0
 */
public class MyBean implements DatamodelTransferObject {
    
    @ColumnValueNotZero()
    private long mitarbeiterid;
    @ColumnValueNotZero()
    private String vorname;
    private String nachname;
    private Date geburtsdatum;
    private transient String istEgal;
    private String gibbetNicht;
    
    /**
     * Creates a new instance of MyBean
     */
    public MyBean() {
    }
    
    public void setMitarbeiterid(long mitarbeiterid) {
        this.mitarbeiterid = mitarbeiterid;
    }
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

    public void setTableOrView(String tableOrViewName) {
    }

    public void setProcedure(String packageName, String procedureName) {
    }
    
}
