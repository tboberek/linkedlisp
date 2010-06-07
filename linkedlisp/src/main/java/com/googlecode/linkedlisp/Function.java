package com.googlecode.linkedlisp;

import java.util.Collections;
import java.util.List;

public abstract class Function {
    private List<String> parameterNames = Collections.emptyList();

    public List<String> getParameterNames() {
        return parameterNames;
    }
    
    public void setParameterNames(List<String> names) {
        this.parameterNames = names;
    }
    
    public abstract Object execute(State s, ListExpression params) throws Exception;


    public Object getVariable(State s, int position) throws Exception {
        return s.getVariable(getParameterNames().get(position));
    }
    
    @Override
	public String toString() {
        return getValue().toString();
    }
    
    public abstract Object getValue();
}
