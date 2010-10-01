package com.googlecode.linkedlisp.functions.list;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.util.Sequence;
import java.util.List;

public class Seq extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
        Number start = 0;
        Number increment = 1;
        Number end = 0;
        if (params.size() == 1) {
            end = (Number) s.evaluate(params.get(0));
        } else if (params.size() == 2) {
            start = (Number) s.evaluate(params.get(0));
            end = (Number) s.evaluate(params.get(1));
        } else if (params.size() >= 3) {
            start = (Number) s.evaluate(params.get(0));
            end = (Number) s.evaluate(params.get(1));
            increment = (Number) s.evaluate(params.get(2));
        }
        return new Sequence(start, end, increment);
    }

}
