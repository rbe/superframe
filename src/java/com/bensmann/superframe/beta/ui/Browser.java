/*
 * Created on 18.07.2004
 *
 */
package com.bensmann.superframe.beta.ui;

/**
 *
 * @author rb
 * @version $Id: Browser.java,v 1.1 2005/07/19 15:51:37 rb Exp $
 */
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Browser extends JFrame {
    
    protected JEditorPane m_browser;
    
    protected MemComboBox m_locator;
    
    protected AnimatedLabel m_runner;
    
    public Browser() {
        super("HTML Browser [ComboBox with Memory]");
        setSize(500, 300);
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(new JLabel("Address"));
        p.add(Box.createRigidArea(new Dimension(10, 1)));
        
        m_locator = new MemComboBox();
        m_locator.load("addresses.dat");
        BrowserListener lst = new BrowserListener();
        m_locator.addActionListener(lst);
        
        p.add(m_locator);
        p.add(Box.createRigidArea(new Dimension(10, 1)));
        
        m_runner = new AnimatedLabel("test", 3);
        p.add(m_runner);
        getContentPane().add(p, BorderLayout.NORTH);
        
        m_browser = new JEditorPane();
        m_browser.setEditable(false);
        m_browser.addHyperlinkListener(lst);
        
        JScrollPane sp = new JScrollPane();
        sp.getViewport().add(m_browser);
        getContentPane().add(sp, BorderLayout.CENTER);
        
        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                m_locator.save("addresses.dat");
                System.exit(0);
            }
        };
        addWindowListener(wndCloser);
        
        setVisible(true);
        m_locator.grabFocus();
    }
    
    class BrowserListener implements ActionListener, HyperlinkListener {
        public void actionPerformed(ActionEvent evt) {
            String sUrl = (String) m_locator.getSelectedItem();
            if (sUrl == null || sUrl.length() == 0 || m_runner.getRunning())
                return;
            BrowserLoader loader = new BrowserLoader(sUrl);
            loader.start();
        }
        
        public void hyperlinkUpdate(HyperlinkEvent e) {
            URL url = e.getURL();
            if (url == null || m_runner.getRunning())
                return;
            BrowserLoader loader = new BrowserLoader(url.toString());
            loader.start();
        }
    }
    
    class BrowserLoader extends Thread {
        protected String m_sUrl;
        
        public BrowserLoader(String sUrl) {
            m_sUrl = sUrl;
        }
        
        public void run() {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            m_runner.setRunning(true);
            
            try {
                URL source = new URL(m_sUrl);
                m_browser.setPage(source);
                m_locator.add(m_sUrl);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(Browser.this, "Error: "
                        + e.toString(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
            m_runner.setRunning(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    public static void main(String argv[]) {
        new Browser();
    }
}

class MemComboBox extends JComboBox {
    public static final int MAX_MEM_LEN = 30;
    
    public MemComboBox() {
        super();
        setEditable(true);
    }
    
    public void add(String item) {
        removeItem(item);
        insertItemAt(item, 0);
        setSelectedItem(item);
        if (getItemCount() > MAX_MEM_LEN)
            removeItemAt(getItemCount() - 1);
    }
    
    public void load(String fName) {
        try {
            if (getItemCount() > 0)
                removeAllItems();
            File f = new File(fName);
            if (!f.exists())
                return;
            FileInputStream fStream = new FileInputStream(f);
            ObjectInput stream = new ObjectInputStream(fStream);
            Object obj = stream.readObject();
            if (obj instanceof ComboBoxModel)
                setModel((ComboBoxModel) obj);
            stream.close();
            fStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Serialization error: " + e.toString());
        }
    }
    
    public void save(String fName) {
        try {
            FileOutputStream fStream = new FileOutputStream(fName);
            ObjectOutput stream = new ObjectOutputStream(fStream);
            stream.writeObject(getModel());
            stream.flush();
            stream.close();
            fStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Serialization error: " + e.toString());
        }
    }
}

class AnimatedLabel extends JLabel implements Runnable {
    protected Icon[] m_icons;
    
    protected int m_index = 0;
    
    protected boolean m_isRunning;
    
    public AnimatedLabel(String gifName, int numGifs) {
        m_icons = new Icon[numGifs];
        for (int k = 0; k < numGifs; k++) {
            System.out.println("Loading image: " + gifName + k + ".gif");
//            m_icons[k] = new ImageIcon(gifName + k + ".gif");
            m_icons[k] = new ImageIcon(Browser.class.getResource(
                    gifName + k + ".gif"));
        }
        setIcon(m_icons[0]);
        
        Thread tr = new Thread(this);
        tr.setPriority(Thread.MAX_PRIORITY);
        tr.start();
    }
    
    public void setRunning(boolean isRunning) {
        m_isRunning = isRunning;
    }
    
    public boolean getRunning() {
        return m_isRunning;
    }
    
    public void run() {
        while (true) {
            if (m_isRunning) {
                m_index++;
                if (m_index >= m_icons.length)
                    m_index = 0;
                setIcon(m_icons[m_index]);
                Graphics g = getGraphics();
                m_icons[m_index].paintIcon(this, g, 0, 0);
            } else {
                if (m_index > 0) {
                    m_index = 0;
                    setIcon(m_icons[0]);
                }
            }
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
        }
    }
}
