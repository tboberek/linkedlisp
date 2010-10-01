package com.googlecode.linkedlisp.functions.semantic;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;
import java.util.List;

public class MakeTDBModel extends Function {
    
	@Override
	public Object execute(Environment s, List params) throws Exception {
	    String source = s.evaluate(params.get(0)).toString();
        Model result = TDBFactory.createModel(source);
        result.setNsPrefix("rdf", RDF.getURI());
        result.setNsPrefix("rdfs", RDFS.getURI());
        result.setNsPrefix("owl", OWL.getURI());
        result.setNsPrefix("xsd", XSD.getURI());
        return result;
	}

	@Override
	public Object getValue() {
		return "model.tdb";
	}
}

