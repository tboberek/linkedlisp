package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class Or extends Function {
	public Or() {
	}

	@Override
	public Object execute(Environment s, List params) throws Exception {
		Object result = null;

        for (Object exp : params)  {
			result = s.evaluate(exp);

			if (null != result) {
				break;
			}
        }

		// Return our calculated value
		return result;
	}
}

