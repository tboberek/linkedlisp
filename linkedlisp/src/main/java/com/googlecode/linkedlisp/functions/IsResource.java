package com.googlecode.linkedlisp.functions;

import java.net.URI;
import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.hp.hpl.jena.rdf.model.Resource;

public class IsResource extends Function {

    @Override
	@SuppressWarnings("unchecked")
    public Object execute(Environment s, List params) throws Exception {
        
        Object x = s.evaluate(params.get(0));
        if (x instanceof Resource || x instanceof URI) return x;
        else return null;
    }

}
