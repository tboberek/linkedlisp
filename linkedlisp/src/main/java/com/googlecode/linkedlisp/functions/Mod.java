package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.ArithmeticFunction;
import com.googlecode.linkedlisp.State;
import com.googlecode.linkedlisp.ListExpression;

public class Mod extends ArithmeticFunction {
	public Mod() {
	}

	@Override
	public Object getValue() {
		return "%";
	}

	@Override
	public Object execute(State s, ListExpression params) throws Exception {
		// Mod only takes two parameters
		if (params.size() > 2)
		{
			throw new Exception();
		}

		Float first 	= evaluateToFloat (s, params.get(0));
		Float second	= evaluateToFloat (s, params.get(1));

		return first % second;

	}

	public Float operation (Float current, Float factor) {
		// This is a no-op for this class - we completely
		// override execute to handle modulus correctly
		return 0f;
	}

}

