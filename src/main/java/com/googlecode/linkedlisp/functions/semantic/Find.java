package com.googlecode.linkedlisp.functions.semantic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Find extends Function {

    @Override
    public Object execute(Environment state, List params) throws Exception {
        Resource s = null;
        if (params.size() > 0 && params.get(0) != null)
            s = state.resolveAsResource(state.evaluate(params.get(0)));
        Property p = null;
        if (params.size() > 1 && params.get(1) != null)
            p = state.resolveAsResource(state.evaluate(params.get(1))).as(Property.class);
        RDFNode o = null;
        if (params.size() > 2 && params.get(2) != null)
            o = toNode(toResource(state.evaluate(params.get(2))), state);
        List result = new ArrayList();
        List<Statement> stmtList = state.getModel().listStatements(s,p,o).toList();
        for (Statement stmt : stmtList) {
            List<Object> statement = Arrays.asList(new Object[]{
                    stmt.getSubject(),stmt.getPredicate(),
                    stmt.getObject().isLiteral() ?
                            stmt.getLiteral().getValue() :
                                stmt.getResource()});
            result.add(statement);
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

    private RDFNode toNode(Object o, Environment s) throws Exception {
        if (o == null) return null;
        else if (o instanceof RDFNode) {
            return (RDFNode)o;
        } else {
            return s.getModel().createTypedLiteral(o);
        }
    }
}
