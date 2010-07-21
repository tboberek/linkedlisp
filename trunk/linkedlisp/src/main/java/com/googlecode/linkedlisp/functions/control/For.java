package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;
import com.googlecode.linkedlisp.functions.Let;

public class For extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        
        String variable = params.get(0).getValue().toString();
        // Skip param 1, as this is the "in"
        Iterable it = (Iterable) params.get(2).evaluate(s);
        Expression exec = params.get(3);
        State sPrime = s.copyForLet(new ListExpression());
        Object lastResult = null;
        for (Object val : it) {
            sPrime.setVariable(variable, val);
            lastResult = exec.evaluate(sPrime);
        }
		return lastResult;
    }

    @Override
    public Object getValue() {
        return "for";
    }

}
