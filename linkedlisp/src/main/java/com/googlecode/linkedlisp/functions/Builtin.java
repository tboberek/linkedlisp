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
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;

public class Builtin extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        
        String name = (String)params.get(0).getValue();
        Function function = (Function)params.get(1).evaluate(s);
        FunctionBuiltin fb = new FunctionBuiltin(function, name, s);
        BuiltinRegistry.theRegistry.register(fb);
        return fb;
    }

    @Override
    public Object getValue() {
        return "builtin";
    }

}
