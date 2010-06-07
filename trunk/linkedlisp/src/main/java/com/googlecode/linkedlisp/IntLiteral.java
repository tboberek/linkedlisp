package com.googlecode.linkedlisp;


public class IntLiteral extends Literal {

    private long value;

    public IntLiteral(long value) {
        super(Long.toString(value));
        this.value = value;
    }

    @Override
	public Object evaluate(State s) {
        return value;
    }
    
    public Object getValue(State s) {
        return value;
    }

}
