package com.googlecode.linkedlisp;

public class Symbol {
    private String name;

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
