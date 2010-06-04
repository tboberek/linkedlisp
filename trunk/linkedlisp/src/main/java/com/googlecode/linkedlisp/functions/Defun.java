package com.googlecode.linkedlisp.functions;

import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Defun extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        
        String name = params.get(0).getValue().toString();

        Expression x = params.get(1);
        if (x instanceof ResourceExpression) {
            Resource res = (Resource)x.evaluate(s);
            String classURI = res.getURI();
            String[] className = classURI.split("://");
            if (className[0].equals("java")) {
                Class functionClass = Class.forName(className[1]);
                Function function = (Function) functionClass.newInstance();
                s.getGlobalVariables().put(name, function);
            } else {
                // TODO: Bind against property URIs.
            }
        } else if (x instanceof ListExpression){
            LispFunction fn = new LispFunction();
            fn.setParameterNames((List<String>)x.getValue());
            fn.setBody(params.get(2));
            s.getGlobalVariables().put(name, fn);
        } else {
            System.err.println("I don't know what to do with this:\t"+x);
        }
        return null;
    }

    @Override
    public Object getValue() {
        return "defun";
    }

}
