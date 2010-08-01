package com.googlecode.linkedlisp;

import java.util.List;

public abstract class ArithmeticFunction extends Function {
	public abstract Double operation (Double current, Double factor);

	@SuppressWarnings("unchecked")
    @Override
	public Object execute(Environment s, List params) throws Exception {
		Double result = 0d;

        for (Object exp : params)  {
			Double factor = Double.valueOf(s.evaluate(exp).toString());
			result = operation(result, factor);
        }

		// Return our calculated value
		return result;
	}
}

