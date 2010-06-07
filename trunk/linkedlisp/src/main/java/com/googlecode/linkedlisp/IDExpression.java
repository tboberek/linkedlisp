package com.googlecode.linkedlisp;

public class IDExpression implements Expression {
    private String value;

    public IDExpression(String value) {
        this.value = value;
    }

    public Object evaluate(State s) throws Exception {
        return s.getVariable(value);
    }
    
    public Object getValue() {
        return value;
    }
    
    @Override
	public String toString() {
        return getValue().toString();
    }
    
}
