package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Print implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws Exception {
        for (int i=0; i < params.size(); ++i) {
            System.out.print(params.get(i).evaluate(s));
        }
        throw new NoReturnException();
    }

    @Override
    public Object getValue(State s) {
        return "print";
    }

}
