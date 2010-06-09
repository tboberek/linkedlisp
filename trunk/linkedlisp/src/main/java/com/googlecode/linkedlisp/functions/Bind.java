package com.googlecode.linkedlisp.functions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.IDExpression;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;

public class Bind extends Function {

    private Builtin builtin = new Builtin();
    private Rule rule = new Rule();
    
    public Object execute(State s, ListExpression params) throws Exception {
        Resource property = (Resource)params.get(0).evaluate(s);
        String name = UUID.randomUUID().toString();
        
        ListExpression builtinExpression = new ListExpression();
        builtinExpression.append(new IDExpression(name));
        builtinExpression.append(params.get(2));
        builtin.execute(s, builtinExpression);
        
        ListExpression ruleExpression = new ListExpression();
        ruleExpression.append(new IDExpression(name+"Rule"));
        
        ListExpression head = new ListExpression();
        ListExpression body = new ListExpression();

        String valueVar = UUID.randomUUID().toString();
        
        ListExpression headClause = new ListExpression();
        
        Function function = (Function)params.get(2).evaluate(s);
        FunctionBuiltin fb = new FunctionBuiltin(function, name, s);
        BuiltinRegistry.theRegistry.register(fb);

        return null;
    }

    @Override
    public Object getValue() {
        return "builtin";
    }

}
