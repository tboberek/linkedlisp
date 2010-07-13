package com.googlecode.linkedlisp;

public class Literal implements Expression {
    private String value;

    public Literal(String value) {
        this.value = value;
    }

    public Object evaluate(State s) throws Exception {
        return value;
    }

    public Object getValue() {
        return value;
    }
    
    @Override
	public String toString() {
        return getValue().toString();
    }
    
}