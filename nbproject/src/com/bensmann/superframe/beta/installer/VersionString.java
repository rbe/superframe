package com.bensmann.superframe.beta.installer;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Class that represents a version (string)
 * 
 * Implements .compareTo(VersionString) so that versions can be compared to each
 * other
 */
public class VersionString {

	/**
	 * Version string separated by dots (".")
	 */
	private String versionString = null;

	/**
	 * Vector with elements of version string
	 */
	private Vector elements = new Vector();

	/**
	 * How much elements does this version string has got?
	 */
	private int elementCount;

	public VersionString(String versionString) {
		this.versionString = versionString;
		parse();
	}

	/**
	 * Computes a value
	 * 
	 * We have to know how many elements a version string has got
	 * 
	 * Example for computation: 1.7.3 => 1 * 100 + 7 * 10 + 3 * 1 = 173
	 * 
	 * @return
	 */
	public int compute() {
		int addedElements = 0;
		int c = elementCount;

		for (int i = 0; i < elementCount; i++) {
			addedElements += ((Integer.valueOf((String) elements.get(i)))
					.intValue() * (int) Math.pow(10, c--));
		}

		return addedElements;
	}

	/**
	 * Return iterator over parsed version string elements
	 * 
	 * @return
	 */
	public Iterator getIterator() {
		return elements.iterator();
	}

	/**
	 * Parse version string into its elements
	 * 
	 * ToDo: get rid of characters (for e.g. "b" like "beta") because it will
	 * bother our computation
	 *  
	 */
	private void parse() {
		StringTokenizer st = new StringTokenizer(versionString, ".");

		while (st.hasMoreTokens()) {
			elements.add(st.nextToken());
			elementCount++;
		}
	}

	/**
	 * Use parsed elements to show version
	 */
	public void show() {
		Iterator i = getIterator();

		while (i.hasNext()) {
			String e = (String) i.next();
			System.out.print(e);

			if (i.hasNext()) {
				System.out.print(".");
			}
		}

		System.out.println();
	}

	/**
	 * Compare to version strings with each other
	 * 
	 * Example: v1 = VersionString("1.0"); v2 = VersionString("2.0");
	 * 
	 * v1.compareTo(v2) -> -1 v2.compareTo(v1) -> 1
	 * 
	 * A return value of 0 says that both versions are equal A return value of
	 * -2 indicates an error
	 * 
	 * @param versionString
	 * @return
	 */
	public int compareTo(VersionString versionString) {
		int v = -2;
		int v1 = compute();
		int v2 = versionString.compute();

		if (v1 == v2) {
			v = 0;
		} else if (v1 < v2) {
			v = -1;
		} else if (v1 > v2) {
			v = 1;
		}

		return v;
	}

	public static void main(String[] args) {
		VersionString v1 = new VersionString("1.7.0");
		VersionString v2 = new VersionString("2.0");
		System.out.println(v1.compute());
		System.out.println(v2.compute());
		System.out.println(v1.compareTo(v2));
	}
}