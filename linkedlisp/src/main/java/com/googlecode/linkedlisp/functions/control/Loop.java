package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class Loop extends Function {

    @Override
	public Object execute(Environment s, List params) throws Exception {
		Object lastResult = null;

		// If the first result is not null, continue on
		// and evaluate all the other parameters
		while (null != s.evaluate(params.get(0)))
		{
			for (int i = 1; i < params.size(); i++)
			{
				lastResult = s.evaluate(params.get(i));
			}
        }

		return lastResult;
    }

    @Override
    public Object getValue() {
        return "loop";
    }

}
