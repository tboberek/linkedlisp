package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Symbol;

import java.util.LinkedList;
import java.util.List;
import com.googlecode.linkedlisp.Environment;

public class For extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        
        String variable = params.get(0).toString();
        // Skip param 1, as this is the "in"
        Iterable it = (Iterable) s.evaluate(params.get(2));
        Object exec = params.get(3);
        if (exec instanceof Symbol && exec.toString().equals("do"))
            exec = params.get(4);
        Environment sPrime = s.copyForScope(new LinkedList(), s);
        Object lastResult = null;
        for (Object val : it) {
            sPrime.setVariable(variable, val);
            lastResult = sPrime.evaluate(exec);
        }
		return lastResult;
    }

}
