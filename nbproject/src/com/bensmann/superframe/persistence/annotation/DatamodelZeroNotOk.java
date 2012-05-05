/*
 * DatamodelZeroNotOk.java
 *
 * Created on 11. Juli 2006, 17:44
 *
 */

package com.bensmann.superframe.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * $Header$
 * @author rb
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatamodelZeroNotOk {
}
