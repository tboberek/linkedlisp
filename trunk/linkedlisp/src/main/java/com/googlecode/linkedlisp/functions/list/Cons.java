package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;

public class Cons extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        List<Object> result = new ArrayList<Object>();
        result.add(s.evaluate(params.get(0)));
        Object cdr = s.evaluate(params.get(0));
        if (cdr != null)
            result.addAll((Collection)cdr);
        return result;
    }

    @Override
    public Object getValue() {
        return "cons";
    }

}
