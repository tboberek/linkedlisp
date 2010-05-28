package com.googlecode.linkedlisp;

import com.hp.hpl.jena.rdf.model.Resource;

public class ResourceExpression implements Expression {

    private Resource resource;
    private String uri;
    
    public ResourceExpression(Resource res) {
        this.resource = res;
    }

    public ResourceExpression(String uri) {
        resource = null;
        this.uri = uri;
    }
    
    public Object evaluate(State s) {
        if (resource == null) {
            String realURI = s.processPrefix(uri);
            resource = s.getModel().createResource(realURI);
        }
        return s.resolveResource(resource);
    }

    public Object getValue(State s) {
        return evaluate(s);
    }

    
}
