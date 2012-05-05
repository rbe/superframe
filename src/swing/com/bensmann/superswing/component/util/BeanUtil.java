/*
 * BeanUtil.java
 *
 * Created on 20. Mai 2007, 18:24
 *
 */

package com.bensmann.superswing.component.util;

import com.bensmann.superframe.java.lang.ReflectionFacade;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author rb
 */
public class BeanUtil {
    
    /**
     *
     */
    private static Logger logger;
    
    static {
        logger = Logger.getLogger(BeanUtil.class.getName());
    }
    
    /** Creates a new instance of BeanUtil */
    public BeanUtil() {
    }
    
    private static boolean compareMethodName(String m1, String m2, String... skip) {
        
        boolean b = false;
        String[] mySkip = { "get", "set", "TextField", "TextArea", "RadioButton", "ComboBox" };
        String[] myArr = new String[mySkip.length + skip.length];
        System.arraycopy(mySkip, 0, myArr, 0, mySkip.length);
        System.arraycopy(skip, 0, myArr, mySkip.length, skip.length);
        
        for (String s : myArr) {
            m1 = m1.replaceAll(s, "");
            m2 = m2.replaceAll(s, "");
        }
        
        if (m1.indexOf(m2) >= 0) {
            b = true;
        }
//        System.out.println(m1+" = "+m2+"? " + b);
        
        return b;
        
    }
    
    /**
     *
     * @param bean
     * @param component
     */
    public static void applyBeanToTextComponent(Object bean, Component component) {
        applyBeanToTextComponent(bean, component, (String[]) null);
    }
    
    /**
     *
     * @param bean
     * @param component
     */
    public static void applyBeanToTextComponent(Object bean, Component component, String... skip) {
        
        ReflectionFacade beanReflectionFacade = new ReflectionFacade(bean);
        ReflectionFacade componentReflectionFacade = new ReflectionFacade(component);
        
        Class beanClazz = bean.getClass();
        Class componentClass = component.getClass();
        String beanGetterMethodName = null;
        String componentGetterMethodName = null;
        String tmp = null;
        //JTextField textField = null;
        Object obj = null;
        
        for (Method beanGetterMethod : beanReflectionFacade.getPublicGetterMethods()) {
            
            beanGetterMethodName = beanGetterMethod.getName();
            
            for (Method panelMethod : componentReflectionFacade.getPublicGetterMethods()) {
                
                componentGetterMethodName = panelMethod.getName();
                
                if (compareMethodName(beanGetterMethodName, componentGetterMethodName, skip)) {
                    
                    try {
                        
                        obj = panelMethod.invoke(component);
                        
                        // JTextField
                        if (obj instanceof JTextField) {
                            //textField = (JTextField) panelMethod.invoke(component);
                            ((JTextField) panelMethod.invoke(component)).
                                    setText("" + beanGetterMethod.invoke(bean));
                        }
                        // JComboBox
                        else if (obj instanceof JComboBox) {
                            ((JComboBox) panelMethod.invoke(component)).
                                    setSelectedItem(beanGetterMethod.invoke(bean));
                        }
                        
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (NullPointerException e) {
                        System.out.println("applyBeanToTextComponent: nullpointer with " + panelMethod + " -> " + componentGetterMethodName);
                    } catch (ClassCastException e) {
                        System.out.println("applyBeanToTextComponent: classcastexception with " + panelMethod + " -> " + componentGetterMethodName);
                    }
                    
                }
                
            }
            
        }
        
        beanClazz = null;
        componentClass = null;
        beanGetterMethodName = null;
        tmp = null;
        obj = null;
        //textField = null;
        
    }
    
    /**
     *
     * @param component
     * @param bean
     */
    public static void applyTextComponentToBean(Component component, Object bean) {
        applyTextComponentToBean(component, bean, "");
    }
    
    /**
     *
     * @param component
     * @param bean
     * @param skip
     */
    public static void applyTextComponentToBean(
            Component component, Object bean, String... skip) {
        
        ReflectionFacade beanReflectionFacade = new ReflectionFacade(bean);
        ReflectionFacade componentReflectionFacade = new ReflectionFacade(component);
        
        Class beanClazz = bean.getClass();
        Class componentClass = component.getClass();
        java.lang.String beanSetterMethodName = null;
        String componentGetterMethodName = null;
        java.lang.String tmp = null;
        //JTextField textField = null;
        Object obj = null;
        
        for (Method beanSetterMethod : beanReflectionFacade.getPublicSetterMethods()) {
            
            beanSetterMethodName = beanSetterMethod.getName();
            
            for (Method componentGetterMethod : componentReflectionFacade.getPublicGetterMethods()) {
                
                componentGetterMethodName = componentGetterMethod.getName();
                
                if (compareMethodName(beanSetterMethodName, componentGetterMethodName, skip)) {
                    
                    try {
                        
                        //textField = (JTextField) componentGetterMethod.invoke(component);
                        //beanSetterMethod.invoke(bean, textField.getText());
                        
                        obj = componentGetterMethod.invoke(component);
                        
                        // JTextField
                        if (obj instanceof JTextField) {
                            beanSetterMethod.invoke(bean, ((JTextField) obj).getText());
                        }
                        // JComboBox
                        else if (obj instanceof JComboBox) {
                            beanSetterMethod.invoke(bean, ((JComboBox) obj).getSelectedItem());
                        }
                        
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (NullPointerException e) {
                        System.out.println("applyTextComponentToBean: nullpointer with " + componentGetterMethodName);
                    } catch (ClassCastException e) {
                        System.out.println("applyTextComponentToBean: classcastexception with " + componentGetterMethodName);
                    }
                    
                }
                
            }
            
        }
        
        beanClazz = null;
        componentClass = null;
        beanSetterMethodName = null;
        tmp = null;
        //textField = null;
        obj = null;
        
    }
    
    /**
     *
     * @param component
     */
    public static void clearTextComponent(Component component, String... pattern) {
        
        ReflectionFacade componentReflectionFacade = new ReflectionFacade(component);
        Object o = null;
        Method[] methods = null;
        
        if (pattern.length > 0) {
            methods = componentReflectionFacade.getPublicGetterMethods(pattern);
        } else {
            methods = componentReflectionFacade.getPublicGetterMethods();
        }
        
        for (Method method : methods) {
            
            System.out.println("clearTextComponent: " + method.getName());
            
            try {
                o = method.invoke(component);
                if (o instanceof JTextComponent) {
                    ((JTextComponent) o).setText("");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
}
