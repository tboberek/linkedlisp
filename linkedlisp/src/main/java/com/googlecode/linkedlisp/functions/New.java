package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.IDExpression;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class New extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value instanceof Class){
            return newJava((Class)value, s, params);
        } else {
            return newSemantic(value.toString(), s, params);
        }
    }

    private Object newSemantic(String ontClass, State s, ListExpression params) throws Exception {
        Resource c = s.getModel().createResource(ontClass);
        if (params.size() == 1) {
            return s.getModel().createResource(c);
        } else {
            String uri = params.get(1).evaluate(s).toString();
            return s.getModel().createResource(uri, c);
        }
    }

    private Object newJava(Class clazz, State s, ListExpression params) throws Exception {
        
        if(params.get(1) instanceof IDExpression) {
            Object p1 = ((IDExpression) params.get(1)).evaluate(s);
            Constructor<?> cons = clazz.getConstructor(p1.getClass());
            return cons.newInstance(p1);
        }
        
        throw new RuntimeException("TODO");
    }

    @Override
    public Object getValue() {
        return "new";
    }

}
