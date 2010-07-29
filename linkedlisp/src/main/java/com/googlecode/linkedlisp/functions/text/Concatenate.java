package com.googlecode.linkedlisp.functions.text;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Concatenate extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        StringBuilder result = new StringBuilder();
        for (Object exp : params)  {
            Object o = s.evaluate(exp);
            if (o != null) result.append(o.toString());
        }
        return result;
    }
    
    @Override
    public Object getValue() {
        return "concatenate";
    }

}
