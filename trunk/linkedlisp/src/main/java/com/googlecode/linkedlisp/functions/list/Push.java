package com.googlecode.linkedlisp.functions.list;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;

public class Push extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Stack stack = (Stack) s.resolve(params.get(0));
        return stack.push(s.evaluate(params.get(1)));
    }

}
