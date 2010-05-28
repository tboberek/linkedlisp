package com.googlecode.linkedlisp;


public class FloatLiteral extends Literal {

    private double value;

    public FloatLiteral(double value) {
        super(Double.toString(value));
        this.value = value;
    }

    public Object evaluate(State s) {
        return value;
    }
    
    public Object getValue(State s) {
        return value;
    }

}
