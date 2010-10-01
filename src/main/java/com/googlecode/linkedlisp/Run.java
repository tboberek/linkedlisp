/**
 * 
 */
package com.googlecode.linkedlisp;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.linkedlisp.functions.Defun;
import com.googlecode.linkedlisp.functions.Include;
import com.googlecode.linkedlisp.functions.Progn;
import com.googlecode.linkedlisp.functions.semantic.MakeModel;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Pawel
 *
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] argsUnused) throws Exception {
		ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(Compile.FILE_NAME));
		
		String[] args = (String[])  objIn.readObject();
		Object program = objIn.readObject();
		
		objIn.close();
		
		Environment state = new Environment();
        MakeModel modelMaker = new MakeModel();

        state.setModel((Model)modelMaker.execute(state, new ListExpression()));
        state.setPrefix("java", "java://");

        state.getVariables().put("defun", new Defun());
        state.getVariables().put("progn", new Progn());
        Main.run(state, Main.class.getResourceAsStream("/init.lisp"));
                
        String[] justArgs = new String[args.length-1];
        System.arraycopy(args, 1, justArgs, 0, justArgs.length);
        List arguments = Main.parseArgs(justArgs);
        state.setVariable("args", arguments);

        Include.initializePath(args[0], state);
        Object result = state.evaluate(program);
        if (result instanceof Function) {
            List toEval = new ArrayList();
            toEval.add(0,result);
            toEval.addAll(1, arguments);
            result = state.evaluate(toEval);
        }
        if (result != null) System.out.println(result.toString());
	}

}
