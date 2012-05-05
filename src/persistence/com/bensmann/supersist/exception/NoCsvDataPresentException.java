/*
 * Created on Jul 24, 2003
 *
 */
package com.bensmann.supersist.exception;

/**
 * @author rb
 * @version $Id: NoCsvDataPresentException.java,v 1.1 2005/07/19 15:51:38 rb Exp $
 *
 * Ausnahme, wenn die Klasse CsvData keine Daten hat,
 * aber danach gefragt wird (waehre sonst eine
 * ArrayIndexOutOfBoundsException)
 * 
 */
public class NoCsvDataPresentException extends Exception {

    public NoCsvDataPresentException() {
    }

    public NoCsvDataPresentException(String message) {
        super(message);
    }

}
