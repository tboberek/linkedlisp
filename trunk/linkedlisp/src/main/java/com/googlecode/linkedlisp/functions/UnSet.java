package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.State;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class UnSet extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        Object value = params.getFirst().evaluate(s);
        if (value instanceof Resource) {
            return unsetSemantic((Resource)value, s, params);
        } else {
            return unsetJava(value, s, params);
        }
    }

    private Object unsetSemantic(Resource subject, State s, ListExpression params) throws Exception {
        InfModel model = s.getModel();
        Property p = ((Resource)params.get(1).evaluate(s)).as(Property.class);
        if (params.size() >= 3) {
            for (int i=2; i < params.size(); ++i) {
                Object o = params.get(i).evaluate(s);
                RDFNode obj = null;
                if (o instanceof RDFNode) {
                    obj = (RDFNode)o;
                }
                else {
                    obj = model.createTypedLiteral(o);
                }
                model.remove(subject, p, obj);
            }
        } else if (params.size() == 2) {
            subject.removeAll(p);
        } else if (params.size() == 1) {
            subject.removeProperties();
        }
        return subject;
    }

    private Object unsetJava(Object value, State s, ListExpression params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getValue() {
        return "unset";
    }

}
