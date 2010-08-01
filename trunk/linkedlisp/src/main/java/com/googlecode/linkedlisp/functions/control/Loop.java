package com.googlecode.linkedlisp.functions.control;

import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.ListExpression;
import com.googlecode.linkedlisp.Environment;
import java.util.List;

public class Loop extends Function {

	private String blockName = null;

    @Override
	public Object execute(Environment s, List params) throws Exception 
   	{
		// Get the first string in our expression
		String firstParam = s.evaluate(params.get(0)).toString();

		// Determine what form of loop we have
		if (firstParam.equals("named"))
		{
			// If we find a block after we already have one,
			// then throw an exception
			if (blockName != null)
			{
				throw new Exception("named clause already found");
			}

			// Get the name for the block
			blockName = s.evaluate(params.get(1)).toString();

			// Get the remaining expressions after we clear
			// out our named clause
			List remaining = params.subList (2, params.size());

			// Execute with the remaining 
			return execute (s, remaining);
			}
		else if (firstParam.equals("for"))
		{
			// The next part of our clause is the control
			// variable
			Object controlVar = params.get(1);

			// After the control variable, we need to grab
			// the control subclause
			Object subclause = params.get(2);
			
			if (subclause.toString().equals("from"))
			{
			//	Integer intialValue = Integer.params.get(3).getValue
			}
			return null;
		}
		else
		{
			// Short form, only allowed if we haven't
			// found any other clause (including a named
			// clause)
			if (blockName != null)
			{
				throw new 
					Exception("found expression where LOOP keyword expected.");
			}	

			return ShortForm (s, params);
		}
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
