package com.googlecode.linkedlisp.functions.semantic;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class Load extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value instanceof Resource) {
            Resource res = (Resource)value;
            FileManager.get().readModel(s.getModel(),res.getURI());
        } else {
            FileManager.get().readModel(s.getModel(),value.toString());
        }
        return null;
    }

    @Override
    public Object getValue() {
        return "load";
    }

}
