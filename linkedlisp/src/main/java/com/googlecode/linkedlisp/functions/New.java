/**
 * 
 */
package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Constructor;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.IDExpression;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Pawel
 *
 */
public class New extends Function {
	
	@Override
	public Object execute(State s, ListExpression params) throws Exception {
		Resource res =  (Resource) params.getFirst().evaluate(s);

		String className = res.getLocalName();
		String namespace = res.getNameSpace();
		
		if(namespace.equals("java://")) {
			Class<?> clazz = Class.forName(className);
			
			if(params.get(1) instanceof IDExpression) {
				Object p1 = ((IDExpression) params.get(1)).evaluate(s);
				Constructor<?> cons = clazz.getConstructor(p1.getClass());
				return cons.newInstance(p1);
			}
			
			throw new RuntimeException("TODO");
		}
			
		throw new RuntimeException("unexpected namespace: " + namespace);
	}
	
    @Override
	public Object getValue() {
        return "new";
    }

}
