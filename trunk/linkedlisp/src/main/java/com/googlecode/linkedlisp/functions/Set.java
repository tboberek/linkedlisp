package com.googlecode.linkedlisp.functions;

import java.util.List;
import java.util.Map;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;


@SuppressWarnings("unchecked")
public class Set extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        if (params.size() > 2) {
            Object value = s.resolve(params.get(0));
            if (value instanceof Resource) {
                return setSemantic((Resource)value, s, params);
            } else if (value instanceof List) {
                int index = s.resolveAsInteger(s.evaluate(params.get(1))).intValue();
                Object val = s.evaluate(params.get(2));
                return ((List)value).set(index, val);
            } else if (value instanceof Map) {
                Object key = s.evaluate(params.get(1));
                Object val = s.evaluate(params.get(2));
                return ((Map)value).put(key, val);
            } else {
                return setJava(value, s, params);
            }
        } else if (params.size() == 2) {
            return s.getVariables().put(params.get(0).toString(),
                                        s.evaluate(params.get(1)));
        } else return null;
    }

    private Object setSemantic(Resource subject, Environment s, List params) throws Exception {
        Property p = s.resolveAsResource(s.evaluate(params.get(1))).as(Property.class);
        if (params.size() == 3) {
            Object o = s.evaluate(params.get(2));
            if (o instanceof Resource)
                subject.addProperty(p, (Resource)o);
            else subject.addLiteral(p, o);
        }
        return subject;
    }

    private Object setJava(Object value, Environment s, List params) {
        // TODO Auto-generated method stub
        return null;
    }

}
