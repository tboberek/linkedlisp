package com.googlecode.linkedlisp;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.hp.hpl.jena.iri.impl.Parser;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.OWLMicroReasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.vocabulary.*;

public class State {

    public static final Map<String,String> DEFAULT_PREFIXES = new HashMap<String,String>();
    static {
        DEFAULT_PREFIXES.put("rdf", RDF.getURI());
        DEFAULT_PREFIXES.put("rdfs", RDFS.getURI());
        DEFAULT_PREFIXES.put("owl", OWL.getURI());
        DEFAULT_PREFIXES.put("xsd", XSD.getURI());
    }

    private Model baseModel = ModelFactory.createDefaultModel();
    private State rootState = this;
    private State parentState = null;
    private Map<String, Object> variables = new HashMap<String,Object>();
    private InfModel model;

    public State(Model m) {
        this.baseModel = m;
    }
    
    public State() {
        buildDefaultRules();
    }
    
    private void buildDefaultRules() {
        List<Rule> r = new ArrayList<Rule>(OWLMicroReasoner.loadRules());
        GenericRuleReasoner reasoner = new GenericRuleReasoner(r);
        reasoner.setOWLTranslation(true);
        reasoner.setTransitiveClosureCaching(true);
        model = ModelFactory.createInfModel(reasoner, baseModel);
        setPrefix("rdf", RDF.getURI());
        setPrefix("rdfs", RDFS.getURI());
        setPrefix("owl", OWL.getURI());
        setPrefix("xsd", XSD.getURI());
        System.out.println(getPrefixes());
    }
    
    public InfModel getModel() {
        if (parentState == null)
            return model;
        else return rootState.getModel();
    }
    
    public Map<String,Object> getGlobalVariables() {
        return rootState.getVariables();
    }
    
    public Map<String,Object> getVariables() {
        return variables;
    }
    
    public Map<String,String> getPrefixes() {
        return getModel().getNsPrefixMap();
    }

    public void setPrefix(String prefix, String uri) {
        try {
            getModel().setNsPrefix(prefix, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public State getRootState() {
        return rootState;
    }
    
    public State getParentState() {
        return parentState;
    }
    
    public String processPrefix(String s) {
        String[] parsed = s.split(":",2);
        if (parsed.length > 1 ) {
            if (getPrefixes().containsKey(parsed[0]))
                return getPrefixes().get(parsed[0])+parsed[1];
            else if (parentState != null 
                && rootState.getPrefixes().containsKey(parsed[0]))
                return rootState.getPrefixes().get(parsed[0])+parsed[1];
        }
        return s;
    }
    
    public Object getVariable(String name) throws Exception {
        Object value = null;
        if (getVariables().containsKey(name)) {
            value = getVariables().get(name);
            if (value instanceof IDExpression && parentState != null) {
                String nameInParent = ((Expression)value).getValue().toString();
                value = parentState.getVariable(nameInParent);            
            } else if (value instanceof Expression) {
                State forEval = this;
                if (this.parentState != null) forEval = this.parentState;
                value = ((Expression)value).evaluate(forEval);
                getVariables().put(name, value);
            }
        } else if (parentState != null){
            value = rootState.getVariable(name);
        } 

        return value;
    }

	public Object setVariable(String name, Object value) throws Exception {
		getVariables().put (name, value);
		return value;
	}
    
    public State copyForCall(ListExpression params, List<String> paramNames) {
        State copy =  new State(baseModel);
        copy.parentState = this;
        copy.rootState = this.rootState;

        if (params != null && paramNames != null) {
            for (int i=0; i< params.size() && i < paramNames.size(); ++i) {
                Expression parameter = params.get(i);
                copy.variables.put(paramNames.get(i), parameter);
            }
        }
        
        return copy;
    }

    public void registerRule(Rule rule) {
        if (parentState != null) rootState.registerRule(rule);
        else {
            GenericRuleReasoner reasoner = (GenericRuleReasoner)model.getReasoner();
            List<Rule> rules = new ArrayList<Rule>(reasoner.getRules());
            rules.add(rule);
            System.out.println("Added Rule:\t"+rule.toString());
            reasoner.setRules(rules);
            model = ModelFactory.createInfModel(reasoner, baseModel);
        }
    }

}
