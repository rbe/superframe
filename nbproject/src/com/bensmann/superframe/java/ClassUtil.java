/*
 * ClassUtil.java
 *
 * Created on 15. Juli 2006, 17:07
 *
 */

package com.bensmann.superframe.java;

import com.bensmann.superframe.persistence.annotation.DatamodelZeroNotOk;
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
public final class ClassUtil {
    
    /**
     * Do not create a new instance of ClassUtil
     */
    private ClassUtil() {
    }
    
    /**
     * Try to find a certain method in a class
     */
    public static Method findMethodByName(Class clazz, String methodName,
            boolean ignoreCase) {
        
        Method foundMethod = null;
        String tmpMethodName = null;
        Method[] methods = clazz.getDeclaredMethods();
        
        if (ignoreCase) {
            methodName = methodName.toLowerCase();
        }
        
        // Look at every method in class
        for (Method method : methods) {
            
            foundMethod = method;
            tmpMethodName = foundMethod.getName();
            
            if (ignoreCase) {
                tmpMethodName = tmpMethodName.toLowerCase();
            }
            
            if (tmpMethodName.equals(methodName)) {
                break;
            }
            
            // We didn't find a method (break-statement inside if above
            // would have exited this loop before the following statement
            foundMethod = null;
            
        }
        
        return foundMethod;
        
    }
    
    /**
     * Look for a getter-method inside a class
     *
     */
    public static Method getGetterMethodByName(Class clazz, String fieldName,
            boolean ignoreCase) {
        
        return findMethodByName(clazz,
                "get" + StringUtil.toUpperCaseFirst(fieldName),
                ignoreCase);
        
    }
    
    /**
     * Look for a setter-method inside a class
     *
     */
    public static Method getSetterMethodByName(Class clazz, String fieldName,
            boolean ignoreCase) {
        
        return findMethodByName(clazz,
                "set" + StringUtil.toUpperCaseFirst(fieldName),
                ignoreCase);
        
    }
    
    /**
     *
     * @param field
     * @param object
     * @return
     */
    public static Object getValueFromField(Field field, Object object) {
        
        String genericString = null;
        String methodName = null;
        Method method = null;
        Annotation a = null;
        boolean zeroNotOk = false;
        Class clazz = null;
        Class[] emptyClassArray = new Class[] {};
        Class fieldType = null;
        Object value = null;
        
        clazz = object.getClass();
        genericString = field.toGenericString();
        fieldType = field.getType();
        
        // Check for annotation DatamodelZeroNotOk
        // If a field is marked by that annotation, non-null values like
        // zero (0) or empty strings are not tolerated
//        System.out.println("looking for annotations of "+field.getName());
//        Annotation[] annotations = field.getAnnotations();
//        for (Annotation annotation : annotations) {
//            System.out.println("annotation=" + annotation.annotationType());
//        }
        a = field.getAnnotation(DatamodelZeroNotOk.class);
        if (a != null) {
            zeroNotOk = true;
        }
        
        // Ignore transient fields
        if (genericString.contains("transient")) {
            value = null;
        } else if (genericString.contains("public")) {
            
            // public
            try {
                value = field.get(object);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                value = null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                value = null;
            }
            
        } else {
            
            // private, so use a getter
            method = getGetterMethodByName(clazz, field.getName(), false);
            try {
//                method = clazz.getMethod(methodName, emptyClassArray);
                value = method.invoke(object);
            }
//            catch (NoSuchMethodException e) {
//                //e.printStackTrace();
//                value = null;
//            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
                value = null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                value = null;
            }
            
        }
        
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
