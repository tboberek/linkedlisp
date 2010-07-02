package com.googlecode.linkedlisp;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;

public abstract class ArithmeticFunction extends Function {
	public abstract Float operation (Float current, Float factor);

	public Float evaluateToFloat (State s, Expression exp) throws Exception {
		String stringFactor = (String) exp.evaluate(s).toString();
		Float factor = Float.valueOf(stringFactor.trim()).floatValue();

		return factor;
	}			

	@Override
	public Object execute(State s, ListExpression params) throws Exception {
		Float result = 0f;

        for (Expression exp : params)  {
			Float factor = evaluateToFloat (s, exp);
			result = operation (result, factor);
        }

		// Return our calculated value
		return result;
	}
}

