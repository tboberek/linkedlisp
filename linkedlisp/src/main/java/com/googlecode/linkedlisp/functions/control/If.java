package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class If extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object value = s.evaluate(params.get(0));
        if (value != null) {
            return s.evaluate(params.get(1));
        } else if (params.size() > 2) {
            return s.evaluate(params.get(2));
        } else return null;
    }

    @Override
    public Object getValue() {
        return "if";
    }

}
