package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Concatenate extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        StringBuilder result = new StringBuilder();
        for (Expression exp : params)  {
            Object o = exp.evaluate(s);
            if (o != null) result.append(o.toString());
        }
        return result;
    }
    
    @Override
    public Object getValue() {
        return "concatenate";
    }

}
