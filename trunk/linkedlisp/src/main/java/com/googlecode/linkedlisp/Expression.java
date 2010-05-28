package com.googlecode.linkedlisp;

public interface Expression {

    public Object evaluate(State s) throws Exception;
    
    public Object getValue(State s);
    
}
