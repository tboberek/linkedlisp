package com.googlecode.linkedlisp.functions.semantic;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.hp.hpl.jena.rdf.model.Model;

public class ModelLet extends Function {

    public Object execute(Environment s, List params) throws Exception {
        
        Model model = (Model)s.evaluate(params.get(0));

        Environment sPrime = s.copyForScope(new ListExpression(), s);
        sPrime.setModel(model);
        Object execution = params.get(1);
        return sPrime.evaluate(execution);
    }

    @Override
    public Object getValue() {
        return "model.let";
    }

}
