package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Constructor;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class New extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value instanceof Class){
            return newJava((Class)value, s, params);
        } else {
            return newSemantic(value.toString(), s, params);
        }
    }

    private Object newSemantic(String ontClass, State s, ListExpression params) throws Exception {
        Resource c = s.getModel().createResource(ontClass);
        if (params.size() == 1) {
            return s.getModel().createResource(c);
        } else {
            String uri = params.get(1).evaluate(s).toString();
            return s.getModel().createResource(uri, c);
        }
    }

    // Look at http://commons.apache.org/beanutils/v1.8.3/apidocs/org/apache/commons/beanutils/ConstructorUtils.html
    private Object newJava(Class clazz, State s, ListExpression params) throws Exception {

    	Object[] objs = new Object[params.size() - 1];
    	Class[] classes = new Class[params.size() - 1];
    	for(int x = 1; x < params.size(); x++) {
    		objs[x - 1] = params.get(x).evaluate(s);
    		classes[x - 1] = objs[x - 1].getClass();
    	}
    	
    	try {
	    	Constructor<?> cons = clazz.getConstructor(classes);
	        return cons.newInstance(objs);
    	} catch (NoSuchMethodException e) {
    		Constructor bestCons = null;
    		int bestScore = Integer.MAX_VALUE;
    		
    		// no exact match, let's try some heuristic
    		// find a compatible constructor, breaking up ties based on minimizing the number of upcasts
    		consLoop:
    		for(Constructor c : clazz.getConstructors()) {
    			if(c.getParameterTypes().length == classes.length) {
    				int score = 0;
    				for(int x = 0; x < classes.length; x++) {
    					if(!c.getParameterTypes()[x].isAssignableFrom(classes[x]))
    						continue consLoop;
    					
    					
    					for(Class tempClass = classes[x]; !tempClass.equals(c.getParameterTypes()[x]); score++, tempClass = tempClass.getSuperclass() )
    					{}
    				}
    				
        			if(score < bestScore) {
        				bestScore = score;
        				bestCons = c;
        			}
    			}
    		}
    		
    		if(bestCons != null)
    			return bestCons.newInstance(objs);

    		// got nothing
    		throw e;
    	}
    }

    @Override
    public Object getValue() {
        return "new";
    }

}
