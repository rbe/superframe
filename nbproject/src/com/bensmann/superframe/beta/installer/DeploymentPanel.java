/*
 * Created on 11.07.2004
 *  
 */
package com.bensmann.superframe.beta.installer;

import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author rb
 * @version $Id: DeploymentPanel.java,v 1.1 2005/07/19 15:51:36 rb Exp $
 * 
 * Panel for deployment of applications
 *  
 */

public class DeploymentPanel extends JPanel {

	/**
	 * Combo box for type of software that should be installed
	 */
	protected JComboBox softwareTypeComboBox;

	/**
	 * Label for informations when installing an addon for office
	 */
	protected JLabel officeAddonInfoText;

	/**
	 * Combo box for StarOffice/OpenOffice addons
	 */
	protected JComboBox officeAddonComboBox;

	/**
	 * Path of StarOffice/OpenOffice installation where new addon should be
	 * deployed
	 */
	protected JButton officeAddonPathButton;

	protected JLabel officeAddonPathLabel;

	protected JTextField officeAddonTextField;

	/**
	 * Label for informations when installing software for dmerce
	 */
	protected JLabel dmerceSoftwareInfoText;

	/**
	 * Combo box for dmerce software components
	 */
	protected JComboBox dmerceSoftwareComboBox;

	/**
	 * Path of dmerce installation where new software should be deployed
	 */
	protected JButton dmerceSoftwarePathButton;

	protected JLabel dmerceSoftwarePathLabel;

	protected JTextField dmerceSoftwareTextField;

	protected JLabel instMethodLabel;

	protected ButtonGroup instMethodButtonGroup;

	protected JRadioButton instMethodFsRadioButton;

	protected JTextField instMethodFsTextArea;

	protected JButton instMethodFsPathButton;

	protected JRadioButton instMethodInternetRadioButton;

	protected JComboBox instMethodInternetServers;

	protected JButton installButton;

	// Maximum width for every component
	int minLeftBounds = 150;

	// Width for all components like labels
	int width = 350;

	// First component: Y position of informational text
	int infoTextYPos = 10;

	int infoTextHeight = 150;

	// Office addon installation
	int officeInstYPos = infoTextYPos + infoTextHeight + 5;

	int officeInstHeight = 75;

	// Installation method
	int instMethodYPos = officeInstYPos + officeInstHeight + 5;

	int instMethodHeight = 115;

	public DeploymentPanel() {
		createAndShowGUI();
	}

	protected void disableInstMethodFs() {
		instMethodFsPathButton.setEnabled(false);
		instMethodFsTextArea.setEnabled(false);
	}

	protected void enableInstMethodFs() {
		instMethodFsPathButton.setEnabled(true);
		instMethodFsTextArea.setEnabled(true);
	}

	/**
	 * Disable all components for choosing an installation method
	 *  
	 */
	protected void disableInstMethodElements() {
		instMethodInternetRadioButton.setEnabled(false);
		instMethodFsRadioButton.setEnabled(false);
		instMethodFsTextArea.setEnabled(false);
		instMethodFsPathButton.setEnabled(false);
		installButton.setEnabled(false);
	}

	/**
	 * Enable all components for choosing an installation method
	 *  
	 */
	protected void enableInstMethodElements() {

		instMethodInternetRadioButton.setEnabled(true);
		instMethodFsRadioButton.setEnabled(true);

		if (instMethodInternetRadioButton.isSelected()) {
			instMethodInternetServers.setEnabled(true);
			instMethodFsPathButton.setEnabled(false);
			instMethodFsTextArea.setEnabled(false);
		} else if (instMethodFsRadioButton.isSelected()) {
			instMethodInternetServers.setEnabled(false);
			instMethodFsPathButton.setEnabled(true);
			instMethodFsTextArea.setEnabled(true);
		}

		installButton.setEnabled(true);

	}

	/**
	 * Disable all elements for installing dmerce software
	 *  
	 */
	protected void disableDmerceSoftwareElements() {
		dmerceSoftwareInfoText.setVisible(false);
		dmerceSoftwarePathLabel.setVisible(false);
		dmerceSoftwarePathButton.setVisible(false);
		dmerceSoftwareTextField.setVisible(false);
		dmerceSoftwareComboBox.setVisible(false);
	}

	/**
	 * Enable all elements for installing dmerce software
	 *  
	 */
	protected void enableDmerceSoftwareElements() {
		dmerceSoftwareInfoText.setVisible(true);
		dmerceSoftwarePathLabel.setVisible(true);
		dmerceSoftwarePathButton.setVisible(true);
		dmerceSoftwareTextField.setVisible(true);
		dmerceSoftwareComboBox.setVisible(true);
	}

	/**
	 * Disable all elements for installing an office addon
	 *  
	 */
	protected void disableOfficeAddonElements() {
		officeAddonInfoText.setVisible(false);
		officeAddonPathLabel.setVisible(false);
		officeAddonPathButton.setVisible(false);
		officeAddonTextField.setVisible(false);
		officeAddonComboBox.setVisible(false);
	}

	/**
	 * Enable all elements for installing an office addon
	 *  
	 */
	protected void enableOfficeAddonElements() {
		officeAddonInfoText.setVisible(true);
		officeAddonPathLabel.setVisible(true);
		officeAddonPathButton.setVisible(true);
		officeAddonTextField.setVisible(true);
		officeAddonComboBox.setVisible(true);
	}

	/**
	 * Enable button for choosing instllation path of StarOffice/OpenOffice
	 *  
	 */
	protected void enableOfficeAddonPathButton() {
		officeAddonPathButton.setEnabled(false);
	}

	/**
	 * Office addon installation
	 */
	public void initOfficeAddon() {

		officeAddonInfoText = new JLabel("<html>" + "1. Bitte wählen Sie hier"
				+ " die StarOffice- oder OpenOffice-Installation aus,"
				+ " in der Sie Addons der 1[wan]Ci nutzen m&ouml;chten."
				+ " Der Installer versucht zun&auml;chst eine Installation"
				+ " automatisch zu finden." + "<br><br>" + "2. Produkt"
				+ "<br><br>" + "3. Resource" + "</html>");
		officeAddonInfoText.setVerticalAlignment(SwingConstants.TOP);
		officeAddonInfoText.setBounds(new Rectangle(minLeftBounds,
				infoTextYPos, width, infoTextHeight));
		officeAddonInfoText.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Information"));

		officeAddonPathLabel = new JLabel();
		officeAddonPathLabel.setBounds(new Rectangle(minLeftBounds,
				officeInstYPos, width, officeInstHeight));
		officeAddonPathLabel.setVerticalAlignment(SwingConstants.TOP);
		officeAddonPathLabel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Pfad der Star/OpenOffice-Installation und AddOn"));

		officeAddonPathButton = new JButton(com.bensmann.superframe.beta.ui.Configuration.getInstance().imageOpen);
		officeAddonPathButton.setBounds(new Rectangle(minLeftBounds + 7,
				officeInstYPos + 20, 20, 20));
		officeAddonPathButton.setBorderPainted(false);
		officeAddonPathButton.setFocusPainted(false);
		officeAddonPathButton
				.addActionListener(new OfficeAddonButtonActionListener(this));

		officeAddonTextField = new JTextField();
		officeAddonTextField.setBounds(new Rectangle(minLeftBounds + 29,
				officeInstYPos + 20, 316, 20));
		officeAddonTextField.setBorder(BorderFactory.createEtchedBorder());
		officeAddonTextField.getDocument().addDocumentListener(
				new InstMethodPathsDocumentListener(this));

		officeAddonComboBox = new JComboBox();
		officeAddonComboBox.addItem("1[IntelligentQuery]");
		officeAddonComboBox.setBounds(new Rectangle(minLeftBounds + 29,
				officeInstYPos + 45, 316, 20));

	}

	/**
	 * dmerce software installation
	 */
	public void initDmerceSoftware() {

		dmerceSoftwareInfoText = new JLabel();
		dmerceSoftwareInfoText = new JLabel("<html>" + "Bitte wählen Sie hier"
				+ " die dmerce-Installation und Komponente aus, die Sie"
				+ " nutzen m&ouml;chten."
				+ " Der Installer versucht zun&auml;chst eine Installation"
				+ " automatisch zu finden." + "<br><br>" + "2. Produkt"
				+ "<br><br>" + "3. Resource" + "</html>");
		dmerceSoftwareInfoText.setVerticalAlignment(SwingConstants.TOP);
		dmerceSoftwareInfoText.setBounds(new Rectangle(minLeftBounds,
				infoTextYPos, width, infoTextHeight));
		dmerceSoftwareInfoText.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Information"));

		dmerceSoftwarePathLabel = new JLabel();
		dmerceSoftwarePathLabel.setBounds(new Rectangle(minLeftBounds,
				officeInstYPos, width, officeInstHeight));
		dmerceSoftwarePathLabel.setVerticalAlignment(SwingConstants.TOP);
		dmerceSoftwarePathLabel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Pfad der dmerce-Installation"));

		dmerceSoftwarePathButton = new JButton(com.bensmann.superframe.beta.ui.Configuration.getInstance().imageOpen);
		dmerceSoftwarePathButton.setBounds(new Rectangle(minLeftBounds + 7,
				officeInstYPos + 20, 20, 20));
		dmerceSoftwarePathButton.setBorderPainted(false);
		dmerceSoftwarePathButton.setFocusPainted(false);
		dmerceSoftwarePathButton
				.addActionListener(new OfficeAddonButtonActionListener(this));

		dmerceSoftwareTextField = new JTextField();
		dmerceSoftwareTextField.setBounds(new Rectangle(minLeftBounds + 29,
				officeInstYPos + 20, 316, 20));
		dmerceSoftwareTextField.setBorder(BorderFactory.createEtchedBorder());
		dmerceSoftwareTextField.getDocument().addDocumentListener(
				new InstMethodPathsDocumentListener(this));

		dmerceSoftwareComboBox = new JComboBox();
		dmerceSoftwareComboBox.addItem("1[NCC]");
		dmerceSoftwareComboBox.addItem("1[Payment]");
		dmerceSoftwareComboBox.addItem("1[Protect]");
		dmerceSoftwareComboBox.setBounds(new Rectangle(minLeftBounds + 29,
				officeInstYPos + 45, 316, 20));
		dmerceSoftwareComboBox.setMaximumRowCount(3);

	}

	/**
	 * Initialize GUI components for installation method
	 *  
	 */
	public void initInstallationMethod() {

		instMethodLabel = new JLabel();
		instMethodLabel.setBounds(new Rectangle(minLeftBounds, instMethodYPos,
				width, instMethodHeight));
		instMethodLabel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Wählen Sie die Installationsquelle"));

		instMethodInternetRadioButton = new JRadioButton();
		instMethodInternetRadioButton.setText("Internet");
		instMethodInternetRadioButton.setBounds(new Rectangle(
				minLeftBounds + 7, instMethodYPos + 20, 100, 15));
		instMethodInternetRadioButton.setFocusPainted(false);
		instMethodInternetRadioButton.setSelected(true);
		instMethodInternetRadioButton
				.addChangeListener(new InstMethodInternetChangeListener(this));

		instMethodInternetServers = new JComboBox();
		instMethodInternetServers.addItem("http://downloads.1ci.com");
		instMethodInternetServers.setBounds(new Rectangle(minLeftBounds + 28,
				instMethodYPos + 40, 316, 20));

		instMethodFsRadioButton = new JRadioButton();
		instMethodFsRadioButton.setText("Lokal");
		instMethodFsRadioButton.setBounds(new Rectangle(minLeftBounds + 7,
				instMethodYPos + 65, 100, 15));
		instMethodFsRadioButton.setFocusPainted(false);
		instMethodFsRadioButton
				.addChangeListener(new InstMethodFsChangeListener(this));

		instMethodFsPathButton = new JButton(com.bensmann.superframe.beta.ui.Configuration.getInstance().imageOpen);
		instMethodFsPathButton.setBounds(new Rectangle(minLeftBounds + 7,
				instMethodYPos + 85, 20, 20));
		instMethodFsPathButton.setBorderPainted(false);
		instMethodFsPathButton.setFocusPainted(false);
		instMethodFsPathButton
				.addActionListener(new InstMethodFsActionListener(this));

		instMethodFsTextArea = new JTextField();
		instMethodFsTextArea.setBounds(new Rectangle(minLeftBounds + 29,
				instMethodYPos + 85, 316, 20));
		instMethodFsTextArea.setBorder(BorderFactory.createEtchedBorder());

		instMethodButtonGroup = new ButtonGroup();
		instMethodButtonGroup.add(instMethodInternetRadioButton);
		instMethodButtonGroup.add(instMethodFsRadioButton);

	}

	/**
	 * Type of software
	 */
	void initSoftwareTypes() {

		softwareTypeComboBox = new JComboBox();
		softwareTypeComboBox.addItem("dmerce");
		softwareTypeComboBox.addItem("Office Addon");
		softwareTypeComboBox.addItem("Branchenlösungen");
		softwareTypeComboBox.addItem("OpenSource");
		softwareTypeComboBox.setBounds(10, 15, 120, 20);
		softwareTypeComboBox.addActionListener(new SoftwareTypeActionListener(
				this));

	}

	/**
	 * Initialize GUI components of "Deployment" panel
	 *  
	 */
	public void createAndShowGUI() {

		initSoftwareTypes();
		initDmerceSoftware();
		initOfficeAddon();
		initInstallationMethod();

		// Button for starting installation
		installButton = new JButton("Installieren");
		installButton.setBounds(new Rectangle(minLeftBounds, 400, 90, 25));
		installButton.setFocusPainted(false);
		installButton.setBorderPainted(true);
		installButton.addActionListener(new InstallAction(this));

		// We prefer dmerce :-)
		disableOfficeAddonElements();
		enableDmerceSoftwareElements();

		// Add components to panel
		setLayout(null);
		add(softwareTypeComboBox, null);
		add(dmerceSoftwareInfoText, null);
		add(dmerceSoftwareComboBox, null);
		add(dmerceSoftwarePathButton, null);
		add(dmerceSoftwarePathLabel, null);
		add(dmerceSoftwareTextField, null);
		add(officeAddonInfoText, null);
		add(officeAddonComboBox, null);
		add(officeAddonPathButton, null);
		add(officeAddonPathLabel, null);
		add(officeAddonTextField, null);
		add(instMethodLabel, null);
		add(instMethodInternetRadioButton, null);
		add(instMethodInternetServers, null);
		add(instMethodFsPathButton, null);
		add(instMethodFsRadioButton, null);
		add(instMethodFsTextArea, null);
		add(installButton, null);

		// We prefer installing via HTTP not filesystem
		disableInstMethodFs();

	}
}