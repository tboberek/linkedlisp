package com.googlecode.linkedlisp.functions.semantic;

import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;

public class Builtin extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        String name = params.get(0).toString();
        Function function = s.resolveAsFunction(s.evaluate(params.get(1)));
        FunctionBuiltin fb = new FunctionBuiltin(function, name, s);
        BuiltinRegistry.theRegistry.register(fb);
        return fb;
    }
}
