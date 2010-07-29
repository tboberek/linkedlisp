package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.Resource;

@SuppressWarnings({ "unchecked", "unused" })
public class Lambda extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        
        Object x = s.resolve(params.get(0));
        if (x instanceof List){
            LispFunction fn = new LispFunction();
            fn.setParameterNames((List)x);
            fn.setBody(s.resolveAsList(params.get(1)));
            fn.setEnvironment(s);
            return fn;
        } else {
            Resource res = s.resolveAsResource(params.get(0));
            if (res.getURI().startsWith("java://")) {
                Class cl = Class.forName(res.getURI().substring(7));
                Function function = (Function) cl.newInstance();
                function.setEnvironment(s);
                return function;
            }
            System.err.println("I don't know what to do with this:\t"+x);
        }
        return null;
    }

    @Override
    public Object getValue() {
        return "lambda";
    }

}
