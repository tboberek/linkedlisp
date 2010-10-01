package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ArithmeticFunction;

public class Multiply extends ArithmeticFunction {
	public Multiply() {
	}

	@Override
	public Object getValue() {
		return "*";
	}

	public Double operation (Double current, Double factor) {
		current *= factor;
		return current;
	}

}

