/*
 * DesktopEventAlerter.java
 *
 * Created on 27.09.2007, 11:13:22
 *
 */

package com.bensmann.superswing.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;

public class DesktopEventAlerter extends JWindow {

    private Dimension screenSize;

    private JLabel messageLabel;

    private static final int ERROR_MESSAGE = 0;

    private static final int INFORMATION_MESSAGE = 1;

    private static final int WARNING_MESSAGE = 2;

    private static final int QUESTION_MESSAGE = 3;

    public DesktopEventAlerter() {
        //super("Alert");
        screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // setResizable(false);
        messageLabel = new JLabel("", JLabel.CENTER);
        setLayout(new BorderLayout());
        setAlwaysOnTop(true);
        getContentPane().add(messageLabel, BorderLayout.CENTER);
        JButton button =
                new JButton("<HTML><FONT FACE = Tahoma>DISMISS</FONT></HTML>");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(button);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for (int i = 100; i > 0;
                     i--) {
                    setBounds(screenSize.width - 400,
                            (screenSize.height - i) - 30, 400, 100);
                    try {
                        Thread.sleep(1 / 1000);
                    } catch (Exception er) {
                    }

                    setVisible(true);
                }
                setVisible(false);
                dispose();
                System.exit(0);
            }
        });
    }

    public void alert(String message, int messageType) {
        switch (messageType) {
            case 0:
                // ERROR_MESSAGE
                {
                    showError(message);
                }
                break;
            case 1:
                // INFORMATION_MESSAGE
                {
                    showInfo(message);
                }
                break;
            case 2:
                //WARNING_MESSAGE
                {
                    showWarning(message);
                }
                break;
            case 3:
                //QUESTION_MESSAGE
                {
                    askQuestion(message);
                }
                break;
            default:
                {
                }
                break;
        }
    }

    private void showWarning(String message) {
        messageLabel.setText("<HTML><FONT COLOR = YELLOW FACE = Tahoma>" +
                message + "</FONT></HTML>");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (int i = 0; i < 100;
                 i++) {
                setBounds(screenSize.width - 250, (screenSize.height - i) - 30,
                        250, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
            Thread.sleep(10000);
            for (int i = 100; i > 0;
                 i--) {
                setBounds(screenSize.width - 250, (screenSize.height - i) - 30,
                        250, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
        } catch (Exception e) {
        } finally {
            setVisible(false);
            dispose();
        }
    }

    private void showInfo(String message) {
        messageLabel.setText("<HTML><FONT COLOR = BLUE FACE = Tahoma>" + message +
                "</FONT></HTML>");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (int i = 0; i < 100;
                 i++) {
                setBounds(screenSize.width - 250, (screenSize.height - i) - 30,
                        250, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
            Thread.sleep(10000);
            for (int i = 100; i > 0;
                 i--) {
                setBounds(screenSize.width - 250, (screenSize.height - i) - 30,
                        250, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
        } catch (Exception e) {
        } finally {
            setVisible(false);
            dispose();
        }
    }

    private void showError(String message) {
        messageLabel.setText("<HTML><FONT COLOR = RED FACE = Tahoma>" + message +
                "</FONT></HTML>");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (int i = 0; i < 100;
                 i++) {
                setBounds(screenSize.width - 400, (screenSize.height - i) - 30,
                        400, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
            Thread.sleep(10000);
            for (int i = 100; i > 0;
                 i--) {
                setBounds(screenSize.width - 400, (screenSize.height - i) - 30,
                        400, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
        } catch (Exception e) {
        } finally {
            setVisible(false);
            dispose();
        }
    }

    private void askQuestion(String message) {
        messageLabel.setText("<HTML><FONT COLOR = GREEN FACE = Tahoma>" + message +
                "</FONT></HTML>");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (int i = 0; i < 100;
                 i++) {
                setBounds(screenSize.width - 250, (screenSize.height - i) - 30,
                        250, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
            Thread.sleep(10000);
            for (int i = 100; i > 0;
                 i--) {
                setBounds(screenSize.width - 250, (screenSize.height - i) - 30,
                        250, 100);
                Thread.sleep(1 / 1000);
                setVisible(true);
            }
        } catch (Exception e) {
        } finally {
            setVisible(false);
            dispose();
        }
    }

    public static void main(String[] args) {
        DesktopEventAlerter alerter = new DesktopEventAlerter();
        alerter.alert("Hello World", DesktopEventAlerter.ERROR_MESSAGE);
    }
}