package com.googlecode.linkedlisp;

import java.util.List;

public abstract class ComparisonFunction extends Function {
	public abstract boolean operation (Object first, Object next);

	public Float evaluateToFloat (Environment s, Object exp) throws Exception {
		String stringFactor = (String) s.evaluate(exp).toString();
		Float factor = Float.valueOf(stringFactor.trim()).floatValue();

		return factor;
	}			

	@SuppressWarnings("unchecked")
    @Override
	public Object execute(Environment s, List params) throws Exception {
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
			Object first = params.get(i);
			Object next = params.get(i + 1);

			// Compare the two parameters we have
			// against each other
			if (!operation(s.evaluate(first), s.evaluate(next))) {
				// Once we've failed, we're done.
				return null;
			}
        }

		// If we get through all the parameters,
		// we're done
		return true;
	}
}

