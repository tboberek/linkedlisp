package com.googlecode.linkedlisp;

public class IDExpression implements Expression {
    private String value;

    public IDExpression(String value) {
        this.value = value;
    }

    public Object evaluate(State s) {
        Object result = s.getVariables().get(value);
        if (result == null)
            result = s.getGlobalVariables().get(value);
        
        return result;
    }
    
    public Object getValue() {
        return value;
    }
    
    public String toString() {
        return getValue().toString();
    }
    
}
