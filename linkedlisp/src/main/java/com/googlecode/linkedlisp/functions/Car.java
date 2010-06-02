package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.RDFList;

public class Car extends Function {

    public Object evaluate(State s) throws Exception {
        Object value = s.getParameterList().getFirst().evaluate(s);
        if (value instanceof Iterable) {
            return ((Iterable)value).iterator().next();
        } else if (value.getClass().isArray()) {
            return Array.get(value, 0);
        } else if (value instanceof RDFList) {
            return ((RDFList)value).getHead();
        } else if (value instanceof String) {
            return ((String)value).charAt(0);
        } else return value;
    }

    @Override
    public Object getValue() {
        return "car";
    }

}
