package com.googlecode.linkedlisp.functions.semantic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class Save extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        String location = params.getFirst().evaluate(s).toString();
        String lang = params.size() > 1 ? params.get(1).evaluate(s).toString() : null;
        String base = params.size() > 2 ? params.get(2).evaluate(s).toString() : null;
        try {
            s.getModel().write(new FileOutputStream(location),lang,base);
            return Boolean.TRUE;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public Object getValue() {
        return "save";
    }

}
