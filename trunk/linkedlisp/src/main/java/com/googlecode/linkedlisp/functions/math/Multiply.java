package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ArithmeticFunction;

public class Multiply extends ArithmeticFunction {
	public Multiply() {
	}

	@Override
	public Object getValue() {
		return "*";
	}

	public Float operation (Float current, Float factor) {
		current *= factor;
		return current;
	}

}

