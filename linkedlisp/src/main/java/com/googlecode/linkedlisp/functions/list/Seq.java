package com.googlecode.linkedlisp.functions.list;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.googlecode.linkedlisp.util.Sequence;

public class Seq extends Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Number start = 0;
        Number increment = 1;
        Number end = 0;
        if (params.size() == 1) {
            end = (Number) params.get(0).evaluate(s);
        } else if (params.size() == 2) {
            start = (Number) params.get(0).evaluate(s);
            end = (Number) params.get(1).evaluate(s);
        } else if (params.size() >= 3) {
            start = (Number) params.get(0).evaluate(s);
            end = (Number) params.get(1).evaluate(s);
            increment = (Number) params.get(2).evaluate(s);
        }
        return new Sequence(start, end, increment);
    }

    @Override
    public Object getValue() {
        return "seq";
    }

}
