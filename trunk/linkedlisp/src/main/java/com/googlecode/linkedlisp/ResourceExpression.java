package com.googlecode.linkedlisp;

import com.hp.hpl.jena.rdf.model.ModelFactory;
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
        return resource;
    }

    public Object getValue() {
        if (resource == null) {
            return ModelFactory.createDefaultModel().createResource(uri);
        } else return resource;
    }
    @Override
	public String toString() {
        return getValue().toString();
    }
    
}
