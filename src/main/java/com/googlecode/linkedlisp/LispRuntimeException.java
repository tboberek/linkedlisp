/**
 * 
 */
package com.googlecode.linkedlisp;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Pawel
 *
 */
public class LispRuntimeException extends Exception {
	
	private final List<String> stacktrace = new LinkedList<String>();
	
	public LispRuntimeException(Exception cause) {
		super(cause);
	}
	
	public LispRuntimeException(Exception cause, String lispFunction) {
		super(cause);
		addToStacktrace(lispFunction);
	}
	
	public void addToStacktrace(String a) {
		stacktrace.add(a);
	}
	
	@Override
	public String toString() {
		String ret = "lisp stacktrace:\n";
		for(String s : stacktrace)
			ret = ret + s + "\n";
		
		return  ret;
	}

}
