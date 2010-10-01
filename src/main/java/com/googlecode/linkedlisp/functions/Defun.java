package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Defun extends Function {

    private final Lambda lambda = new Lambda();
    
    @Override
	public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        lambda.setEnvironment(environment);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object execute(Environment s, List params) throws Exception {
        String name = params.get(0).toString();
        Object function = lambda.execute(s, s.getRest(params));
        s.setVariable(name, function);
        return null;
    }
}
