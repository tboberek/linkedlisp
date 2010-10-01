package com.googlecode.linkedlisp.functions.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.Environment;

public class CreateList extends Function {
    @Override
	public Object execute(Environment s, List params) throws Exception {
        LinkedList result = new LinkedList();
        if (params.size() > 1)
            result.add(s.evaluate(params.get(0)));
        return result;
    }

}
