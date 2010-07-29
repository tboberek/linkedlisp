package com.googlecode.linkedlisp.functions.semantic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import java.util.List;

public class Save extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        String location = s.evaluate(params.get(0)).toString();
        String lang = params.size() > 1 ? s.evaluate(params.get(1)).toString() : null;
        String base = params.size() > 2 ? s.evaluate(params.get(2)).toString() : null;
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
