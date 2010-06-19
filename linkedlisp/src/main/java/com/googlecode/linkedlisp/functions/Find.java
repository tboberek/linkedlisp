package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Find extends Function {

    @Override
    public Object execute(State state, ListExpression params) throws Exception {
        Resource s = null;
        if (params.size() > 0 && params.get(0) != null)
            s = toResource(params.get(0).evaluate(state));
        Property p = null;
        if (params.size() > 1 && params.get(1) != null)
            p = toProperty(params.get(1).evaluate(state));
        RDFNode o = null;
        if (params.size() > 2 && params.get(2) != null)
            o = toNode(params.get(2).evaluate(state), state);
        List result = new ArrayList();
        for (Statement stmt : state.getModel().listStatements(s,p,o).toList()) {
            result.add(Arrays.asList(new Object[]{
                        stmt.getSubject(),stmt.getPredicate(),
                        stmt.getObject().isLiteral() ?
                        stmt.getLiteral() :
                        stmt.getResource()}));
        }
        return result;
    }

    private Resource toResource(Object o) {
        if (o == null) return null;
        return ((RDFNode)o).as(Resource.class);
    }

    private Property toProperty(Object o) {
        if (o == null) return null;
        return ((RDFNode)o).as(Property.class);
    }

    private RDFNode toNode(Object o, State s) throws Exception {
        if (o == null) return null;
        else if (o instanceof RDFNode) {
            return (RDFNode)o;
        } else {
            return s.getModel().createTypedLiteral(o);
        }
    }

    @Override
    public Object getValue() {
        return "find";
    }

}
