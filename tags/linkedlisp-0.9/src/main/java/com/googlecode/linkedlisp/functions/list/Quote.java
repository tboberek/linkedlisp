package com.googlecode.linkedlisp.functions.list;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Quote extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object first = params.get(0);
        return first;
    }
    
    @Override
    public Object getValue() {
        return "QUOTE";
    }

}
