package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.RDFList;

public class Cdr extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value == null) return null;
        else if (value instanceof Collection<?>) {
            if (((Collection)value).size() > 1) {
                List result = new ArrayList((Collection)value);
                result.remove(0);
                return result;
            } else return null;
        } else if (value.getClass().isArray()) {
            List result = new ArrayList(Arrays.asList(value));
            if (result.size() < 2) return null;
            else {
                result.remove(0);
                return result;
            }
        } else if (value instanceof RDFList) {
            RDFList l = (RDFList) value;
            return l.getTail();
        } else if (value instanceof String) {
            if (((String)value).length() > 1)
                return ((String)value).substring(1);
            else return null;
        } else return value;
    }

    @Override
    public Object getValue() {
        return "cdr";
    }

}
