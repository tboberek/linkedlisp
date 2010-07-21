package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Length extends Function {
    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Collection<Object> coll = (Collection<Object>) params.get(0).evaluate(s);
        return coll.size();
    }

    @Override
    public Object getValue() {
        return "length";
    }

}
