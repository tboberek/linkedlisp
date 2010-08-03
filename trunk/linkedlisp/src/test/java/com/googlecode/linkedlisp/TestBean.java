/**
 * 
 */
package com.googlecode.linkedlisp;

/**
 * @author Pawel
 *
 */
public class TestBean {
	
	private String firstName;
	private String lastName;
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getName() {
		return lastName + ", " + firstName;
	}

	@org.junit.Test
	public void testBean() {
	    // Empty test method, because everything seems to be a test here.
	}
	
}
