package com.googlecode.linkedlisp.functions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class Prefix extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        String prefixName = (String) params.getFirst().getValue();
        Resource prefixResource = (Resource) params.get(1).evaluate(s);
        s.getPrefixes().put(prefixName, prefixResource.getURI());
        return null;
    }

    @Override
    public Object getValue() {
        return "prefix";
    }

}
