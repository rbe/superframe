package com.bensmann.wadf.taglib.core;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * 
 * @author rb
 * @version $Id: MessageTag.java,v 1.1 2005/07/19 12:03:51 rb Exp $
 *  
 */
public class MessageTag extends BnmBodyTagSupport {

	private ArrayList a = null;

	private String output = "";

	/**
	 * Get all entries from session attribute qMessage and delete attribute from
	 * session
	 * 
	 * @return
	 */
	public int doStartTag() throws JspTagException {

		a = (ArrayList) pageContext.getSession().getAttribute("qMessage");
		pageContext.getSession().removeAttribute("qMessage");

		return EVAL_BODY_BUFFERED;

	}

	/**
	 * 
	 */
	public int doAfterBody() {
		return EVAL_PAGE;
	}

	/**
	 * 
	 */
	public int doEndTag() throws JspTagException {

		String header = "";
		String footer = "";
		String prefix = "";
		String postfix = "<br/>";

		//hole BodyContent aus dem Tag
		if (a != null) {

			BodyContent bc = getBodyContent();
			if (bc != null) {
				String body = getBodyContent().getString();
				//TODO: header usw. rausziehen
				System.out.println(body);
			}

			output = header;

			//gehe durch Liste von Nachrichten und stelle sie dar
			Iterator iterator = a.iterator();
			while (iterator.hasNext()) {
				output += prefix + (String) iterator.next() + postfix;
			}
			output += footer;
			output = "<p class=\"msg\">" + output + "</p>\n";

			try {
				pageContext.getOut().write(output);
			} catch (Exception e) {
				throw new JspTagException(e.toString());
			}

		}

		pageContext.getSession().removeAttribute("qMessage");

		return EVAL_PAGE;

	}

}