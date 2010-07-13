package com.googlecode.linkedlisp;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;

public abstract class ComparisonFunction extends Function {
	public abstract boolean operation (Object first, Object next);

	public Float evaluateToFloat (State s, Expression exp) throws Exception {
		String stringFactor = (String) exp.evaluate(s).toString();
		Float factor = Float.valueOf(stringFactor.trim()).floatValue();

		return factor;
	}			

	@Override
	public Object execute(State s, ListExpression params) throws Exception {
		int size = params.size ();

		// If there's only one parameter,
		// we return true
		if (1 == size) {
			return true;
		}

		// Go through all the parameters, comparing each against
	    // the next, until we're done
		for (int i = 0; i < size - 1; i++)
		{
			Expression first = params.get(i);
			Expression next = params.get(i + 1);

			// Compare the two parameters we have
			// against each other
			if (!operation(first.evaluate(s), next.evaluate(s))) {
				// Once we've failed, we're done.
				return null;
			}
        }

		// If we get through all the parameters,
		// we're done
		return true;
	}
}

