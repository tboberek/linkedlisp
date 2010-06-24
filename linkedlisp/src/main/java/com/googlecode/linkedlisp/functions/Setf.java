package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Setf extends Function {
    public Setf() {
    }

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
		String name = (String) params.get(0).getValue ();	
		return s.setVariable (name, params.get(1).evaluate(s));
    }

    @Override
    public Object getValue() {
        return "setf";
    }

}
