package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Progn implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws Exception {
        Object result = null;
        for (int i=0; i< params.size(); ++i) try {
            Object o = params.get(i).evaluate(s);
            if (i == params.size()-1)
                result = o;
        } catch (NoReturnException e) {
            if (i == params.size()-1)
                throw e;
        }
        return result;
    }
    
    @Override
    public Object getValue(State s) {
        return "progn";
    }

}
