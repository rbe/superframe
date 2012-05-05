package com.bensmann.superframe.beta.ui;

import java.io.File;

import javax.swing.ImageIcon;

import com.bensmann.superframe.beta.installer.DeploymentPanel;
import com.bensmann.superframe.beta.installer.DownloadManager;
import com.bensmann.superframe.beta.installer.MainFrame;

/**
 * Configuration holder
 */
public class Configuration {
    
    /**
     * Singleton
     */
    private static Configuration singleton;
    
    /**
     * Debug flag
     */
    public final boolean DEBUG = false;
    
    /**
     * Image: Open
     */
    public final ImageIcon imageOpen = new ImageIcon(this.getClass().
            getResource("open.gif"));
    
    /**
     * Icons
     */
    public ImageIcon imageHelp = new ImageIcon(this.getClass().
            getResource("help.gif"));
    
    public ImageIcon imageInfo = new ImageIcon(this.getClass().
            getResource("info.gif"));
    
    public ImageIcon imageExit = new ImageIcon(this.getClass().
            getResource("exit.gif"));
    
    public ImageIcon imageConfig = new ImageIcon(this.getClass().
            getResource("config.gif"));
    
    public ImageIcon imageUpdate = new ImageIcon(this.getClass().
            getResource("update.gif"));
    
    public ImageIcon imageWeb = new ImageIcon(this.getClass().
            getResource("web.gif"));
    
    public ImageIcon imageSearch = new ImageIcon(this.getClass().
            getResource("search.gif"));
    
    public ImageIcon imagePrint = new ImageIcon(this.getClass().
            getResource("print.gif"));
    
    public ImageIcon imagePDF = new ImageIcon(this.getClass().
            getResource("pdf.gif"));
    
    /**
     * Prefix string
     */
    public final String versionPrefix = "1[IntelligentQuery]";
    
    /**
     * Version string of IntelligentQuery
     */
    public final String versionString = "1.5.7";
    
    /**
     * Current directory for JFileChooser
     */
    public String currentDirectory;
    
    /**
     * Automatically discorvered Office installation path
     */
    public File discoveredOfficeInstPath;
    
    /**
     * Automatically discorvered dmerce installation path
     */
    public File discoveredDmerceInstPath;
    
    /**
     *
     */
    public MainFrame mainFrame;
    
    /**
     *
     */
    public DeploymentPanel deploymentPanel;
    
    /**
     *
     */
    public DownloadManager downloadManager;
    
    /**
     * Singleton pattern: private constructor
     */
    private Configuration() {
    }
    
    /**
     *
     * @return
     */
    public static Configuration getInstance() {
        
        if (singleton == null) {
            singleton = new Configuration();
        }
        
        return singleton;
    }
    
}