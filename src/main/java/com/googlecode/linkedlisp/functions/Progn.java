package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Progn extends Function {

    @SuppressWarnings("unchecked")
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object result = null;
        params.iterator();
        for (Object exp : params)  {
            result = s.evaluate(exp);
        }
        return result;
    }

}
