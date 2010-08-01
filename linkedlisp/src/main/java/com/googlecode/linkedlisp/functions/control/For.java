package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;

import java.util.LinkedList;
import java.util.List;
import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.functions.Let;

public class For extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        
        String variable = params.get(0).toString();
        // Skip param 1, as this is the "in"
        Iterable it = (Iterable) s.evaluate(params.get(2));
        Object exec = params.get(3);
        Environment sPrime = s.copyForScope(new LinkedList(), s);
        Object lastResult = null;
        for (Object val : it) {
            sPrime.setVariable(variable, val);
            lastResult = sPrime.evaluate(exec);
        }
		return lastResult;
    }

    @Override
    public Object getValue() {
        return "for";
    }

}