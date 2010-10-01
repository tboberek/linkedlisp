package com.googlecode.linkedlisp.functions;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import com.googlecode.linkedlisp.Environment;
import com.googlecode.linkedlisp.Function;
import com.googlecode.linkedlisp.parser.LinkedLispLexer;
import com.googlecode.linkedlisp.parser.LinkedLispParser;

@SuppressWarnings("unchecked")
public class Include extends Function {

    public static String PATH_VARIABLE = "include.path";
    
    public static void initializePath(String script, Environment e) throws Exception {
        List includePath = new ArrayList();
        File scriptFile = new File(script);
        includePath.add(scriptFile.getParent());
        e.setVariable(PATH_VARIABLE, includePath);
    }
    
    @Override
    public Object execute(Environment s, List params) throws Exception {
        String name = params.get(0).toString();
        List<String> includePath = (List<String>) s.getVariable(PATH_VARIABLE);
        if (includePath == null) return null;

        for (String path : includePath) {
            File dirPath = new File(path);
            File scriptPath = new File (dirPath,name);
            if (!scriptPath.exists()) {
                scriptPath = new File (dirPath,name+".lisp");
            }
            if (scriptPath.exists()) {
                ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(scriptPath));
                LinkedLispLexer lexer = new LinkedLispLexer(in);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                LinkedLispParser parser = new LinkedLispParser(tokens);
                
                Object program = parser.eval();

                return s.evaluate(program);
            }
        }
        throw new RuntimeException("Cannot find script "+name);
    }
}
