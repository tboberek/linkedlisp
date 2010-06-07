package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class LispFunction extends Function {

    private Expression body;

    @Override
    public Object execute(State s, ListExpression params) throws Exception {
        State newState = s.copyForCall(params, getParameterNames());
        return body.evaluate(newState);
    }

    @Override
    public Object getValue() {
        return body;
    }

    public void setBody(Expression expression) {
        body = expression;
    }

}
