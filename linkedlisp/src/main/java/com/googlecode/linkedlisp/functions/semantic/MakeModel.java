package com.googlecode.linkedlisp.functions.semantic;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class MakeModel extends Function {
    
	@Override
	public Object execute(State s, ListExpression params) throws Exception {
	    Model result = null;
	    if (params.size() == 0)
	        result = ModelFactory.createDefaultModel();
	    else {
	        String source = params.get(0).evaluate(s).toString();
	        result = FileManager.get().loadModel(source);
	    }
        result.setNsPrefix("rdf", RDF.getURI());
        result.setNsPrefix("rdfs", RDFS.getURI());
        result.setNsPrefix("owl", OWL.getURI());
        result.setNsPrefix("xsd", XSD.getURI());
        return result;
	}

	@Override
	public Object getValue() {
		return "model.mem";
	}
}

