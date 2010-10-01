package com.googlecode.linkedlisp;

import java.util.Collections;
import java.util.List;

public abstract class Function {
    
    private Environment environment;
    
    private List parameterNames = Collections.emptyList();

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
    public Environment getEnvironment() {
        return environment;
    }
    
    public List getParameterNames() {
        return parameterNames;
    }
    
    public void setParameterNames(List names) {
        this.parameterNames = names;
    }
    
    @SuppressWarnings("unchecked")
    public abstract Object execute(Environment s, List params) throws Exception;


    @Override
	public String toString() {
        return getValue().toString();
    }
    
    
    public Object getValue() {
    	// default to function with same name as class
    	String ret = getClass().getSimpleName().toLowerCase();
    	return ret;
    }
}
