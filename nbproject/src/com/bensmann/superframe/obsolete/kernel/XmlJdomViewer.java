/*
 * Created on Jun 10, 2003
 *
 */
package com.bensmann.superframe.obsolete.kernel;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.bensmann.superframe.java.LangUtil;

/**
 * @author rb
 * @version $Id: XmlJdomViewer.java,v 1.1 2005/07/19 15:51:39 rb Exp $
 *
 */
public class XmlJdomViewer {

	/**
	 * Konstruktor
	 */
	public XmlJdomViewer() {
	}

	/**
	 * Zeigt das angegebene XML-Dokument mit den Unterelementen in 
	 * einer eingerückten Struktur an.
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static void viewer(String fileName)
		throws IOException, JDOMException {

		// ---- Read XML file ----
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File(fileName));
		
		// ---- Modify XML data ----
		//  ... do anything with XML data
		// ---- Write XML file ----
		XMLOutputter fmt = new XMLOutputter();
		
		// only for nicer formatting
		//fmt.setIndent("  "); 
		//fmt.setNewlines(true);
		fmt.output(doc, System.out);
	}

	/**
	 * Zeigt das angegebene Element mit den Unterelementen in einer
	 * eingerückten Struktur an.
	 * 
	 * @param el
	 */
	public static void elViewer(Element el) {
		
		XMLOutputter fmt = new XMLOutputter();

		// only for nicer formatting
		//fmt.setIndent("  ");
		//fmt.setNewlines(true);
		
		try {
			fmt.output(el, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Wandelt das angegebene Dokument in einen String um.
	 * 
	 * @param doc
	 * @return
	 */
	public String docToString(Document doc) {
		
		String doc2String = "";
		XMLOutputter xml = new XMLOutputter();
		
		try {
			doc2String = xml.outputString(doc);
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		return doc2String;
	}
	
	/**
	 * Wandelt das angegebene Element und Unterelemente in einen
	 * String um.
	 * 
	 * @param el
	 * @return
	 */
	public String elToString(Element el) {
		
		String el2String = "";
		XMLOutputter fmt = new XMLOutputter();
		
		//fmt.setIndent("  "); // only for nicer formatting
		//fmt.setNewlines(true); // only for nicer formatting
		try {
			el2String = fmt.outputString(el);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return el2String;
	}

	/**
	 * Eine Hilfe zur Benutzung des Klasse, falls man falsche Parameter
	 * angegeben hat.
	 */
	public static void usage() {
		System.err.println("Usage: java XmlJDOMParser MyXmlFile.xml");
		System.exit(2);
	}

	public static void main(String[] args) {

		Boot.printCopyright("XML JDOM PARSER");

		if (args.length != 1)
			usage();

		for (int i = 0; i < args.length; i++) {

			try {
				XmlJdomViewer.viewer(args[i]);
			} catch (IOException e) {
				LangUtil.consoleDebug(
					true,
					"Document cannot be accessed: " + e.getMessage());
			} catch (JDOMException e) {
				LangUtil.consoleDebug(
					true,
					"Document is not an valid XML document: " + e.getMessage());
			}

		}

	}

}