package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ComparisonFunction;

public class GreaterThan extends ComparisonFunction {
	public GreaterThan() {
	}

	@Override
	public Object getValue() {
		return ">";
	}

	public boolean operation (Object first, Object second) {
		Float firstNum = Float.valueOf(first.toString().trim()).floatValue();
		Float secondNum = Float.valueOf(second.toString().trim()).floatValue();
		
		return (firstNum > secondNum);
	}
}

