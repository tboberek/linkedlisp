package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Expression;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.ResourceExpression;
import com.googlecode.linkedlisp.State;

public class Lambda extends Function {

    @Override
	public Object execute(State s, ListExpression params) throws Exception {
        
        Expression x = params.get(0);
        if (x instanceof ResourceExpression) {
            Object res = x.evaluate(s);
            if (res instanceof Class) {
                Class functionClass = (Class)res;
                Function function = (Function) functionClass.newInstance();
                return function;
            }
        } else if (x instanceof ListExpression){
            LispFunction fn = new LispFunction();
            fn.setParameterNames((List<String>)x.getValue());
            fn.setBody(params.get(1));
            return fn;
        } else {
            System.err.println("I don't know what to do with this:\t"+x);
        }
        return null;
    }

    @Override
    public Object getValue() {
        return "lambda";
    }

}
