package com.googlecode.linkedlisp.functions.semantic;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.Builtin;
import com.hp.hpl.jena.reasoner.rulesys.Node_RuleVariable;
import com.hp.hpl.jena.reasoner.rulesys.RuleContext;

public class FunctionBuiltin implements Builtin {

    private Function function;
    private String name;
    private Environment parentState;
    
    public FunctionBuiltin(Function function, String name, Environment parentState) {
        this.parentState = parentState;
        this.function = function;
        this.name = name;
    }

    @Override
    public boolean bodyCall(Node[] args, int length, RuleContext context) {
        //System.out.println("Calling "+name+ args);
        Node output = args[0];
        ListExpression params = new ListExpression();
        params.add(function);
        for (int i=1;i< args.length;++i) {
            Node n = args[i];
            if (n == null) continue;
            if (n instanceof Node_RuleVariable) {
                //System.out.println("Derefing "+((Node_Variable)n).getName());
                n = ((Node_RuleVariable)n).deref();
            }
            //System.out.println(n.toString());
            RDFNode node = parentState.getModel().getRDFNode(n);
            if (node.isResource()) {
                params.append(node.as(Resource.class));
            } else if (node.isLiteral()) {
                params.append(node.as(Literal.class).getValue());
            }
        }
        boolean returnVal = false;
        try {
            Object result = parentState.evaluate(params);
            List<Object> results = new LinkedList<Object>();
            if (result instanceof Iterable) {
                for (Object o : (Iterable)result) {
                    results.add(o);
                }
            } else results.add(result);
            for (Object value : results) {
                Node n = null;
                if (value instanceof RDFNode) {
                    n = ((RDFNode)value).asNode();
                } else {
                    n = parentState.getModel().createTypedLiteral(value).asNode();
                }
                if (n != null) {
                    context.getEnv().bind(output, n);
                    returnVal = true;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            returnVal = false;
        }
        return returnVal;
    }

    @Override
    public int getArgLength() {
        return function.getParameterNames().size()+1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public void headAction(Node[] args, int length, RuleContext context) {
        bodyCall(args,length,context);
    }

    @Override
    public boolean isMonotonic() {
        return true;
    }

    @Override
    public boolean isSafe() {
        return true;
    }

    public Function getFunction() {
        return function;
    }

}
