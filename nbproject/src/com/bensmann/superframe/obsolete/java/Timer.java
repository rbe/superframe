/*
 * Timer.java
 *
 * Created on 18. Mai 2005, 13:20
 *
 */

package com.bensmann.superframe.obsolete.java;

/**
 *
 * @author Ralf Bensmann
 * @version $Id: Timer.java,v 1.1 2005/07/19 15:51:39 rb Exp $
 */
public class Timer {
    
    private long startTime;
    
    private long stopTime;
    
    /**
     * Creates a new instance of Timer
     */
    public Timer() {
    }
    
    public void start() {
        startTime = System.nanoTime();
        stopTime = startTime;
    }
    
    public void stop() {
        stopTime = System.nanoTime();
    }
    
    /**
     * 
     * @return 
     */
    public long getDelta() {
        return stopTime - startTime;
    }
    
    /**
     * 
     * @return 
     */
    public double getDeltaInMs() {
        return (stopTime - startTime) / 1000 / 1000;
    }
    
}
