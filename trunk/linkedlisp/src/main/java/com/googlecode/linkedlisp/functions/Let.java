package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;

public class Let extends Function {

    @SuppressWarnings("unchecked")
    public Object execute(Environment s, List params) throws Exception {
        
        Object x = params.get(0);

        Environment sPrime = s.copyForScope((ListExpression)x, s);
        Object execution = params.get(1);
        return sPrime.evaluate(execution);
    }

    @Override
    public Object getValue() {
        return "let";
    }

}
