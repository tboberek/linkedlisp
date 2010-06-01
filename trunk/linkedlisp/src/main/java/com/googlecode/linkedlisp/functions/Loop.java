package com.googlecode.linkedlisp.functions;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Loop implements Function {

    private ListExpression params;

    public void setParameters(ListExpression list) {
        params = list;
    }

    public Object evaluate(State s) throws NoReturnException {
        
        String loopName = params.get(0).getValue(s).toString();
        
        // TODO This is just a copy of defun, and not loop yet.
        LispFunction fn = new LispFunction();
        String name = params.getFirst().getValue(s).toString();
        fn.setParameterNames((ListExpression)params.get(1));
        fn.setBody(params.get(2));
        s.getVariables().put(name, fn);
        throw new NoReturnException();
    }

    @Override
    public Object getValue(State s) {
        return "defun";
    }

}
