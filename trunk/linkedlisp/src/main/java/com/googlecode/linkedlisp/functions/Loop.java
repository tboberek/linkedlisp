package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;

public class Loop extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
		Object lastResult = null;

		// If the first result is not null, continue on
		// and evaluate all the other parameters
		while (null != params.get(0).evaluate(s))
		{
			for (int i = 1; i < params.size(); i++)
			{
				lastResult = params.get(i).evaluate(s);
			}
        }

		return lastResult;
    }

    @Override
    public Object getValue() {
        return "loop";
    }

}
