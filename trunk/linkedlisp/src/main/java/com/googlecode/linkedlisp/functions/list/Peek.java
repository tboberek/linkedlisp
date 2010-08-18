package com.googlecode.linkedlisp.functions.list;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;

public class Peek extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        Object o = s.evaluate(params.get(0));
        if (o instanceof Stack) return ((Stack)o).peek();
        else if (o instanceof Queue) return ((Queue)o).peek();
        else return null;
    }

}
