package com.googlecode.linkedlisp.functions.list;

import java.util.Collection;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;

public class Length extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Collection<Object> coll = (Collection<Object>) s.evaluate(params.get(0));
        return coll.size();
    }

}
