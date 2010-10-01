package com.googlecode.linkedlisp;

import java.io.Serializable;

public class Symbol implements Serializable {
	private static final long serialVersionUID = -5050278876640519892L;
	private final String name;

    public Symbol(String value) {
        this.name = value;
    }

    public Object evaluate(Environment s) {
        return s.getVariable(name);
    }
    
    public Object getValue() {
        return name;
    }
    
    @Override
	public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }
    
}
