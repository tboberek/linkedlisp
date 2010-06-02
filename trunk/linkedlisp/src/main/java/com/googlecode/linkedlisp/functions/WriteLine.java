package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class WriteLine extends Function {

    public Object evaluate(State s) throws Exception {
        Object value = s.getParameterList().getFirst().evaluate(s);
        System.out.println(value);
        return null;
    }

    @Override
    public Object getValue() {
        return "write-line";
    }

}
