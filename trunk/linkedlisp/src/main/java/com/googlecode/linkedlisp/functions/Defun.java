package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;

public class Defun extends Function {

    private final Lambda lambda = new Lambda();
    
    @Override
    public Object execute(State s, ListExpression params) throws Exception {
        String name = params.get(0).getValue().toString();
        Object function = lambda.execute(s, params.getRest());
        s.getGlobalVariables().put(name, function);
        return null;
    }

    @Override
    public Object getValue() {
        return "defun";
    }

}
