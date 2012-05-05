/*
 * Created on Dec 21, 2004
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

import java.util.Random;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author rb
 * @version $Id: MyDesktop.java,v 1.1 2005/07/19 15:51:38 rb Exp $
 *
 */
public class MyDesktop extends JFrame {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3546359543836850231L;
    
    private JDesktopPane jDesktop = new JDesktopPane();
    
    private JMenuBar menuBar = new JMenuBar();
    
    private Random rand = new Random();
    
    public MyDesktop() {
        
        super("DesktopBuilder");
        initComponents();
        
    }
    
    private void initComponents() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(jDesktop);
        setSize(800, 600);
        
        createMenuBar();
        
        setVisible(true);
        
    }
    
    private void createMenuBar() {
        setJMenuBar(MenuCreator.getInstance().getMenuBar("bnmUserMenus"));
    }
    
    public JInternalFrame addInternalToDesktop() {
        
        JInternalFrame internalFrame;
        internalFrame = new JInternalFrame("Ein internes Fenster", // title
                true, // resizable
                true, // closeable
                true, // maximizable
                true); // iconifiable
        
        internalFrame.setBounds(rand.nextInt(100), rand.nextInt(100),
                100 + rand.nextInt(400), 100 + rand.nextInt(300));
        internalFrame.getContentPane().add(new JScrollPane(new JTextArea()));
        internalFrame.setVisible(true);
        
        jDesktop.add(internalFrame);
        
        return internalFrame;
        
    }
    
    public static void main(String[] args) {
        MyDesktop j = new MyDesktop();
        j.addInternalToDesktop();
    }
    
}