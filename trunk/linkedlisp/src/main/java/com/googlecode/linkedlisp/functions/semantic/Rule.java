package com.googlecode.linkedlisp.functions.semantic;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Symbol;
import com.googlecode.linkedlisp.Environment;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.reasoner.rulesys.Node_RuleVariable;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Functor;

public class Rule extends Function {

    public Object execute(Environment s, List params) throws Exception {
        
        String name = params.get(0).toString();
        List<ClauseEntry> a = buildClauses((List)params.get(1), s);
        String direction = params.get(2).toString();
        List<ClauseEntry> b = buildClauses((List)params.get(3), s);
        com.hp.hpl.jena.reasoner.rulesys.Rule rule = null;
        
        if ("->".equals(direction)) {
            rule = new com.hp.hpl.jena.reasoner.rulesys.Rule(name, b, a);
            rule.setBackward(false);
        } else if ("<-".equals(direction)) {
            rule = new com.hp.hpl.jena.reasoner.rulesys.Rule(name, a, b);
            rule.setBackward(true);
        }
        //System.out.println("Created rule: "+rule.toString());
        if (rule != null) {
            Environment env = getEnvironment();
            if (env != null) {
                env.registerRule(rule);
            } else {
                s.registerRule(rule);
            }
        }
        
        return rule;
    }

    private List<ClauseEntry> buildClauses(List value, Environment s) throws Exception {
        List<ClauseEntry> result = new ArrayList<ClauseEntry>();
        int index = 0;
        for (Object exp : value) {
            List clause = (List)exp;
            if (clause.size() == 3) {
                
                TriplePattern entry = new TriplePattern(toNode(clause.get(0), s, index++),
                                                        toNode(clause.get(1), s, index++),
                                                        toNode(clause.get(2), s, index++));
                result.add(entry);
            } else if (clause.get(0).toString().equals("rule")){
                result.add((com.hp.hpl.jena.reasoner.rulesys.Rule)s.evaluate(clause));
            } else if (clause.size() == 2) {
                String functionName = clause.get(0).toString();
                List<Node> arguments = new ArrayList<Node>();
                List args = s.resolveAsList(clause.get(1));
                for (Object o : args) {
                    arguments.add(new Node_RuleVariable(o.toString(), index++));
                }
                Functor f = new Functor(functionName,arguments);
                result.add(f);
            }
        }
        return result;
    }

    private Node toNode(Object exp, Environment s, int index) throws Exception {
        if (exp instanceof RDFNode) {
            return ((RDFNode)exp).asNode();
        } else if (exp instanceof java.net.URI){
            return s.resolveAsResource(exp).asNode();
        } else if (exp instanceof Symbol) {
            return new Node_RuleVariable(exp.toString(), index);
        } else {
            return s.getModel().createTypedLiteral(s.evaluate(exp)).asNode();
        }
    }

    @Override
    public Object getValue() {
        return "rule";
    }

}
