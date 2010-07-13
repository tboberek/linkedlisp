package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;

public class Or extends Function {
	public Or() {
	}

	@Override
	public Object execute(State s, ListExpression params) throws Exception {
		Object result = null;

        for (Expression exp : params)  {
			result = exp.evaluate(s);

			if (null != result) {
				break;
			}
        }

		// Return our calculated value
		return result;
	}

	@Override
	public Object getValue() {
		return "or";
	}
}

