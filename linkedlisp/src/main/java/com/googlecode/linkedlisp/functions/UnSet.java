package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

@SuppressWarnings("unchecked")
public class UnSet extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        if (value instanceof Resource) {
            return unsetSemantic((Resource)value, s, params);
        } else {
            return unsetJava(value, s, params);
        }
    }

    private Object unsetSemantic(Resource subject, Environment s, List params) throws Exception {
        InfModel model = s.getModel();
        Property p = s.resolveAsResource(s.evaluate(params.get(1))).as(Property.class);
        if (params.size() >= 3) {
            for (int i=2; i < params.size(); ++i) {
                Object o = s.evaluate(params.get(i));
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

    private Object unsetJava(Object value, Environment s, List params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getValue() {
        return "unset";
    }

}
