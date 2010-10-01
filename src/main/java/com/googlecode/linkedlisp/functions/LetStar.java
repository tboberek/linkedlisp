package com.googlecode.linkedlisp.functions;

import java.util.List;
import java.util.LinkedList;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;

public class LetStar extends Function {

    @SuppressWarnings("unchecked")
    public Object execute(Environment s, List params) throws Exception
   	{
		// Create a new block
		Environment block = s.copyForScope(new LinkedList(), s);

		// Get the symbol block
		List symbolList = (List) params.get(0);

		// Define the symbols
		for (Object pair : symbolList)
		{
			List pairList = (List) pair;

			// Get the symbol
			String symbol = pairList.get(0).toString();
			Object value = pairList.get(1);

			// Attempt to define the symbol in the new 
			// environment, using the variables of the
			// new environment
			block.setVariable(symbol, block.evaluate(value));
		}

		// Get the execution block
		Object execution = params.get(1);

		// Execute the block
		return block.evaluate(execution);
    }

    @Override
    public Object getValue() {
        return "let*";
    }

}
