package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Mod extends Function {
    public Mod() {
    }

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
		// First, get the string values of the parameters
		String first	= (String) params.get(0).getValue();
		String second	= (String) params.get(1).getValue();

		// Then, attempt to convert the values into floats
		Float firstNum	= Float.valueOf(first.trim()).floatValue();
		Float secondNum = Float.valueOf(second.trim()).floatValue();

		// Now actually calculate mod
        return firstNum % secondNum;
    }

    @Override
    public Object getValue() {
        return "print";
    }

}
