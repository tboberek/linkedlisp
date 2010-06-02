package com.googlecode.linkedlisp.functions;

import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Defun extends Function {

    public Object evaluate(State s) throws Exception {
        ListExpression params = s.getParameterList();
        
        String name = params.get(0).getValue().toString();

        Object x = params.get(1).evaluate(s);
        if (x instanceof Resource) {
            Resource res = (Resource)x;
            String classURI = res.getURI();
            String[] className = classURI.split("://");
            if (className[0].equals("java")) {
                Class functionClass = Class.forName(className[1]);
                Function function = (Function) functionClass.newInstance();
                s.getGlobalVariables().put(name, function);
            } else {
                // TODO: Bind against property URIs.
            }
        } else {
            LispFunction fn = new LispFunction();
            fn.setParameterNames((List<String>)x);
            fn.setBody(params.get(2));
            s.getGlobalVariables().put(name, fn);
        }
        return null;
    }

    @Override
    public Object getValue() {
        return "defun";
    }

}
