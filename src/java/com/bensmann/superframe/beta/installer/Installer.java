/*
 * Created on 08.07.2004
 *
 */
package com.bensmann.superframe.beta.installer;

import com.bensmann.superframe.beta.ui.Configuration;
import java.io.File;

import com.bensmann.superframe.beta.ui.UserInterfaceManager;
import com.bensmann.superframe.beta.ui.SplashScreen;

/**
 * @author rb
 * @version $Id: Installer.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 *
 */

public class Installer {
    
    private Configuration cfg = Configuration.getInstance();
    
    /**
     *
     *
     */
    public Installer() {
        
        // Splashscreen
        new Thread(new SplashScreen("Installer")).start();
        
        UserInterfaceManager.getInstance().setSystemLookAndFeel();
        
        cfg.mainFrame = new MainFrame();
        cfg.downloadManager = new DownloadManager(cfg.mainFrame);
        
                /*
                 * try { for (int i = 0; i < 10; i++) cfg.downloadManager
                 * .startDownload( new URL(
                 * "http://www.branchenloesungen.de/pdf/saferpay_flyer.pdf"), new
                 * File("C:\\test" + i + ".pdf")); } catch (Exception e) {
                 * e.printStackTrace(); }
                 */
        
        UserInterfaceManager.getInstance().setLocationInMiddleOfScreen(cfg.mainFrame);
        
        cfg.deploymentPanel = new DeploymentPanel();
        
        discoverPaths();
        
        cfg.mainFrame.getContentPane().add(new TabbedPane());
        cfg.mainFrame.setVisible(true);
        
    }
    
    /**
     * Discover paths of installations (using threads)
     *
     */
    public void discoverPaths() {
        
        // Discover dmerce installation path
        cfg.deploymentPanel.disableInstMethodElements();
        cfg.deploymentPanel.enableOfficeAddonPathButton();
        
        new Thread() {
                        /*
                         * (non-Javadoc)
                         *
                         * @see java.lang.Thread#run()
                         */
            public void run() {
                File f = DiscoverInstallationPath.discoverDmerceSoftwarePath();
                if (f != null) {
                    com.bensmann.superframe.beta.ui.Configuration.getInstance().discoveredDmerceInstPath = f;
                    cfg.deploymentPanel.dmerceSoftwareTextField.setText(f
                            .getAbsolutePath());
                }
            }
        }.run();
        
        cfg.deploymentPanel.dmerceSoftwarePathButton.setEnabled(true);
        
        if (com.bensmann.superframe.beta.ui.Configuration.getInstance().discoveredDmerceInstPath != null) {
            cfg.deploymentPanel.enableInstMethodElements();
        }
        
        // Discover Office installation path
        cfg.deploymentPanel.disableInstMethodElements();
        cfg.deploymentPanel.enableOfficeAddonPathButton();
        
        new Thread() {
                        /*
                         * (non-Javadoc)
                         *
                         * @see java.lang.Thread#run()
                         */
            public void run() {
                File f = DiscoverInstallationPath.discoverStarOfficePath();
                if (f != null) {
                    com.bensmann.superframe.beta.ui.Configuration.getInstance().discoveredOfficeInstPath = f;
                    cfg.deploymentPanel.officeAddonTextField.setText(f
                            .getAbsolutePath());
                }
            }
        }.run();
        
        cfg.deploymentPanel.officeAddonPathButton.setEnabled(true);
        
        if (com.bensmann.superframe.beta.ui.Configuration.getInstance().discoveredOfficeInstPath != null) {
            cfg.deploymentPanel.enableInstMethodElements();
        }
        
    }
    
    public static void main(String[] args) {
        new Installer();
    }
}