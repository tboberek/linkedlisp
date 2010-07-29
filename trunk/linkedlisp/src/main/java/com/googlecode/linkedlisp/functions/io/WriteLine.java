package com.googlecode.linkedlisp.functions.io;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class WriteLine extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        System.out.println(value);
        return null;
    }

    @Override
    public Object getValue() {
        return "write-line";
    }

}
