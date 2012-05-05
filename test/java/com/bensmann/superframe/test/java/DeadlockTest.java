/*
 * DeadlockTest.java
 *
 * Created on 4. August 2005, 09:50
 *
 */

package com.bensmann.superframe.test.java;

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class DeadlockTest {
    
    static Object a = new Object();
    static Object b = new Object();
    
    static class T1 extends Thread {
        
        public void run() {
            
            synchronized( a ) {
                System.out.println( "T1: Lock auf a bekommen" );
                warte();
                synchronized( b ) {
                    System.out.println( "T1: Lock auf b bekommen" );
                    
                }
            }
            
        }
        
        private void warte() {
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            
        }
        
    }
    
    static class T2 extends Thread {
        
        public void run() {
            
            synchronized(b) {
                
                System.out.println("T2: Lock auf b bekommen");
                synchronized(a) {
                    System.out.println("T2: Lock auf a bekommen");
                }
                
            }
            
        }
        
    }
    
    public static void main( String args[] ) {
        new T1().start();
        new T2().start();
    }
    
}