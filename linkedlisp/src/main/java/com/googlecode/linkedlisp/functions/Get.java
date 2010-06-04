package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Get extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value instanceof Resource) {
            return getSemantic((Resource)value, s, params);
        } else {
            return getJava(value, s, params);
        }
    }

    private Object getSemantic(Resource subject, State s, ListExpression params) throws Exception {
        
        Resource predicate = null;
        if (params.size() > 1) {
            predicate = (Resource)params.get(1).evaluate(s);
            List<RDFNode> foo = subject.as(Individual.class).listPropertyValues(predicate.as(Property.class)).toList();
            List<Object> result = new ArrayList<Object>(foo.size());
            for (RDFNode node : foo) {
                if (node.isLiteral()) {
                    result.add(node.as(Literal.class).getValue());
                } else {
                    result.add(node.as(Resource.class));
                }
            }
            return result.size() == 0 ? null : 
                (result.size() == 1 ? result.get(0) : result);
        } else {
            List<Object> result = new ArrayList<Object>();
            for (Statement stmt : subject.listProperties().toList()) {
                RDFNode object = stmt.getObject();
                Object value = object.isLiteral() 
                            ? object.as(Literal.class).getValue()
                            : object.as(Resource.class);
                result.add(Arrays.asList(new Object[] {
                        stmt.getPredicate(),
                        value
                        }));
            }
            return result;
        }
    }

    private Object getJava(Object value, State s, ListExpression params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getValue() {
        return "get";
    }

}
