package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Setf extends Function {
    public Setf() {
    }

    @SuppressWarnings("unchecked")
    @Override
	public Object execute(Environment s, List params) throws Exception {
		String name = params.get(0).toString();	
		return s.setVariable (name, s.evaluate(params.get(1)));
    }

    @Override
    public Object getValue() {
        return "setf";
    }

}
