package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Quote extends Function {

    public Object evaluate(State s) throws Exception {
        Expression first = s.getParameterList().getFirst();
        return first.getValue();
    }
    
    @Override
    public Object getValue() {
        return "QUOTE";
    }

}
