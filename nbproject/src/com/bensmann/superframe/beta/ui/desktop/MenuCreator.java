/*
 * Created on Dec 21, 2004
 *
 */
package com.bensmann.superframe.beta.ui.desktop;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * Singelton Pattern used here
 * 
 * Create a menu bar (JMenuBar) and menus (JMenu) from settings stored in
 * property files or using the Java Preferences API
 * 
 * Menus: A string containing all names of any menus separated by a comma. Each
 * name of a menu must occur in names of menu items
 * 
 * MenuItems: for each menu item we need some informations: name of the item,
 * the mnemonic key, keyboard accelerator and the associated listener that must
 * be attachted to the menu item
 *  
 * @author rb
 * @version $Id$
 */
public class MenuCreator {

	/**
	 * Singleton pattern
	 */
	private static MenuCreator singleton = null;

	/**
	 * Application helper
	 */
	private ApplHelper applHelper = ApplHelper.getInstance();

	/**
	 * A resource bundle; remember to have the properties file in the
         * classpath
	 */
	private ResourceBundle menuBundle;

	/**
	 * Filename for menu resource bundle
	 */
	private String menuBundleFilename;

	/**
	 * Preferences
	 */
	private Preferences prefs;

	/**
	 * All generated menus
	 */
	private Vector jMenus = new Vector();

	/**
	 * Ident for list of menus
	 */
	private String menusIdent = "bnmUserMenus";

	/**
	 * Private constructor; singleton
	 * 
	 * @param menuBundleFilename
	 */
	private MenuCreator(String menuBundleFilename) {
		this.menuBundleFilename = menuBundleFilename;
		prefs = Preferences.userNodeForPackage(this.getClass());
	}

	/**
	 * Return instance of MenuCreator; singleton pattern
	 * 
	 * @param menuBundleFilename
	 * @return
	 */
	public static MenuCreator getInstance(String menuBundleFilename) {

		if (singleton == null) {
			singleton = new MenuCreator(menuBundleFilename);
		}

		return singleton;

	}

	/**
	 * Return instance of MenuCreator; singleton pattern
	 * 
	 * @param menuBundleFilename
	 * @return
	 */
	public static MenuCreator getInstance() {

		if (singleton == null) {
			singleton = new MenuCreator(null);
		}

		return singleton;

	}

	/**
	 * Return a JMenu object created from the arguments (name, mnemonic,
	 * accelerator)
	 * 
	 * @param name
	 * @return
	 */
	private JMenu createMenu(String name) {

		JMenu menu = new JMenu(name);

		return menu;

	}

	/**
	 * Create a JMenuItem from the arguments (name, mnenonic, accelerator)
         * and return the object
	 * 
	 * @param name
	 * @param mnemonic
	 * @param accelerator
	 * @return
	 */
	private JMenuItem createMenuItem(String name, char mnemonic,
			String accelerator) {

		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setMnemonic(mnemonic);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator));

		return menuItem;

	}

	/**
	 * Create menubar from property file and return it
	 * 
	 * @param key
	 *            java.lang.String Schlüsselwort für den Eintrag der MenuBar
	 * @return javax.swing.JMenuBar aus dem Propertyfile erzeugte Menüleiste
	 */
	public JMenuBar getMenuBar(String key) {

		JMenuBar m = new JMenuBar();

		// Rekursive Aufschlüsselung der Menü-Inhalte
		menuBundle = ResourceBundle.getBundle("menu");
		buildMenu(m, menuBundle.getString(key));

		return m;
	}

	/**
	 * Erzeuge Menü (rekursiv).
	 * 
	 * @param comp
	 *            javax.swing.JComponent die Parent-Component des Menüs
	 * @param key
	 *            java.lang.String Key des/der Menüs
	 * @param bundle
	 *            java.util.ResourceBundle Abbildung mit Menüeinträgen
	 */
	private void buildMenu(JComponent comp, String key) {

		//
		// ggfs. Loop über Untermenüs
		//
		StringTokenizer st = new StringTokenizer(key);

		while (st.hasMoreTokens()) {

			// Eintrag aus ResourceBundle holen
			// wenn es ein Menü ist dann hat es weitere unterpunkte...
			String token = "" + st.nextToken();
			System.out.println("1:" + token);

			try {

				// Get entry for subitems; if this is not found
				// (MissingResourceException) then this entry is a menuitem
				String items = menuBundle.getString(token + ".items");
				String text = menuBundle.getString(token + ".text");
				char mn = menuBundle.getString(token + ".mnemonic").charAt(0);
				System.out.println("2:" + items + "/" + text + "/" + mn);

				JMenu menu = new JMenu(text);
				menu.setMnemonic(mn);
				comp.add(menu);

				System.out.println("buildMenu");
				buildMenu(menu, items);

			} catch (MissingResourceException mre) {

				// Untereinträge wurde nicht gefunden... also ist dieser Eintrag
				// ein MenuItem
				System.out.println("buildMenuItem");
				buildMenuItem(comp, token);

			}

		}

	}

	/**
	 * Erzeuge Menüeintrag.
	 * <p>
	 * 
	 * @param comp
	 *            javax.swing.JComponent die Parent-Component des Eintrages
	 * @param key
	 *            java.lang.String Key des Eintrages
	 * @param bundle
	 *            java.util.ResourceBundle Abbildung mit Menüeinträgen
	 */
	private void buildMenuItem(JComponent comp, String key) {

		if (key.equals("-")) {

			// Sonderfall, nur Trennstrich einfügen...
			comp.add(new JSeparator());

		} else {

			// Could item be added to menu? Is set to true if text and action
			// was found
			boolean addItem = false;
			JMenuItem item = new JMenuItem();

			// Text setzen
			try {
				String text = menuBundle.getString(key + ".text");
				item.setText(text);
				addItem = true;
			} catch (MissingResourceException e) {
			}

			// ggfs Mnemonic setzen
			try {
				char mn = menuBundle.getString(key + ".mnemonic").charAt(0);
				item.setMnemonic(mn);
			} catch (MissingResourceException e) {
			}

			// ggfs Accelerator setzen
			try {
				String val = menuBundle.getString(key + ".accelerator");
				item.setAccelerator(KeyStroke.getKeyStroke(val));
			} catch (MissingResourceException e) {
			}

			// ggfs Action setzen
			try {

				String val = menuBundle.getString(key + ".action");
				AbstractBaseAction action = (AbstractBaseAction) applHelper
						.loadActionClass(val);

				if (action != null) {
					action.setComponent(item);
					item.addActionListener(action);
				} else {
					addItem = false;
				}

			} catch (MissingResourceException e) {
				addItem = false;
			}

			if (addItem)
				comp.add(item);

		}

	}

	/**
	 * Read preferences and build a menu bar
	 * 
	 * @return
	 */
	public JMenuBar createMenuBarFromPrefs() {

		String menus = (String) prefs.get(menusIdent, null);
		return null;

	}

	public void buildMenu(JMenuBar menuBar) {
	}

}