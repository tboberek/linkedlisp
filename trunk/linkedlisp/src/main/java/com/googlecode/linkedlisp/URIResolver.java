package com.googlecode.linkedlisp;

import com.hp.hpl.jena.rdf.model.Resource;

public interface URIResolver {

    public String getURIPrefix();
    
    public Object resolve(Resource resource);

}
