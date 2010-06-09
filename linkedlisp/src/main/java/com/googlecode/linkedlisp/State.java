package com.googlecode.linkedlisp;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.iri.impl.Parser;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class State {

    private Model baseModel = ModelFactory.createDefaultModel();
    private State rootState = this;
    private State parentState = null;
    private Map<String, String> prefixes = new HashMap<String,String>();
    private Map<String, Object> variables = new HashMap<String,Object>();
    private InfModel model;

    public State(InfModel m) {
        this.model = m;
    }
    
    public State() {
        buildDefaultRules();
    }
    
    private void buildDefaultRules() {
        String ruleSrc = "@include <OWLMicro>.";
        StringReader sr = new StringReader(ruleSrc);
        List<Rule> r = Rule.rulesParserFromReader(new BufferedReader(sr)).getRulesPreload();
        GenericRuleReasoner reasoner = new GenericRuleReasoner(r);
        reasoner.setOWLTranslation(true);
        reasoner.setTransitiveClosureCaching(true);
        model = ModelFactory.createInfModel(reasoner, baseModel);
    }
    
    public InfModel getModel() {
        return model;
    }
    
    public Map<String,Object> getGlobalVariables() {
        return rootState.getVariables();
    }
    
    public Map<String,Object> getVariables() {
        return variables;
    }
    
    public Map<String,String> getPrefixes() {
        return prefixes;
    }

    public State getRootState() {
        return rootState;
    }
    
    public State getParentState() {
        return parentState;
    }
    
    public String processPrefix(String s) {
        String[] parsed = s.split(":",2);
        if (parsed.length > 1 )
            if (getPrefixes().containsKey(parsed[0]))
                return getPrefixes().get(parsed[0])+parsed[1];
            else if (parentState != null 
                && rootState.getPrefixes().containsKey(parsed[0]))
                return rootState.getPrefixes().get(parsed[0])+parsed[1];
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
    
    public State copyForCall(ListExpression params, List<String> paramNames) {
        State copy =  new State();
        copy.parentState = this;
        copy.rootState = this.rootState;
        copy.prefixes.putAll(this.prefixes);
        copy.model = this.model;

        if (params != null && paramNames != null) {
            for (int i=0; i< params.size() && i < paramNames.size(); ++i) {
                Expression parameter = params.get(i);
                copy.variables.put(paramNames.get(i), parameter);
            }
        }
        
        return copy;
    }

    public void registerRule(Rule rule) {
        GenericRuleReasoner reasoner = (GenericRuleReasoner)model.getReasoner();
        reasoner.getRules().add(rule);
    }

}
