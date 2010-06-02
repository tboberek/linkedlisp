package com.googlecode.linkedlisp;

import java.util.Collections;
import java.util.List;

public abstract class Function implements Expression {
    private List<String> parameterNames = Collections.EMPTY_LIST;

    public List<String> getParameterNames() {
        return parameterNames;
    }
    
    public void setParameterNames(List<String> names) {
        this.parameterNames = names;
    }
    
    public Object getVariable(State s, int position) throws Exception {
        return s.getVariable(getParameterNames().get(position));
    }
    
    public String toString() {
        return getValue().toString();
    }
}
