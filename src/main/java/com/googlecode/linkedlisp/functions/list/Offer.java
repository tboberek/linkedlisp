package com.googlecode.linkedlisp.functions.list;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;

public class Offer extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Queue q = (Queue) s.resolve(params.get(0));
        return q.offer(s.evaluate(params.get(1)));
    }

}
