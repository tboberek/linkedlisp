package com.googlecode.linkedlisp.functions;

import java.util.Arrays;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.NoReturnException;
import com.googlecode.linkedlisp.State;

public class Lambda extends Function {

    public Lambda() {
        setParameterNames(Arrays.asList(new String[]{}));
    }
    public Object evaluate(State s) throws NoReturnException {
        // TODO This is just a copy of defun, and not Lambda yet.
        throw new NoReturnException();
    }

    @Override
    public Object getValue() {
        return "lambda";
    }

}
