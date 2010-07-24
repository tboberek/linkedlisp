package com.googlecode.linkedlisp.functions.semantic;

import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Prefix extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
    	Object val = params.getFirst().getValue();
    	if(val instanceof String && params.size() == 2) {
	        String prefixName = (String) val;
	        Resource prefixResource = (Resource) params.get(1).evaluate(s);
	        s.setPrefix(prefixName, prefixResource.getURI());
    	} else if(val instanceof List && params.size() == 2) {
    		for(Object o : (List) val) {
    			List l = (List) o;
    			if(l.size() != 2)
    				throw new RuntimeException("TODO");
    			String prefixName = (String) l.get(0);
    	        Resource prefixResource = (Resource) l.get(1);
    	        s.setPrefix(prefixName, prefixResource.getURI());
    		}
    	} else
    		throw new RuntimeException("TODO");

        return null;
    }

    @Override
    public Object getValue() {
        return "prefix";
    }

}
