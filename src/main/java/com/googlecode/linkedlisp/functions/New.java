package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Constructor;
import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.hp.hpl.jena.rdf.model.Resource;

@SuppressWarnings("unchecked")
public class New extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));

        if (value instanceof Resource && ( (Resource)value).getURI().startsWith("java://")){
            return newJava(Class.forName(((Resource)value).getURI().substring(7)), s, params);
        } else {
            return newSemantic(value.toString(), s, params);
        }
    }

    private Object newSemantic(String ontClass, Environment s, List params) throws Exception {
        Resource c = s.getModel().createResource(ontClass);
        if (params.size() == 1) {
            return s.getModel().createResource(c);
        } else {
            return s.resolveAsResource(params.get(1));
        }
    }

    // Look at http://commons.apache.org/beanutils/v1.8.3/apidocs/org/apache/commons/beanutils/ConstructorUtils.html
    private Object newJava(Class clazz, Environment s, List params) throws Exception {

    	Object[] objs = new Object[params.size() - 1];
    	Class[] classes = new Class[params.size() - 1];
    	for(int x = 1; x < params.size(); x++) {
    		objs[x - 1] = s.resolve(params.get(x));
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

}
