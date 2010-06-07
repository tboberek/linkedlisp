package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.RDFList;

public class Car extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value == null) {
            System.out.println(params.getFirst());
            return null;
        }
        else if (value instanceof Iterable) {
            Iterator i = ((Iterable)value).iterator();
            if (i.hasNext()) return i.next();
            else return null;
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) > 0)
                return Array.get(value, 0);
            else return null;
        } else if (value instanceof RDFList) {
            return ((RDFList)value).getHead();
        } else if (value instanceof String) {
            if (((String)value).length() > 0)
                return ((String)value).charAt(0);
            else return null;
        } else return value;
    }

    @Override
    public Object getValue() {
        return "car";
    }

}
