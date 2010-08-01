package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import java.util.List;
import java.util.LinkedList;

public class Loop extends Function {

	private String blockName = null;

    @Override
	public Object execute(Environment s, List params) throws Exception 
   	{
		// Get the first object in our param list
		Object firstParam = params.get(0);
		String firstString = firstParam.toString();

		// If we have a list, then we're the short-form
		// of loop (evaluate forever).  Otherwise,
		// we're a specialized version of loop
		if (firstParam instanceof List)
		{
			return ShortForm(s, params);
		}
		else if (firstString.equals("named"))
		{
			// If we find a block after we already have one,
			// then throw an exception
			if (blockName != null)
			{
				throw new RuntimeException("named clause already found");
			}

			// Get the name for the block
			blockName = s.evaluate(params.get(1)).toString();

			// Get the remaining expressions after we clear
			// out our named clause
			List remaining = params.subList (2, params.size());

			// Execute with the remaining 
			return execute (s, remaining);
			}
		else if (firstString.equals("for"))
		{
			// The next part of our clause is the control
			// variable
			Object ctrlVar = params.get(1);

			// After the control variable, we need to grab
			// the control subclause
			Object subclause = params.get(2);
			
			if (subclause.toString().equals("from"))
			{
				return ForFrom(ctrlVar, params.subList(3, params.size()), s);
			}
			else if (subclause.toString().equals("in"))
			{
				return ForIn(ctrlVar, params.subList(3, params.size()), s);
			}

			return null;
		}
		else if (firstString.equals("do"))
		{
			// Short form with a do - make sure we
			// don't have a name, and that our next 
			// param is a list
			if (blockName != null)
			{
				String error = "found expression where LOOP keyword " +
					"expected.";

				throw new RuntimeException (error);
			}	
			if (!(params.get(1) instanceof List))
			{
				String paramString = params.get(1).toString();
				String errorString = "Compound form expected, but found " +
					paramString;

				throw new RuntimeException(errorString);
			}
			
			// If we're good, consume the do
			params = params.subList(1, params.size());

			return ShortForm (s, params);
		}
		else 
		{
			String errorString = firstString + 
				" is an unknown keyword for loop.";
		   
			throw new RuntimeException(errorString);	
		}
    }

	private Object ForIn (Object ctrlVar, List params, Environment s) 
		throws Exception
	{
		// Verify that we actually have a "in" and "do", and
		// that the body of our expression is a list
		if (!params.get(1).toString().equals("do"))
		{
			String error = params.get(3).toString() + " is an unknown " +
				"keyword for loop.";

			throw new RuntimeException(error);
		}

		// Evaluate the control list - iterably!
        Iterable ctrlList = (Iterable) s.evaluate(params.get(0));

		// Create a local environment for the looping
		Environment block = s.copyForScope(new LinkedList(), s);

		// Get our param list
		List blockParams = params.subList(1, params.size());

		// Loop through all the expressions in the body until
		// we're done with the control variable
		for (Object item : ctrlList)
		{
			block.setVariable (ctrlVar.toString(), item);

			for (Object exp : blockParams)
			{
				block.evaluate(exp);
			}
		}

		return null;

	}

	private Object ForFrom (Object ctrlVar, List params, Environment s) 
		throws Exception
	{
		// Verify that we actually have a "to" and "do", and
		// that the body of our expression is a list
		if (!params.get(1).toString().equals("to"))
		{
			String error = params.get(1).toString() + " is an unknown " +
				"keyword for loop.";

			throw new RuntimeException(error);
		}
		if (!params.get(3).toString().equals("do"))
		{
			String error = params.get(3).toString() + " is an unknown " +
				"keyword for loop.";

			throw new RuntimeException(error);
		}

		// Now we should have two integers for the control 
		// variable
		Integer start = Integer.valueOf(params.get(0).toString ());
		Integer end	  = Integer.valueOf(params.get(2).toString ());

		// Create a local environment for the looping
		Environment block = s.copyForScope(new LinkedList(), s);

		// Add our control variable to the local scope
		block.setVariable(ctrlVar.toString(), start);

		// Get our param list
		List blockParams = params.subList(4, params.size());

		// Loop through all the expressions in the body until
		// we're done with the control variable
		while (block.resolveAsInteger(ctrlVar) <= end)
		{
			for (Object exp : blockParams)
			{
				block.evaluate(exp);
			}

			// Increment the control var
			Long current = block.resolveAsInteger(ctrlVar) + 1;
			block.setVariable(ctrlVar.toString(), current);
		}

		return null;
	}

	private Object ShortForm (Environment s, List params) throws Exception
	{
		// Short-form loop - simply repeates forever
		try 
		{
			while (true)
			{
				for (Object exp : params)
				{
				s.evaluate(exp);	
				}
			}
		}
		catch (Exception e)
		{

		}

		return null;
	}	

    @Override
    public Object getValue() {
        return "loop";
    }

}
