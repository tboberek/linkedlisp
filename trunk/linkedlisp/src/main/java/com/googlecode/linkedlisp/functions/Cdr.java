package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.RDFList;

public class Cdr extends Function {

    public Object evaluate(State s) throws Exception {
        Object value = s.getParameterList().getFirst().evaluate(s);
        if (value instanceof Collection<?>) {
            List result = new ArrayList((Collection)value);
            result.remove(0);
            return result;
        } else if (value.getClass().isArray()) {
            List result = new ArrayList(Arrays.asList(value));
            result.remove(0);
            return result;
        } else if (value instanceof RDFList) {
            RDFList l = (RDFList) value;
            return l.getTail();
        } else if (value instanceof String) {
            return ((String)value).substring(1);
        } else return value;
    }

    @Override
    public Object getValue() {
        return "cons";
    }

}
