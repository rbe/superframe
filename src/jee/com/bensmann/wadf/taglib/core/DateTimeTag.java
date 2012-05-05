/*
 * Created on 14.03.2004
 *  
 */
package com.bensmann.wadf.taglib.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author rb
 * @version $Id: DateTimeTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 * 
 * Output formatted date
 *  
 */
public class DateTimeTag extends BnmTagSupport {

	private String format = "dd.MM.yyyy HH:MM:s";

	public int doStartTag() {
		return SKIP_BODY;
	}

	public int doEndTag() {

		try {
			pageContext.getOut().print(
					new SimpleDateFormat(format).format(new Date()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}