package com.bensmann.superframe.obsolete.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Das Interface der Queue-Collection.
 */
public interface Queue {
    
    /**
     * F�gt das Element o am Ende der Queue an. Falls
     * keine Ausnahme ausgel�st wurde, gibt die Methode
     * true zur�ck.
     *
     * @param o Ein Element/beliebiges Object
     */
    boolean add(Object o);
    
    /**
     * Liefert das erste Element der Queue und entfernt es
     * daraus. Falls die Queue leer ist, wird eine Ausnahme
     * des Typs NoSuchElementException ausgel�st.
     *
     * @exception NoSuchElementException
     */
    Object retrieve() throws NoSuchElementException;
    
    /**
     * Liefert die Anzahl der Elemente der Queue.
     */
    int size();
    
    /**
     * Entfernt alle Elemente aus der Queue.
     */
    void clear();
    
    /**
     * Liefert einen Iterator �ber alle Elemente der Queue.
     */
    Iterator iterator();
}