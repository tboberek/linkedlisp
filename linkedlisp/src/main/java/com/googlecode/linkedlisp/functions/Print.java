package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Print extends Function {
    public Print() {
        setParameterNames(Arrays.asList(new String[]{"x"}));
    }

    public Object evaluate(State s) throws Exception {
        Object value = getVariable(s,0);
        System.out.print(value);
        return value;
    }

    @Override
    public Object getValue() {
        return "print";
    }

}
