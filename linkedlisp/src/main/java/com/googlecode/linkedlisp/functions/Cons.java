package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Cons extends Function {
    public Object evaluate(State s) throws Exception {
        ListExpression params = s.getParameterList();
        List<Object> result = new ArrayList<Object>();
        result.add(params.get(0).evaluate(s));
        Object cdr = params.get(0).evaluate(s);
        if (cdr != null)
            result.addAll((Collection)cdr);
        return result;
    }

    @Override
    public Object getValue() {
        return "cons";
    }

}
