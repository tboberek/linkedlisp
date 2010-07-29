package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ArithmeticFunction;

public class Divide extends ArithmeticFunction {
	public Divide() {
	}

	@Override
	public Object getValue() {
		return "/";
	}

	public Double operation (Double current, Double factor) {
		current /= factor;
		return current;
	}

}

