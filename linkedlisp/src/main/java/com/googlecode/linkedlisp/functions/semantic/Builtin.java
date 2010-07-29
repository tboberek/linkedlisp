package com.googlecode.linkedlisp.functions.semantic;

import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;

public class Builtin extends Function {

    public Object execute(Environment s, List params) throws Exception {
        String name = params.get(0).toString();
        Function function = (Function)s.resolveAsFunction(s.evaluate(params.get(1)));
        FunctionBuiltin fb = new FunctionBuiltin(function, name, s);
        BuiltinRegistry.theRegistry.register(fb);
        return fb;
    }

    @Override
    public Object getValue() {
        return "builtin";
    }

}
