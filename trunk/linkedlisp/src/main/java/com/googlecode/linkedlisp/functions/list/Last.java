package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.lang.reflect.Array;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.RDFList;

public class Last extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        if (value == null) return null;
        else if (value instanceof List) {
            List l = (List)value;
            if (l.size() == 0) return null;
            else return l.get(l.size()-1);
        } else if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            if (length == 0) return null;
            else return Array.get(value, length - 1);
        } else if (value instanceof RDFList) {
            RDFList l = (RDFList) value;
            if (l.isEmpty()) return null;
            return l.get(l.size());
        } else if (value instanceof String) {
            if (((String)value).length() > 1)
                return ((String)value).substring(((String)value).length()-1);
            else return null;
        } else return value;
    }

    @Override
    public Object getValue() {
        return "last";
    }

}
