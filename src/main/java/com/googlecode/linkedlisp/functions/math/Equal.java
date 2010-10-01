package com.googlecode.linkedlisp.functions.math;

import com.googlecode.linkedlisp.ComparisonFunction;

public class Equal extends ComparisonFunction {
	public Equal() {
	}

	@Override
	public Object getValue() {
		return "=";
	}

	public boolean operation (Object first, Object second) {
            if (first != null)
		return first.equals(second);
            else return second == null;
	}
}

