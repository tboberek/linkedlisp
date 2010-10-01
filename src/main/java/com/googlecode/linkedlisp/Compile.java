/**
 * 
 */
package com.googlecode.linkedlisp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import com.googlecode.linkedlisp.parser.LinkedLispLexer;
import com.googlecode.linkedlisp.parser.LinkedLispParser;

/**
 * @author Pawel
 *
 */
public class Compile {

	static final String FILE_NAME = "test.binlisp";
	
	public static void main(String[] args) throws Exception {
		// Would prefer to serialize the state as well, but jena stuff wasn't designed for that.
		// Have to settle on serializing the AST
        
        ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(args[0]));
        LinkedLispLexer lexer = new LinkedLispLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LinkedLispParser parser = new LinkedLispParser(tokens);
        
        Object program = parser.eval();

        if(parser.getNumberOfSyntaxErrors() > 0)
        	throw new RuntimeException("found " + parser.getNumberOfSyntaxErrors() + " syntax error(s)");
        
        ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        objOut.writeObject(args);
        objOut.writeObject(program);
        objOut.close();
        
        System.out.println("program successfully serialized to " + FILE_NAME);

	}
}
