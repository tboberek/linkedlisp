package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Defun implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws Exception {
        String name = params.getFirst().getValue(s).toString();

        if (params.get(1) instanceof ResourceExpression) {
            Resource classResource = (Resource)params.get(1).evaluate(s);
            String classURI = classResource.getURI();
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
            fn.setParameterNames((ListExpression)params.get(1));
            fn.setBody(params.get(2));
            s.getVariables().put(name, fn);
        }
        throw new NoReturnException();
    }

    @Override
    public Object getValue(State s) {
        return "defun";
    }

}
