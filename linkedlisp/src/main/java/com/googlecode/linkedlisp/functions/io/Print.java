package com.googlecode.linkedlisp.functions.io;

import java.util.Arrays;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;

public class Print extends Function {
    public Print() {
        setParameterNames(Arrays.asList(new String[]{"x"}));
    }

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object v = s.evaluate(params.get(0));
        System.out.print(v);
        System.out.flush();
        return v;
    }

    @Override
    public Object getValue() {
        return "print";
    }

}
