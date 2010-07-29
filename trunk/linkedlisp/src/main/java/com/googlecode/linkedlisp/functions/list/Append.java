package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;

public class Append extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Collection<Object> result = (Collection<Object>) s.evaluate(params.get(0));
        result.add(s.evaluate(params.get(1)));
        return result;
    }

    @Override
    public Object getValue() {
        return "append";
    }

}
