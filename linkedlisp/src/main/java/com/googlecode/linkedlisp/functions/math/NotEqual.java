package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ComparisonFunction;

public class NotEqual extends ComparisonFunction {
	public NotEqual() {
	}

	@Override
	public Object getValue() {
		return "!=";
	}

	public boolean operation (Object first, Object second) {
		return (first != second);
	}
}

