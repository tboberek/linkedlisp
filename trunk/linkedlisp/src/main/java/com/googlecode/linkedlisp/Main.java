package com.googlecode.linkedlisp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import com.googlecode.linkedlisp.functions.Defun;
import com.googlecode.linkedlisp.functions.Progn;
import com.googlecode.linkedlisp.parser.*;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Main {
        
    public static void main(String[] args) throws Exception {
        State state = new State();

        Map<String,String> defaultMappings 
            = ModelFactory.getDefaultModelPrefixes().getNsPrefixMap();
        for (String prefix : defaultMappings.keySet()) {
            String p = prefix.split(":")[0]; // We strip off the ':'s.
            state.getPrefixes().put(p, defaultMappings.get(prefix));
        }
        
        state.getPrefixes().put("xsd", "http://www.w3.org/2001/XMLSchema#");
        state.getPrefixes().put("prop", "prop://");
        state.getPrefixes().put("call", "call://");
        state.getPrefixes().put("java", "java://");

        state.getGlobalVariables().put("defun", new Defun());
        state.getGlobalVariables().put("progn", new Progn());
        run(state, Main.class.getResourceAsStream("/init.lisp"));
                
        String[] justArgs = new String[args.length-1];
        System.arraycopy(args, 1, justArgs, 0, justArgs.length);
        ListExpression arguments = parseArgs(justArgs);
        state.getGlobalVariables().put("arguments", arguments.evaluate(state));

        
        Object result = run(state, new FileInputStream(args[0]));
        if (result != null) System.out.println(result.toString());
    }

    private static ListExpression parseArgs(String[] args) throws RecognitionException {
        StringBuilder sb = new StringBuilder();
        sb.append("'(");
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
        try {
            Object result = program.evaluate(state);
            return result;
        } catch (NoReturnException e) {
            return null;
        }
    }
}
