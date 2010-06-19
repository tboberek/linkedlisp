package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.IDExpression;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.reasoner.rulesys.Node_RuleVariable;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.reasoner.TriplePattern;
import com.hp.hpl.jena.reasoner.rulesys.ClauseEntry;
import com.hp.hpl.jena.reasoner.rulesys.Functor;

public class Rule extends Function {

    public Object execute(State s, ListExpression params) throws Exception {
        
        String name = (String)params.get(0).getValue();
        List<ClauseEntry> a = buildClauses((ListExpression)params.get(1), s);
        String direction = (String)params.get(2).getValue();
        List<ClauseEntry> b = buildClauses((ListExpression)params.get(3), s);
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
            s.registerRule(rule);
        }
        
        return rule;
    }

    private List<ClauseEntry> buildClauses(ListExpression value, State s) throws Exception {
        List<ClauseEntry> result = new ArrayList<ClauseEntry>();
        int index = 0;
        for (Expression exp : value) {
            ListExpression clause = (ListExpression)exp;
            if (clause.size() == 3) {
                
                TriplePattern entry = new TriplePattern(toNode(clause.get(0), s, index++),
                                                        toNode(clause.get(1), s, index++),
                                                        toNode(clause.get(2), s, index++));
                result.add(entry);
            } else if (clause.get(0).getValue().equals("rule")){
                result.add((com.hp.hpl.jena.reasoner.rulesys.Rule)clause.evaluate(s));
            } else if (clause.size() == 2) {
                String functionName = (String)clause.get(0).getValue();
                List<Node> arguments = new ArrayList<Node>();
                List args = (List)clause.get(1).getValue();
                for (Object o : args) {
                    arguments.add(new Node_RuleVariable(o.toString(), index++));
                }
                Functor f = new Functor(functionName,arguments);
                result.add(f);
            }
        }
        return result;
    }

    private Node toNode(Expression exp, State s, int index) throws Exception {
        if (exp instanceof ResourceExpression) {
            return ((RDFNode)exp.evaluate(s)).asNode();
        } else if (exp instanceof IDExpression) {
            return new Node_RuleVariable(exp.toString(), index++);
        } else {
            return s.getModel().createTypedLiteral(exp.evaluate(s)).asNode();
        }
    }

    @Override
    public Object getValue() {
        return "rule";
    }

}
