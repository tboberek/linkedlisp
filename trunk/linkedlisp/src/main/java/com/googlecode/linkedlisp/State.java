package com.googlecode.linkedlisp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class State {

    private OntModel model = ModelFactory.createOntologyModel();
    private Map<String, String> prefixes = new HashMap<String,String>();
    private Map<String, Object> variables = new HashMap<String,Object>();
    private Map<String, URIResolver> uriResolvers = new HashMap<String,URIResolver>();
    private Map<String, Object> globals = new HashMap<String,Object>();

    public State(OntModel m) {
        this.model = m;
    }
    
    public State() {
    }
    
    public OntModel getModel() {
        return model;
    }
    
    public Map<String,Object> getGlobalVariables() {
        return globals;
    }
    
    public Map<String,Object> getVariables() {
        return variables;
    }
    
    public Map<String,String> getPrefixes() {
        return prefixes;
    }

    public String processPrefix(String s) {
        String[] parsed = s.split(":",2);
        if (parsed.length > 1 && getPrefixes().containsKey(parsed[0])) {
            return getPrefixes().get(parsed[0])+parsed[1];
        } else return s;
    }
    
    public State copyForCall() {
        State copy =  new State();
        copy.globals = this.globals;
        copy.prefixes.putAll(this.prefixes);
        copy.model = this.model;
        copy.uriResolvers = this.uriResolvers;
        
        return copy;
    }

    public Object resolveResource(Resource resource) {
        String[] schemeSplit = resource.getURI().split("://",2);
        if (schemeSplit.length > 1 && getURIResolvers().containsKey(schemeSplit[0])) {
            return getURIResolvers().get(schemeSplit[0]).resolve(resource);
        } else return resource;
    }
    
    private Map<String, URIResolver> getURIResolvers() {
        return uriResolvers;
    }
    
}
