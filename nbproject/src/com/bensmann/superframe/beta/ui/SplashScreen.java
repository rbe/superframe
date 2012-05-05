package com.bensmann.superframe.beta.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.BevelBorder;

public class SplashScreen extends JWindow implements Runnable {
    
    /**
     *
     */
    private JPanel panel;
    
    /**
     * Show splash screen for x ms
     */
    private int delay = 2000;
    
    /**
     * Image used in splash screen
     */
    protected ImageIcon image = new ImageIcon(this.getClass().getResource(
            "1ci_splashscreen.gif"));
    
    /**
     * The subtitle is displayed below the image
     */
    private String subtitle;
    
    /**
     * 
     * @param subtitle 
     */
    public SplashScreen(String subtitle) {
        this.subtitle = subtitle;
    }
    
    /**
     * 
     * @param subtitle 
     * @param delay 
     */
    public SplashScreen(String subtitle, int delay) {
        this.subtitle = subtitle;
        this.delay = delay;
    }
    
    /**
     *
     */
    public void initializeUI() {
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createBevelBorder(BevelBorder.RAISED), BorderFactory
                .createEtchedBorder()));
        panel.add(new JLabel(image, JLabel.CENTER), BorderLayout.CENTER);
        panel.add(new JLabel(subtitle, JLabel.CENTER), BorderLayout.SOUTH);
        
        getContentPane().add(panel);
        
    }
    
    /**
     *
     */
    private void showSplashScreen() {
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        
        setLocation(dimension.width / 3, dimension.height / 3);
        setSize((int) (image.getIconWidth() * 1.2), (int) (image
                .getIconHeight() * 1.2));
        setVisible(true);
        
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
        
        setVisible(false);
        dispose();
        
    }
    
    /**
     *
     */
    public void run() {
        initializeUI();
        showSplashScreen();
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        new SplashScreen("HLWerk Admin").run();
        System.exit(0);
    }
    
}
