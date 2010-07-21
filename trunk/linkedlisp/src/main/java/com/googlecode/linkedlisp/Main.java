package com.googlecode.linkedlisp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import com.googlecode.linkedlisp.functions.Defun;
import com.googlecode.linkedlisp.functions.semantic.MakeModel;
import com.googlecode.linkedlisp.functions.Progn;
import com.googlecode.linkedlisp.parser.LinkedLispLexer;
import com.googlecode.linkedlisp.parser.LinkedLispParser;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Main {
        
    public static void main(String[] args) throws Exception {
        State state = new State();
        MakeModel modelMaker = new MakeModel();

        state.setModel((Model)modelMaker.execute(state, new ListExpression()));
        state.setPrefix("java", "java://");

        state.getVariables().put("defun", new Defun());
        state.getVariables().put("progn", new Progn());
        run(state, Main.class.getResourceAsStream("/init.lisp"));
                
        String[] justArgs = new String[args.length-1];
        System.arraycopy(args, 1, justArgs, 0, justArgs.length);
        ListExpression arguments = parseArgs(justArgs);
        state.getGlobalVariables().put("arguments", arguments.getValue());

        
        Object result = run(state, new FileInputStream(args[0]));
        if (result != null) System.out.println(result.toString());
    }

    private static ListExpression parseArgs(String[] args) throws RecognitionException {
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

    private static Object run(State state, InputStream is) throws Exception {
        ANTLRInputStream in = new ANTLRInputStream(is);
        LinkedLispLexer lexer = new LinkedLispLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LinkedLispParser parser = new LinkedLispParser(tokens);
        
        Expression program = parser.eval();

        if(parser.getNumberOfSyntaxErrors() > 0)
        	throw new RuntimeException("found " + parser.getNumberOfSyntaxErrors() + " syntax error(s)");
        
        try {
            Object result = program.evaluate(state);
            return result;
        } catch (NoReturnException e) {
            return null;
        }
    }
}
