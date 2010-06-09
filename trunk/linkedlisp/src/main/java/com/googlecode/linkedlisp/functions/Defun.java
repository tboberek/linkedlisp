package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Defun extends Function {

    private Lambda lambda = new Lambda();
    
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
