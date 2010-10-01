package com.googlecode.linkedlisp.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

@SuppressWarnings("unchecked")
public class Get extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.resolve(params.get(0));
        if (value instanceof Resource) {
            return getSemantic((Resource)value, s, params);
        } else if(value instanceof ListExpression) {
        	return getJava(value, s, params);
        } else if (value instanceof List) {
            int index = s.resolveAsInteger(params.get(1)).intValue();
            return ((List)value).get(index);
        } else if (value instanceof Map) {
            Object key = s.resolve(params.get(1));
            return ((Map)value).get(key);
        } else {
            return getJava(value, s, params);
        }
    }

    private Object getSemantic(Resource subject, Environment s, List params) throws Exception {
        
        if (params.size() > 1) {
            Resource predicate = s.resolveAsResource(params.get(1));
            List<Statement> foo = subject.listProperties(predicate.as(Property.class)).toList();
            List<Object> result = new ArrayList<Object>(foo.size());
            for (Statement stmt : foo) {
                RDFNode node = stmt.getObject();
                if (node.isLiteral()) {
                    result.add(node.as(Literal.class).getValue());
                } else {
                    result.add(node.as(Resource.class));
                }
            }
            return result.size() == 0 ? null :
                (result.size() == 1 ? result.get(0) : result);
        } else {
            List<Object> result = new ArrayList<Object>();
            for (Statement stmt : subject.listProperties().toList()) {
                RDFNode object = stmt.getObject();
                Object value = object.isLiteral()
                            ? object.as(Literal.class).getValue()
                            : object.as(Resource.class);
                result.add(Arrays.asList(new Object[] {
                        stmt.getPredicate(),
                        value
                        }));
            }
            return result;
        }
    }

    private Object getJava(Object bean, Environment s, List params) throws Exception {
        String name = s.evaluate(params.get(1)).toString();
    	return BeanUtils.getProperty(bean, name);
    }

}
