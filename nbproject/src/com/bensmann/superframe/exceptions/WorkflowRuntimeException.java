/*
 * Datei angelegt am 10.10.2003
 */
package com.bensmann.superframe.exceptions;

/**
 * Diese Exception wird vom Workflow-Engine verwendet, um unerwartete
 * Fehler bei der Verarbeitung von Workflows zur Laufzeit zu melden.
 * @author Masanori Fujita
 */
public class WorkflowRuntimeException extends Exception {
	
	public WorkflowRuntimeException(String message) {
		super(message);
	}

}
