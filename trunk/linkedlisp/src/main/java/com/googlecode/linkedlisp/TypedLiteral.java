package com.googlecode.linkedlisp;

import java.util.Arrays;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.rdf.model.Resource;

public class TypedLiteral extends Literal {

    private com.hp.hpl.jena.rdf.model.Literal value;
    private ResourceExpression type;

    public TypedLiteral(com.hp.hpl.jena.rdf.model.Literal value) {
        super(value.getString());
        this.value = value;
    }

    public TypedLiteral(String value, ResourceExpression type) {
        super(value);
        this.type = type;
    }

    @Override
	public Object evaluate(State s) throws Exception {
        if (value == null) {
            Resource typeRes = (Resource) type.evaluate(s);
            String typeURI = typeRes.getURI();
            TypeMapper mapper = TypeMapper.getInstance();
            RDFDatatype datatype = mapper.getTypeByName(typeURI);
            value = s.getModel().createTypedLiteral((String)getValue(),datatype);
        }

        return value.getValue();
    }
    
    @Override
	public Object getValue() {
        return Arrays.asList(new Object[]{super.getValue(), type.getValue()});
    }
    
}