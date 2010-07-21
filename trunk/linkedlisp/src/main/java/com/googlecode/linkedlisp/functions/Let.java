package com.googlecode.linkedlisp.functions;

import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Let extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        
        Expression x = params.get(0);

        State sPrime = s.copyForLet((ListExpression)x);
        Expression execution = params.get(1);
        return execution.evaluate(sPrime);
    }

    @Override
    public Object getValue() {
        return "let";
    }

}
