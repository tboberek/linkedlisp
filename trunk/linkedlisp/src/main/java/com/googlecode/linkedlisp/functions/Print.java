package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Print extends Function {
    public Print() {
        setParameterNames(Arrays.asList(new String[]{"x"}));
    }

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = getVariable(s,0);
        System.out.print(value);
        return value;
    }

    @Override
    public Object getValue() {
        return "print";
    }

}
