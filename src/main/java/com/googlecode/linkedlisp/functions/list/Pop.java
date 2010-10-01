package com.googlecode.linkedlisp.functions.list;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;

public class Pop extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Stack q = (Stack) s.evaluate(params.get(0));
        return q.pop();
    }

}
