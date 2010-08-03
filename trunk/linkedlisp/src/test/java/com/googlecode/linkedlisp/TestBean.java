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

}
