package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.ComparisonFunction;

public class Equal extends ComparisonFunction {
	public Equal() {
	}

	@Override
	public Object getValue() {
		return "=";
	}

	public boolean operation (Object first, Object second) {
		return (first == second);
	}
}

