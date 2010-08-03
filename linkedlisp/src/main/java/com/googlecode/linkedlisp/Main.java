package com.googlecode.linkedlisp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.googlecode.linkedlisp.functions.Defun;
import com.googlecode.linkedlisp.functions.Include;
import com.googlecode.linkedlisp.functions.Progn;
import com.googlecode.linkedlisp.functions.semantic.MakeModel;
import com.googlecode.linkedlisp.parser.LinkedLispLexer;
import com.googlecode.linkedlisp.parser.LinkedLispParser;
import com.hp.hpl.jena.rdf.model.Model;

public class Main {
        
    public static void main(String[] args) throws Exception {
        Environment state = new Environment();
        MakeModel modelMaker = new MakeModel();

        state.setModel((Model)modelMaker.execute(state, new ListExpression()));
        state.setPrefix("java", "java://");

        state.getVariables().put("defun", new Defun());
        state.getVariables().put("progn", new Progn());
        run(state, Main.class.getResourceAsStream("/init.lisp"));
                
        String[] justArgs = new String[args.length-1];
        System.arraycopy(args, 1, justArgs, 0, justArgs.length);
        List arguments = parseArgs(justArgs);
        state.setVariable("args", arguments);

        Include.initializePath(args[0], state);
        Object result = null;
		if (args.length > 1 && args[0].equals("--ast"))
		{
			result = outputAST(state, new FileInputStream(args[1]));
		}
		else
		{
			result = run(state, new FileInputStream(args[0]));
		}
	
		if (result instanceof Function) {
            List toEval = new ArrayList();
            toEval.add(0,result);
            toEval.addAll(1, arguments);
            result = state.evaluate(toEval);
        }
        if (result != null) System.out.println(result.toString());
    }

    static List parseArgs(String[] args) throws RecognitionException {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String s : args) {
            sb.append(" \"");
            sb.append(s);
            sb.append("\" ");
        }
        sb.append(")");
        ANTLRStringStream in = new ANTLRStringStream(sb.toString());
        LinkedLispLexer lexer = new LinkedLispLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LinkedLispParser parser = new LinkedLispParser(tokens);
        return parser.listExp();
    }

	static Object parseFile(InputStream is) throws Exception
	{
        ANTLRInputStream in = new ANTLRInputStream(is);
        LinkedLispLexer lexer = new LinkedLispLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LinkedLispParser parser = new LinkedLispParser(tokens);
        
        Object program = parser.eval();

        if(parser.getNumberOfSyntaxErrors() > 0)
        	throw new RuntimeException("found " + 
					parser.getNumberOfSyntaxErrors() + " syntax error(s)");

		return program;
	}

	static Object outputAST(Environment state, InputStream is) throws Exception
	{
		Object program = parseFile(is);
		return program;
	}

    static Object run(Environment state, InputStream is) throws Exception {
        
		Object program = parseFile(is);
       	return state.evaluate(program);
    }
}
