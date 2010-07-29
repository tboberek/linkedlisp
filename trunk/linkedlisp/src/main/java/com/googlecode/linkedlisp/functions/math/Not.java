package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class Not extends Function {
	public Not() {
	}

	@Override
	public Object execute(Environment s, List params) throws Exception {
		Object result = null;

		// Not only takes on parameter!
		if (params.size() > 1) {
			throw new Exception();
		}

		if (null == s.evaluate(params.get(0))) {
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

