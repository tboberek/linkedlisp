package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class If extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value != null) {
            return params.get(1).evaluate(s);
        } else if (params.size() > 2) {
            return params.get(2).evaluate(s);
        } else return null;
    }

    @Override
    public Object getValue() {
        return "if";
    }

}
