/*
 * Created on 20.07.2004
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

/**
 * Calculate transfer rate and update label in downloadPanel
 *
 * @author rb
 *
 */
class SpeedTimer extends TimerTask {
    
    private Downloader downloader;
    
    private DownloadPanel downloadPanel;
    
    public SpeedTimer(Downloader downloader, DownloadPanel downloadPanel) {
        
        this.downloader = downloader;
        this.downloadPanel = downloadPanel;
        
        // Timer
        Timer timer = new Timer();
        timer.schedule(this, 0, 10 * 1000);
        
    }
    
        /*
         * (non-Javadoc)
         *
         * @see java.util.TimerTask#run()
         */
    public void run() {
        update();
    }
    
    protected void update() {
        double speed = 0;
        double delta = (System.currentTimeMillis() - downloader.startTime) * 1000;
        if (downloader.bytesCounter > 0 && delta > 0) {
            speed = downloader.bytesCounter / delta;
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            downloadPanel.getSpeedLabel().setText(nf.format(speed * 1024) +
                    " kB/sec");
        }
    }
    
}

/**
 * Panel for downloading files using threads
 *
 * @author rb
 *
 */

class DownloadPanel extends JPanel {
    
    URL url;
    
    JLabel urlLabel = new JLabel();
    
    JLabel speedLabel = new JLabel();
    
    JButton suspendButton = new JButton();
    
    JProgressBar progressBar = new JProgressBar();
    
    public DownloadPanel(URL url) {
        this.url = url;
        init();
    }
    
    public JProgressBar getProgressBar() {
        return progressBar;
    }
    
    public JLabel getSpeedLabel() {
        return speedLabel;
    }
    
    public JButton getSuspendButton() {
        return suspendButton;
    }
    
    public JLabel getUrlLabel() {
        return urlLabel;
    }
    
    private void init() {
        
        urlLabel.setText(url.toExternalForm());
        
        progressBar.setMaximum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);
        
        setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.insets = new Insets(0, 5, 5, 5);
        
        // Buttons
        suspendButton.setText("Pause");
        constr.weightx = 0.2;
        constr.gridx = 0;
        constr.gridy = 1;
        add(suspendButton, constr);
        constr.weightx = 0.2;
        constr.gridx = 1;
        constr.gridy = 1;
        add(new JButton("test1"), constr);
        constr.weightx = 0.2;
        constr.gridx = 2;
        constr.gridy = 1;
        add(new JButton("test2"), constr);
        constr.weightx = 0.2;
        constr.gridx = 3;
        constr.gridy = 1;
        add(new JButton("test3"), constr);
        
        // Label for URL
        constr.weightx = 0.5;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridwidth = 4;
        add(urlLabel, constr);
        
        // Label for download speed
        constr.weightx = 0.3;
        constr.gridx = 4;
        constr.gridy = 0;
        add(speedLabel, constr);
        
        // Progressbar
        constr.weightx = 0.3;
        constr.gridx = 4;
        constr.gridy = 1;
        add(progressBar, constr);
        
    }
    
}

/**
 * Download a file from an URL in a thread. If a DownloadPanel is set, we set
 * the progress of downloading from an URL.
 *
 * @author rb
 *
 */

class Downloader implements Runnable {
    
    private URL url;
    
    private URLConnection urlConnection;
    
    private int contentLength = 0;
    
    private File destinationFile;
    
    private FileWriter fileWriter;
    
    private BufferedReader bufferedReader;
    
    private boolean doDownload = false;
    
    private boolean suspendDownload = false;
    
    private boolean downloadCompleted = false;
    
    private DownloadPanel downloadPanel = null;
    
    protected int bytesCounter;
    
    protected final long startTime = System.currentTimeMillis();
    
    /**
     *
     * @param url
     * @param destinationFile
     * @param progressBar
     * @throws IOException
     */
    public Downloader(URL url, File destinationFile, DownloadPanel downloadPanel)
    throws IOException {
        
        this.url = url;
        this.destinationFile = destinationFile;
        this.downloadPanel = downloadPanel;
        
        doDownload = true;
        fileWriter = new FileWriter(destinationFile);
        urlConnection = url.openConnection();
        contentLength = urlConnection.getContentLength();
        
    }
    
    public Downloader(URL url, File destinationFile) throws IOException {
        
        this.url = url;
        this.destinationFile = destinationFile;
        
        doDownload = true;
        fileWriter = new FileWriter(destinationFile);
        urlConnection = url.openConnection();
        contentLength = urlConnection.getContentLength();
        
    }
    
    public void resumeDownload() {
        suspendDownload = false;
    }
    
    public void stopDownload() {
        doDownload = false;
        destinationFile.delete();
    }
    
    public void suspendDownload() {
        suspendDownload = true;
    }
    
        /*
         * (non-Javadoc)
         *
         * @see java.lang.Runnable#run()
         */
    public void run() {
        
        int i;
        
        while (!doDownload) {
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            
        }
        
        SpeedTimer speedTimer = new SpeedTimer(this, downloadPanel);
        
        while (doDownload && !downloadCompleted) {
            
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            
            try {
                
                if (bufferedReader != null) {
                    
                    while ((i = bufferedReader.read()) != -1) {
                        
                        fileWriter.write(i);
                        
                        if (downloadPanel != null) {
                            
                            double t;
                            t = (++bytesCounter / (double) contentLength) * 100;
                            downloadPanel.getProgressBar().setValue((int) t);
                            
                        }
                        
                    }
                    
                    fileWriter.close();
                    
                    downloadCompleted = true;
                    if (downloadPanel != null) {
                        
                        downloadPanel.getProgressBar().setValue(100);
                        speedTimer.cancel();
                        speedTimer.update();
                        
                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}

/**
 * @author rb
 * @version $Id: DownloadManager.java,v 1.1 2005/07/19 15:51:38 rb Exp $
 *
 */

//public class DownloadManager extends JPanel {
public class DownloadManager extends JFrame {
    
    private URL url;
    
    private JFrame jFrame;
    
    private JPanel downloaderPanel = new JPanel();
    
    private JScrollPane scrollPane = new JScrollPane();
    
    /**
     *
     * @param url
     * @param destinationFile
     */
    public DownloadManager(JFrame jFrame) {
        this.jFrame = jFrame;
        createAndShowGUI();
    }
    
    public DownloadManager() {
        createAndShowGUI();
    }
    
    /**
     * Start a download
     *
     * @throws IOException
     */
    public Downloader startDownload(URL url, File destinationFile)
    throws IOException {
        
        DownloadPanel downloadPanel = new DownloadPanel(url);
        downloaderPanel.add(downloadPanel);
        
        //jFrame.setVisible(true);
        setVisible(true);
        
        Downloader d = new Downloader(url, destinationFile, downloadPanel);
        new Thread(d).start();
        
        return d;
        
    }
    
    /**
     * Initialize UI
     *
     */
    private void createAndShowGUI() {
        
        downloaderPanel.setLayout(new GridLayout(0, 1, 5, 10));
        downloaderPanel.setSize(1000, 1000);
        scrollPane.getViewport().add(downloaderPanel);
        getContentPane().add(scrollPane);
        //jpanel: add(scrollPane);
        
        setTitle("1Ci [DownloadManager]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(515, 50 * 3));
        
        setVisible(true);
        
    }
    
    public static void main(String[] args) throws IOException {
                /*
                 * JFrame jFrame = new JFrame(); jFrame.setTitle("1Ci
                 * [DownloadManager]");
                 * jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 * jFrame.setSize(new Dimension(515, 50 * 3)); DownloadManager d = new
                 * DownloadManager(jFrame); jFrame.getContentPane().add(d);
                 *
                 * for (int i = 0; i < 4; i++) { try { Thread.sleep(5000); } catch
                 * (InterruptedException e) { // TODO Auto-generated catch block
                 * e.printStackTrace(); } d.startDownload(new URL(
                 * "http://www.branchenloesungen.de/pdf/saferpay_flyer.pdf"), new
                 * File("C:\\test" + i + ".pdf")); }
                 *
                 * jFrame.setVisible(true);
                 */
        DownloadManager d = new DownloadManager();
        for (int i = 0; i < 4; i++) {
            d.startDownload(new URL(
                    "http://www.branchenloesungen.de/pdf/saferpay_flyer.pdf"),
                    new File("C:\\test" + i + ".pdf"));
        }
        
    }
    
}