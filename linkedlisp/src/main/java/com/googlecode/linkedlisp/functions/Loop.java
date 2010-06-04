package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Loop extends Function {

    public Loop() {
        setParameterNames(Arrays.asList(new String[]{"name","x", "y"}));
    }
    
    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object execute(State s, ListExpression params) throws NoReturnException {
        // TODO implement it.
        return null;
    }

    @Override
    public Object getValue() {
        return "loop";
    }

}
