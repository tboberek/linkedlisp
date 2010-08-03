/**
 * 
 */
package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Method;
import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Pawel
 *
 */
public class Call extends Function {
	
	@Override
	public Object execute(Environment s, List params) throws Exception {
		if(params.size() < 2)
			throw new RuntimeException("not enough params: " + params);
		
		Object obj = s.resolve(params.get(0));
		
		Object instance;
		Class clazz;
		
		if(obj instanceof Resource) {
			// classname methodname args
			if (((Resource) obj).getURI().startsWith("java://")) {
				instance = null;
				clazz = Class.forName(((Resource) obj).getURI().substring(7));
			} else
				throw new RuntimeException("unrecognized resource: " + params);
        	  
		} else {
			// object methodname args
			instance = s.evaluate(obj);
			clazz = instance.getClass();
		}
		
		String methodName = params.get(1).toString();
		Object[] args = new Object[params.size() - 2];
		Class[] argClasses = new Class[params.size() - 2];
		for(int x = 0; x < args.length; x++) {
			args[x] = s.resolve(params.get(x + 2));
			argClasses[x] = args[x].getClass();
		}
		
		Method method;
		try {
			// try exact match
			method = clazz.getMethod(methodName, argClasses);
		} catch(NoSuchMethodException e) {
			Method bestMethod = null;
    		int bestScore = Integer.MAX_VALUE;
    		
    		// no exact match, let's try some heuristic
    		// find a compatible method, breaking up ties based on minimizing the number of upcasts
    		consLoop:
    		for(Method m : clazz.getMethods()) {
    			if(m.getParameterTypes().length == argClasses.length && m.getName().equals(methodName)) {
    				int score = 0;
    				for(int x = 0; x < argClasses.length; x++) {
    					if(!m.getParameterTypes()[x].isAssignableFrom(argClasses[x]))
    						continue consLoop;
    					
    					
    					for(Class tempClass = argClasses[x]; !tempClass.equals(m.getParameterTypes()[x]); score++, tempClass = tempClass.getSuperclass() )
    					{}
    				}
    				
        			if(score < bestScore) {
        				bestScore = score;
        				bestMethod = m;
        			}
    			}
    		}
    		
    		if(bestMethod != null)
    			method = bestMethod;
    		else
	    		// got nothing
	    		throw e;
		}

		return method.invoke(instance, args);
	}
}
