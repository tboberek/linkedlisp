package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class LispFunction extends Function {

    private Expression body;

    @Override
    public Object evaluate(State s) throws Exception {
        return body.evaluate(s);
    }

    @Override
    public Object getValue() {
        return body;
    }

    public void setBody(Expression expression) {
        body = expression;
    }

}
