package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class LispFunction implements Function {

    private Expression body;
    private ListExpression parameterNames;
    private ListExpression parameters;

    public void setParameterNames(ListExpression expression) {
        parameterNames = expression;
    }

    @Override
    public void setParameters(ListExpression list) {
        parameters = list;
    }

    @Override
    public Object evaluate(State s) throws Exception {
        State newState = s.copyForCall();
        for (int i=0; i < parameterNames.size() && i < parameters.size(); ++i) {
            newState.getVariables().put(parameterNames.get(i).getValue(s).toString(),
                                        parameters.get(i));
        }
        return body.evaluate(newState);
    }

    @Override
    public Object getValue(State s) {
        return null;
    }

    public void setBody(Expression expression) {
        body = expression;
    }

}
