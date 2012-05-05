/*
 * TestBean.java
 *
 * Created on 13. Mai 2006, 12:04
 *
 */

package com.bensmann.supersist.datamodel;

import com.bensmann.supersist.datamodel.transfer.DatamodelTransferObject;
import java.util.Date;

/**
 *
 * @author rb
 */
public class TestBean implements DatamodelTransferObject {
    
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

    public void setTableOrView(String tableOrViewName) {
    }

    public void setProcedure(String packageName, String procedureName) {
    }
    
}
