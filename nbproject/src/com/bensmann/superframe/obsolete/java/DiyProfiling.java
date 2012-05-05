/*
 * Created on Jun 5, 2003
 *
 */
package com.bensmann.superframe.obsolete.java;

import java.util.HashMap;
import java.util.Map;

/**
 * Do it yourself Profiling
 *
 * Beispiel:
 *
 * public void schlechtePerformance() {
 *
 * 		DyiProfiling dyip = new DyiProfiling("Ident");
 *
 * 		dyip.addTimer("ersteAufgabe");
 * 		tueEinPaarEchtLangDauerndeDinger();
 * 		dyip.stopTimer("ersteAufgabe");
 *
 * 		dyip.dump();
 *
 * }
 *
 * @author rb
 * @version $Id: DiyProfiling.java,v 1.1 2005/07/19 15:51:38 rb Exp $
 */
public class DiyProfiling {
    
    /**
     *
     */
    private String ident;
    
    /**
     *
     */
    //private String subIdent;
    
    /**
     *
     */
    private HashMap startTime = new HashMap();
    
    /**
     *
     */
    private Map stopTime = new HashMap();
    
    /**
     *
     * @param ident
     */
    public DiyProfiling(String ident) {
        this.ident = ident;
    }
    
    /**
     *
     *
     */
    public void addTimer(String subIdent) {
        startTime.put(subIdent, new Long(System.currentTimeMillis()));
    }
    
    /**
     *
     *
     */
    public void dump() {
    }
    
    /**
     *
     * @return
     */
    public String getTimeElapsed(String subIdent) {
        
        return (
                ((Long) stopTime.get(subIdent)).longValue()
                - ((Long) startTime.get(subIdent)).longValue())
                + " ms";
        
    }
    
    /**
     *
     *
     */
    public void stopTimer(String subIdent) {
        stopTime.put(subIdent, new Long(System.currentTimeMillis()));
    }
    
}