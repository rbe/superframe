/*
 * ThreadTest.java
 *
 * Created on 5. August 2005, 14:33
 *
 */

package com.bensmann.superframe.test.java;

import com.bensmann.superframe.java.Debug;

class Data {
    
    public String aString = "aString";
    
}

/**
 * Two threads do basically(!) not belong to each other in any way
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
class Test1 implements Runnable {
    
    private boolean doWork = true;
    
    private Data data;
    
    public Test1() {
        data = new Data();
    }
    
    public void stopWork() {
        Debug.log("Stopping work");
        doWork = false;
    }
    
    public void run() {
        
        int counter = 0;
        
        Debug.log("Instance field aString when begin work=" +
                data + ": " + data.aString);
        
        while (doWork) {
            
            // Read instance field aString and show it
            Debug.log("Content of aString before modification by me: " +
                    data + ": " + data.aString);
            // Modify ...
            data.aString = "Modified by " + Thread.currentThread().getId() +
                    "#" + counter++;
            // Show it again
            Debug.log("Instance field aString=" + data + ": " + data.aString);
            
            Thread.yield();
            try {
                Thread.sleep((int) (600 * Math.random()));
            } catch (InterruptedException e) {
                
            }
            
        }
        
    }
    
}

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class ThreadTest {
    
    /** Creates a new instance of ThreadTest */
    public ThreadTest() {
    }
    
    public static void main(String[] args) throws Exception {
        
        Test1 test11 = new Test1();
        Test1 test12 = new Test1();
        
        Thread t11 = new Thread(test11);
        Thread t12 = new Thread(test12);
        t11.start();
        t12.start();
        
        Thread.sleep(5000);
        Debug.log("Stopping threads");
        test11.stopWork();
        test12.stopWork();
        t11.join();
        t12.join();
        
    }
    
}
