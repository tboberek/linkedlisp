package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.RDFList;

public class If extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value != null) {
            return params.get(1).evaluate(s);
        } else if (params.size() > 2) {
            return params.get(2).evaluate(s);
        } else return null;
    }

    @Override
    public Object getValue() {
        return "if";
    }

}
