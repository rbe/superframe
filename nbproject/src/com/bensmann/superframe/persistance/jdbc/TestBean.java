/*
 * TestBean.java
 *
 * Created on 13. Mai 2006, 12:04
 *
 */

package com.bensmann.superframe.persistance.jdbc;

import java.util.Date;

/**
 *
 * <procedure nachname="MITARBEITER_HINZU" package-nachname="TIM_USER" argument-count="16">
 * <argument nachname="P_EMAIL" position="16" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="50" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_MOBILTELEFON" position="15" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="20" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_TELEFON" position="14" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="20" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_ORTPRIVAT" position="13" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="100" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_PLZPRIVAT" position="12" in-out="IN" java-type="java.lang.String" data-type="CHAR" data-length="5" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_ANSCHRIFTPRIVAT" position="11" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="100" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_JAHRESURLAUB_TAGE" position="10" in-out="IN" java-type="java.lang.BigDecimal" data-type="NUMBER" data-length="22" data-precision="2" data-scale="0" />
 * <argument nachname="P_SOLLSTUNDEN_MONAT" position="9" in-out="IN" java-type="java.lang.BigDecimal" data-type="NUMBER" data-length="22" data-precision="3" data-scale="0" />
 * <argument nachname="P_ENDZEIT" position="8" in-out="IN" java-type="java.util.Date" data-type="DATE" data-length="unknown" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_STARTZEIT" position="7" in-out="IN" java-type="java.util.Date" data-type="DATE" data-length="unknown" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_GEBURTSDATUM" position="6" in-out="IN" java-type="java.util.Date" data-type="DATE" data-length="unknown" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_NIEDERLASSUNGID" position="5" in-out="IN" java-type="java.lang.BigDecimal" data-type="NUMBER" data-length="22" data-precision="38" data-scale="0" />
 * <argument nachname="P_NACHNAME" position="4" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="50" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_VORNAME" position="3" in-out="IN" java-type="java.lang.String" data-type="VARCHAR2" data-length="20" data-precision="unknown" data-scale="unknown" />
 * <argument nachname="P_ERSTELLT_MITARBEITERID" position="2" in-out="IN" java-type="java.lang.BigDecimal" data-type="NUMBER" data-length="22" data-precision="38" data-scale="0" />
 * <argument nachname="V_MITARBEITERID" position="1" in-out="OUT" java-type="java.lang.BigDecimal" data-type="NUMBER" data-length="22" data-precision="38" data-scale="0" />
 * </procedure>
 *
 * $Header$
 *
 * @author rb
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
public class TestBean implements DatamodelBean {
    
    String email;
    String fax;
    String telefon;
    String ortprivat;
    String plzprivat;
    String anschriftprivat;
    String ansprechpartner;
    String vorname;
    String nachname;
    String mobiltelefon;
    int jahresurlaubTage;
    int sollstundenMonat;
    Date startzeit;
    Date endzeit;
    Date geburtsdatum;
    int niederlassungid;
    int erstelltMitarbeiterId;
    long id;
    
    /*
    public String getP_EMAIL(){return email;}
    public String getP_FAX() {return fax;}
    public String getP_TELEFON() {return telefon;}
    public String getP_ORTPRIVAT() {return ortprivat;}
    public String getP_PLZPRIVAT() {return plzprivat;}
    public String getP_ANSCHRIFTPRIVAT() {return anschriftprivat;}
    public String getP_ANSPRECHPARTNER() {return ansprechpartner;}
    public String getP_NACHNAME() {return nachname;}
    public String getP_VORNAME() {return vorname;}
    public String getP_MOBILTELEFON() {return mobiltelefon;}
    public int getP_JAHRESURLAUB_TAGE() {return jahresurlaubTage;}
    public int getP_SOLLSTUNDEN_MONAT() {return sollstundenMonat;}
    public Date getP_STARTZEIT() {return startzeit;}
    public Date getP_ENDZEIT() {return endzeit;}
    public Date getP_GEBURTSDATUM() {return geburtsdatum;}
    public int getP_NIEDERLASSUNGID() {return niederlassungid;}
    public int getP_ERSTELLT_MITARBEITERID() {return erstelltMitarbeiterId;}
    public void setV_MITARBEITERID(long id) {this.id=id;}
     */
    public String getEmail(){return email;}
    public String getFax() {return fax;}
    public String getTelefon() {return telefon;}
    public String getOrtprivat() {return ortprivat;}
    public String getPlzprivat() {return plzprivat;}
    public String getAnschriftprivat() {return anschriftprivat;}
    public String getAnsprechpartner() {return ansprechpartner;}
    public String getNachname() {return nachname;}
    public String getVorname() {return vorname;}
    public String getMobiltelefon() {return mobiltelefon;}
    public int getJahresurlaub_tage() {return jahresurlaubTage;}
    public int getSollstunden_monat() {return sollstundenMonat;}
    public Date getStartzeit() {return startzeit;}
    public Date getEndzeit() {return endzeit;}
    public Date getGeburtsdatum() {return geburtsdatum;}
    public int getNiederlassungid() {return niederlassungid;}
    public int getErstellt_mitarbeiterid() {return erstelltMitarbeiterId;}
    public void setMitarbeiterid(long id) {this.id=id;}
    
    public void reset() {
        email=null;
        fax=null;
        telefon=null;
        ortprivat=null;
        plzprivat=null;
        anschriftprivat=null;
        ansprechpartner=null;
        vorname=null;
        nachname=null;
        mobiltelefon=null;
        jahresurlaubTage=0;
        sollstundenMonat=0;
        startzeit=null;
        endzeit=null;
        geburtsdatum=null;
        niederlassungid=0;
        erstelltMitarbeiterId=0;
        id=0;
    }
    
}
