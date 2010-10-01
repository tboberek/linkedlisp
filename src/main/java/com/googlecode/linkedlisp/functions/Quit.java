package com.googlecode.linkedlisp.functions;

import java.util.List;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;

public class Quit extends Function {

    @SuppressWarnings("unchecked")
    @Override
	public Object execute(Environment s, List params) throws Exception {
        if (params.size() > 0) {
            Long exitVal = s.resolveAsInteger(s.evaluate(params.get(0)));
            System.exit(exitVal.intValue());
        } else System.exit(0);
        return null;
    }

}
