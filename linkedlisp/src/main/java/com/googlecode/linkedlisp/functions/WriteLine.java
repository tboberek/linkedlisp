package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class WriteLine extends Function {

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
