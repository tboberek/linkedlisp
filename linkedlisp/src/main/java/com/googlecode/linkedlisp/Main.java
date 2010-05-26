package com.googlecode.linkedlisp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import com.googlecode.linkedlisp.parser.*;

public class Main {
    public static void main(String[] args) throws IOException, RecognitionException {
        ANTLRFileStream in = new ANTLRFileStream(args[0]);
        LinkedLispLexer lexer = new LinkedLispLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LinkedLispParser parser = new LinkedLispParser(tokens);
        parser.eval();
    }
}
