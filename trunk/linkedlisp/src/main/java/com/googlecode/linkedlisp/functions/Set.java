package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.List;
import java.util.Map;


public class Set extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value instanceof Resource) {
            return setSemantic((Resource)value, s, params);
        } else if (value instanceof List) {
            int index = ((Number)params.get(1).evaluate(s)).intValue();
            Object val = params.get(2).evaluate(s);
            return ((List)value).set(index, value);
        } else if (value instanceof Map) {
            Object key = params.get(1).evaluate(s);
            Object val = params.get(2).evaluate(s);
            return ((Map)value).put(key, value);
        } else {
            return setJava(value, s, params);
        }
    }

    private Object setSemantic(Resource subject, State s, ListExpression params) throws Exception {
        Property p = ((Resource)params.get(1).evaluate(s)).as(Property.class);
        if (params.size() == 3) {
            Object o = params.get(2).evaluate(s);
            if (o instanceof Resource)
                subject.addProperty(p, (Resource)o);
            else subject.addLiteral(p, o);
        }
        return subject;
    }

    private Object setJava(Object value, State s, ListExpression params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getValue() {
        return "set";
    }

}
