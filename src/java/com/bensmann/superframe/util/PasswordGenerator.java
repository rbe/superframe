/*
 * Created on Nov 17, 2003
 *
 */
package com.bensmann.superframe.util;

import java.util.Random;

/**
 * Password Generator
 *
 * @author rb
 * @version $Id: PasswordGenerator.java,v 1.1 2005/07/19 15:51:39 rb Exp $
 */
public class PasswordGenerator {

    /**
     * Strength of password
     * L = low, M = medium, H = high
     */
    private char strength;

    /**
     * Length of password to generate
     */
    private int length;

    /**
     * 
     */
    private Random random;

    /**
     * Initialization on demand holder idiom
     */
    private static final class Holder {
        private static final PasswordGenerator INSTANCE = new PasswordGenerator('L', 8);
    }

    /**
     * Constructor: strength should be L, M or H.
     * @param strength
     * @param length
     */
    private PasswordGenerator(char strength, int length) {
        this.strength = Character.toUpperCase(strength);
        this.length = length;
        random = new Random();
    }

    /**
     * 
     * @return
     */
    public static PasswordGenerator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     *
     * @return 
     */
    public StringBuffer generate() {
        StringBuffer newPassword = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char c = Character.forDigit(random.nextInt(36), Character.MAX_RADIX);
            if (random.nextInt(2) > 0) {
                newPassword.append(Character.toUpperCase(c));
            } else {
                newPassword.append(c);
            }
        }
        String n = newPassword.toString();
        n = n.replace('0', '3');
        n = n.replace('1', '2');
        n = n.replace('l', '2');
        n = n.replace('O', '3');
        return newPassword;
    }

    /**
     * Returns a new generated password
     * @return 
     */
    public String get() {
        return generate().toString();
    }

}