/*
 * ClassUtil.java
 *
 * Created on 15. Juli 2006, 17:07
 *
 */

package com.bensmann.supersist.util;

import com.bensmann.superframe.exception.ReflectionException;
import com.bensmann.superframe.java.lang.ClassUtil;
import com.bensmann.supersist.datamodel.annotation.ColumnValueNotZero;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * $Header$
 * @author rb
 * @version $Id$
 * @date $Date$
 * @log $Log$
 */
public final class MyClassUtil extends ClassUtil {
    
    /**
     * Do not create a new instance of ClassUtil
     */
    private MyClassUtil() {
    }
    
    /**
     * 
     * 
     * @param field 
     * @param object 
     * @return 
     * @throws com.bensmann.superframe.exception.ReflectionException 
     */
    public static Object getValueFromField(Field field, Object object)
    throws ReflectionException {
        
        Annotation a = null;
        boolean zeroNotOk = false;
        Class fieldType = null;
        Object value = null;
        
        value = ClassUtil.getFieldValue(field, object);
        
        // Check for annotation DatamodelZeroNotOk
        // If a field is marked by that annotation, non-null values like
        // zero (0) or empty strings are not tolerated
        a = field.getAnnotation(ColumnValueNotZero.class);
        if (a != null) {
            zeroNotOk = true;
        }
        
        fieldType = field.getType();
        
        // We don't want zero values; set value to null
        if (zeroNotOk) {
            
            if (fieldType == String.class) {
                if (((String) value).equals("")) {
                    value = null;
                }
            } else if (fieldType == Integer.TYPE || fieldType == Integer.class) {
                
                if (((Integer) value) == 0) {
                    value = null;
                }
                
            } else if (fieldType == Long.TYPE || fieldType == Long.class) {
                
                System.out.println(field.getName() + " is 'long' = " + value);
                
                if (((Long) value) == 0l) {
                    value = null;
                }
                
            } else if (fieldType == Float.TYPE || fieldType == Float.class) {
                
                if (((Float) value) == 0.0f) {
                    value = null;
                }
                
            } else if (fieldType == Double.TYPE || fieldType == Double.class) {
                
                if (((Double) value) == 0.0d) {
                    value = null;
                }
                
            }
            
        }
        
        return value;
        
    }
    
}
