package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.ArithmeticFunction;

public class Divide extends ArithmeticFunction {
	public Divide() {
	}

	@Override
	public Object getValue() {
		return "/";
	}

	public Float operation (Float current, Float factor) {
		current /= factor;
		return current;
	}

}

