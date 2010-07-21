package com.googlecode.linkedlisp.functions.io;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class WriteLine extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        System.out.println(value);
        return null;
    }

    @Override
    public Object getValue() {
        return "write-line";
    }

}
