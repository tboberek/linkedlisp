package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;

public class Not extends Function {
	public Not() {
	}

	@Override
	public Object execute(State s, ListExpression params) throws Exception {
		Object result = null;

		// Not only takes on parameter!
		if (params.size() > 1) {
			throw new Exception();
		}

		if (null == params.get(0).evaluate(s)) {
			result = true;
		}

		// Return our calculated value
		return result;
	}

	@Override
	public Object getValue() {
		return "not";
	}
}

