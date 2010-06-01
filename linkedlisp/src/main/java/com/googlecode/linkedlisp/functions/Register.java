package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;

public class Register implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws Exception {
        String name = params.getFirst().getValue(s).toString();
        
        // TODO A lot of this goes away when we add a proper URI resolver for java.
        throw new NoReturnException();
    }

    @Override
    public Object getValue(State s) {
        return "registerFunction";
    }

}
