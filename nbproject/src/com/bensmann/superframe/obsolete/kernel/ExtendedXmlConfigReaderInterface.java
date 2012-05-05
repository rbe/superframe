/*
 * Created on Jun 18, 2003
 */
package com.bensmann.superframe.obsolete.kernel;

import java.io.InputStream;

/**
 * general config reader for an xml resource string
 * 
 * @author rb
 * @version $Id: ExtendedXmlConfigReaderInterface.java,v 1.1 2005/07/19 15:51:39 rb Exp $
 */
public interface ExtendedXmlConfigReaderInterface extends
		XmlConfigReaderInterface {

	/**
	 * resource string for xml string
	 */
	public void setXmlResource(InputStream stream);

	/**
	 * Ermittelt, ob die XML-Datei per Stream oder per String festgelegt worden
	 * ist.
	 * 
	 * @return
	 */
	public boolean isSetAsStream();

}