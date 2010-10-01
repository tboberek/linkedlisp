package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ArithmeticFunction;
import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.ListExpression;
import java.util.List;

public class Mod extends ArithmeticFunction {
	public Mod() {
	}

	@Override
	public Object getValue() {
		return "%";
	}

	@Override
	public Object execute(Environment s, List params) throws Exception {
		// Mod only takes two parameters
		if (params.size() > 2)
		{
			throw new Exception();
		}

		Double first 	= s.resolveAsFloat(params.get(0));
		Double second	= s.resolveAsFloat(params.get(1));

		return first % second;

	}

	public Double operation (Double current, Double factor) {
		// This is a no-op for this class - we completely
		// override execute to handle modulus correctly
		return 0d;
	}

}

