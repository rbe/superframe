/*
 * GenericsTest.java
 *
 * Created on 2. August 2005, 12:45
 *
 */

package com.bensmann.superframe.test.java;

class MyGeneric<T> {
    
    public T test() {
        return (T) "11";
    }
    
}

/**
 *
 * @author Ralf Bensmann
 * @version $Id$
 */
public class GenericsTest {
    
    /** Creates a new instance of GenericsTest */
    public GenericsTest() {
    }
    
    public static void main(String[] args) {
        
        MyGeneric<Double> m = new MyGeneric<Double>();
        System.out.println("m=" + m +
                "test=" + m.test());
        
    }
    
}
