package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Append extends Function {
    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Collection<Object> result = (Collection<Object>) params.get(0).evaluate(s);
        result.add(params.get(1).evaluate(s));
        return result;
    }

    @Override
    public Object getValue() {
        return "append";
    }

}
