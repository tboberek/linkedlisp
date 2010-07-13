package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.ComparisonFunction;

public class GreaterThanOrEqual extends ComparisonFunction {
	public GreaterThanOrEqual() {
	}

	@Override
	public Object getValue() {
		return ">=";
	}

	public boolean operation (Object first, Object second) {
		Float firstNum = Float.valueOf(first.toString().trim()).floatValue();
		Float secondNum = Float.valueOf(second.toString().trim()).floatValue();

		return (firstNum >= secondNum);
	}
}

