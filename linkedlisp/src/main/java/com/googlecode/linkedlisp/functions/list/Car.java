package com.googlecode.linkedlisp.functions.list;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.RDFList;

public class Car extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        if (value == null) {
            System.out.println(params.get(0));
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
