package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Evaluate extends Function {

    @SuppressWarnings("unchecked")
    @Override
	public Object execute(Environment s, List params) throws Exception {
        return s.evaluate(params);
    }

    @Override
    public Object getValue() {
        return "eval";
    }

}
