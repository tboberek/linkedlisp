package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Cons extends Function {
    @Override
	public Object execute(State s, ListExpression params) throws Exception {
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
