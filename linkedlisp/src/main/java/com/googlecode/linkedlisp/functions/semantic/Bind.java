package com.googlecode.linkedlisp.functions.semantic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Symbol;
import com.hp.hpl.jena.rdf.model.Resource;

public class Bind extends Function {

    private Builtin builtin = new Builtin();
    private Rule rule = new Rule();
    
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        builtin.setEnvironment(environment);
        rule.setEnvironment(environment);
    }
    
    @SuppressWarnings("unchecked")
    public Object execute(Environment s, List params) throws Exception {
        Resource property = s.resolveAsResource(s.evaluate(params.get(0)));
        String name = "binding"+UUID.randomUUID().toString();
        
        ListExpression builtinExpression = new ListExpression();
        builtinExpression.append(new Symbol(name));
        builtinExpression.append(params.get(2));
        FunctionBuiltin fb = (FunctionBuiltin)builtin.execute(s, builtinExpression);
        Function f = fb.getFunction();
        
        List functionParameters = f.getParameterNames();
        
        ListExpression ruleExpression = new ListExpression();
        ruleExpression.append(new Symbol(name+"Rule"));
        
        ListExpression head = new ListExpression();
        ListExpression body = new ListExpression();

        Symbol valueVar = new Symbol("o");
        
        ListExpression headClause = new ListExpression();
        headClause.append(functionParameters.get(0));
        headClause.append(property);
        headClause.append(valueVar);
        
        head.append(headClause);
        
        List builtinCallParams = new ArrayList();
        builtinCallParams.add(builtinCallParams.size(), valueVar);
        for (Object paramName : functionParameters) {
            builtinCallParams.add(builtinCallParams.size(), paramName);
        }

        List builtinCall = new ArrayList();
        builtinCall.add(builtinCall.size(), new Symbol(name));
        builtinCall.add(builtinCall.size(), builtinCallParams);
        
        List bindingCondition = (List)params.get(1);
        for (Object e : bindingCondition) {
            body.append(e);
        }
        body.append(builtinCall);
        
        List r = new ArrayList();
        r.add(r.size(), new Symbol("rule_"+name));
        r.add(r.size(), head);
        r.add(r.size(), new Symbol("<-"));
        r.add(r.size(), body);
        
        return rule.execute(s, r);
    }

    @Override
    public Object getValue() {
        return "builtin";
    }

}
