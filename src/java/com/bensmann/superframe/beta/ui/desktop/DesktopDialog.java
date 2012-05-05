/*
 * Created on 28.03.2005
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author rb
 * @version $Id: DesktopDialog.java,v 1.1 2005/07/19 15:51:38 rb Exp $
 * 
 * Standarized dialog for any type of application.
 * 
 * Features: Keeps your dialog organized
 * <p>- Provides a nice title with background in an own panel
 * <p>- An own panel for text/images/information
 * <p>- Separate panel for buttons
 *  
 */
public class DesktopDialog extends JDialog {

	private JPanel jContentPane = null;

	private JFrame parentFrame = null;

	private JPanel titlePanel = null;

	private JPanel imagePanel = null;

	private JPanel mainPanel = null;

	private JPanel buttonPanel = null;

	private JButton jButton = null;

	private JLabel titleLabel = null;

	private JLabel spacer = new JLabel();

	private ImageIcon infoImage = new ImageIcon(this.getClass().getResource(
			"info.gif")); //  @jve:decl-index=0:visual-constraint="57,105"

	/**
	 * (Calculated) height of this dialog
	 */
	private int dialogHeight;

	/**
	 * (Calculated) width of this dialog
	 */
	private int dialogWidth;

	private String windowTitle = "Unknown Dialog";

	private String dialogTitle = "An even longer title for an unknown dialog";

	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public DesktopDialog() {
		super();
		initialize();
	}

	public DesktopDialog(JFrame parentFrame) {
		super(parentFrame);
		initialize();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		
		if (jContentPane == null) {
			
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTitlePanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getImagePanel(), java.awt.BorderLayout.WEST);
			jContentPane.add(getMainPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getButtonPanel(), java.awt.BorderLayout.CENTER);
			
		}
		
		return jContentPane;
		
	}

	/**
	 * This method initializes titlePanel that show the title of this dialog
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTitlePanel() {

		if (titlePanel == null) {

			titlePanel = new JPanel();
			titlePanel.setLayout(new BorderLayout());
			titlePanel.setBackground(Color.WHITE);
			titlePanel.setPreferredSize(new java.awt.Dimension(100, 100));

			titlePanel.add(spacer, BorderLayout.WEST);

			titleLabel = new JLabel("A dialog with unknown title");
			titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
			titlePanel.add(titleLabel, BorderLayout.CENTER);

		}

		return titlePanel;

	}

	/**
	 * This method initializes titlePanel that show the title of this dialog
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getImagePanel() {

		if (imagePanel == null) {

			imagePanel = new JPanel();
			imagePanel.setLayout(new BorderLayout());
			imagePanel.setBackground(Color.WHITE);
			imagePanel.setPreferredSize(new java.awt.Dimension(100, 100));

			imagePanel.add(spacer, BorderLayout.WEST);

			jLabel = new JLabel();
			jLabel.setIcon(infoImage);
			imagePanel.add(jLabel, BorderLayout.CENTER);

		}

		return imagePanel;

	}

	/**
	 * This method initializes contentPanel that holds the
	 * (textual/informaional) content of this dialog
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainPanel() {
		
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.add(getJButton(), null);
		}
		
		return mainPanel;
		
	}

	/**
	 * This method initializes buttonPanel that holds all buttons of this dialog
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
		}
		return buttonPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {

		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Test Button");
		}

		return jButton;

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		spacer.setPreferredSize(new Dimension(15, 10));
		spacer.setBorder(BorderFactory.createLineBorder(Color.RED));

		setContentPane(getJContentPane());

		// Set dialog height and width
		if (parentFrame != null) {
			dialogHeight = (int) (parentFrame.getHeight() / 2);
			dialogWidth = (int) (parentFrame.getWidth() / 2);
			setSize(dialogWidth, dialogHeight);
		} else {
			setSize(640, 480);
		}

		setTitles(null, null);
		setVisible(true);

	}

	/**
	 * Set titles: window title and title/description in dialog (text in dialog
	 * title panel)
	 * 
	 * @param windowTitle
	 * @param dialogTitle
	 */
	public void setTitles(String windowTitle, String dialogTitle) {

		if (windowTitle != null)
			this.windowTitle = windowTitle;
		if (dialogTitle != null)
			this.dialogTitle = dialogTitle;

		setTitle(this.windowTitle);
		titleLabel.setText(this.dialogTitle);

	}

	/**
	 * Adds a button to the button panel
	 * 
	 * @param button
	 */
	public void addButton(JButton button) {
		button.setFocusPainted(false);
		buttonPanel.add(button);
	}
	
	public static void main(String[] args) {
		new DesktopDialog();//.setTitles("Abc", "xyZ");
	}

} //  @jve:decl-index=0:visual-constraint="201,32"
