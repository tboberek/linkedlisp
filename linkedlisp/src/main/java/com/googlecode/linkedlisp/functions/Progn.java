package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Progn extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        Object result = null;
        params.iterator();
        for (Expression exp : params)  {
            Object o = exp.evaluate(s);
            result = o;
        }
        return result;
    }
    
    @Override
    public Object getValue() {
        return "progn";
    }

}
