package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ArithmeticFunction;

public class Plus extends ArithmeticFunction {
	public Plus() {
	}

	@Override
	public Object getValue() {
		return "+";
	}

	public Float operation (Float current, Float factor) {
		current += factor;
		return current;
	}

}

