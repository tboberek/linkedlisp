package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class ListFunction implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws Exception {
        return params;
    }

    @Override
    public Object getValue(State s) {
        return "list";
    }

}
