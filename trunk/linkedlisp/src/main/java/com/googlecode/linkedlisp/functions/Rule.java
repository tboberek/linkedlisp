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
        System.out.println("Created rule: "+rule.toString());
        if (rule != null) {
            s.registerRule(rule);
        }
        
        return rule;
    }

    private List<ClauseEntry> buildClauses(ListExpression value, State s) throws Exception {
        List<ClauseEntry> result = new ArrayList<ClauseEntry>();
        for (Expression exp : value) {
            ListExpression clause = (ListExpression)exp;
            if (clause.size() == 3) {
                
                TriplePattern entry = new TriplePattern(toNode(clause.get(0), s),
                                                    toNode(clause.get(1), s),
                                                    toNode(clause.get(2), s));
                result.add(entry);
            } else if (clause.get(0).getValue().equals("rule")){
                result.add((com.hp.hpl.jena.reasoner.rulesys.Rule)clause.evaluate(s));
            } else if (clause.size() == 2) {
                String functionName = (String)clause.get(0).getValue();
                List<Node> arguments = new ArrayList<Node>();
                List args = (List)clause.get(1).getValue();
                for (Object o : args) {
                    arguments.add(Node.createVariable(o.toString()));
                }
                Functor f = new Functor(functionName,arguments);
                result.add(f);
            }
        }
        return result;
    }

    private Node toNode(Expression exp, State s) throws Exception {
        if (exp instanceof ResourceExpression) {
            return ((RDFNode)exp.getValue()).asNode();
        } else if (exp instanceof IDExpression) {
            return Node.createVariable(exp.toString());
        } else {
            return s.getModel().createTypedLiteral(exp.evaluate(s)).asNode();
        }
    }

    @Override
    public Object getValue() {
        return "rule";
    }

}
