package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Quote extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        Expression first = params.getFirst();
        return first.getValue();
    }
    
    @Override
    public Object getValue() {
        return "QUOTE";
    }

}
