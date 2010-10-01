package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

@SuppressWarnings("unchecked")
public class LispFunction extends Function {

    private List body;

    @Override
    public Object execute(Environment s, List params) throws Exception {
        Environment newState = s.copyForCall(params, getParameterNames(), getEnvironment());
        return newState.evaluate(body);
    }

    @Override
    public Object getValue() {
        return body;
    }

    public void setBody(List expression) {
        body = expression;
    }

}
