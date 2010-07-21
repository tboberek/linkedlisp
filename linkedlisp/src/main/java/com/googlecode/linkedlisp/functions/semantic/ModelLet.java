package com.googlecode.linkedlisp.functions.semantic;

import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

public class ModelLet extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        
        Model model = (Model)params.get(0).evaluate(s);

        State sPrime = s.copyForLet(new ListExpression());
        sPrime.setModel(model);
        Expression execution = params.get(1);
        return execution.evaluate(sPrime);
    }

    @Override
    public Object getValue() {
        return "model.let";
    }

}
