package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Cons implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws Exception {
        return new ListExpression((Expression)params.get(0).evaluate(s),
                                  (ListExpression)params.get(1).evaluate(s));
    }

    @Override
    public Object getValue(State s) {
        return "cons";
    }

}
