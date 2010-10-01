package com.googlecode.linkedlisp.functions.semantic;

import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.Resource;

public class Prefix extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
    	Object val = params.get(0);
    	if(params.size() == 2) {
	        String prefixName = val.toString();
	        Resource prefixResource = s.resolveAsResource(s.evaluate(params.get(1)));
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
}
