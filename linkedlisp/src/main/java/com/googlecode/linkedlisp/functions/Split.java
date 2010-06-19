package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;


public class Split extends Function {

    @Override
    public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        String regex = params.get(1).evaluate(s).toString().replace("\\\\", "\\");
        if (value == null) return null;
        return new ArrayList(Arrays.asList(value.toString().split(regex)));
    }

    @Override
    public Object getValue() {
        return "split";
    }

}
