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
        String name = "binding"+UUID.randomUUID().toString();
        
        ListExpression builtinExpression = new ListExpression();
        builtinExpression.append(new IDExpression(name));
        builtinExpression.append(params.get(2));
        FunctionBuiltin fb = (FunctionBuiltin)builtin.execute(s, builtinExpression);
        Function f = fb.getFunction();
        
        List<String> functionParameters = f.getParameterNames();
        
        ListExpression ruleExpression = new ListExpression();
        ruleExpression.append(new IDExpression(name+"Rule"));
        
        ListExpression head = new ListExpression();
        ListExpression body = new ListExpression();

        String valueVar = "o";
        
        ListExpression headClause = new ListExpression();
        headClause.append(new IDExpression(functionParameters.get(0)));
        headClause.append(new ResourceExpression(property));
        headClause.append(new IDExpression(valueVar));
        
        head.append(headClause);
        
        ListExpression builtinCallParams = new ListExpression();
        builtinCallParams.append(new IDExpression(valueVar));
        for (String paramName : functionParameters) {
            builtinCallParams.append(new IDExpression(paramName));
        }

        ListExpression builtinCall = new ListExpression();
        builtinCall.append(new IDExpression(name));
        builtinCall.append(builtinCallParams);
        
        body.append(builtinCall);
        ListExpression bindingCondition = (ListExpression)params.get(1);
        for (Expression e : bindingCondition) {
            body.append(e);
        }
        
        ListExpression r = new ListExpression();
        r.append(new IDExpression("rule_"+name));
        r.append(head);
        r.append(new IDExpression("<-"));
        r.append(body);
        
        return rule.execute(s, r);
    }

    @Override
    public Object getValue() {
        return "builtin";
    }

}
