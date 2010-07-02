package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.ArithmeticFunction;

public class Minus extends ArithmeticFunction {
	public Minus() {
	}

	@Override
	public Object getValue() {
		return "-";
	}

	public Float operation (Float current, Float factor) {
		current -= factor;
		return current;
	}

}

