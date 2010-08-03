package com.googlecode.linkedlisp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest
{

	static class TestInfo {
		File filename;
		Exception exception;
		
		TestInfo(File fileName, Exception exception) {
			this.filename = fileName;
			this.exception = exception;
		}
		
		@Override
		public String toString() {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			exception.printStackTrace(new PrintWriter(out, true));
			return filename + ": " + out.toString();
		}
	}
	
	@Test
	public void runSamples() throws Exception {
		// simply try running all the *.lisp in /tests
	
		List<TestInfo> failed = new LinkedList<TestInfo>();
		
		for(File f : new File("./tests").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".lisp")
					&& !pathname.getName().endsWith("LoopDoTest.lisp")
					&& !pathname.getName().endsWith("LoopTest.lisp");
			}
		})) {
			System.err.println("running " + f);
			
			try {
				Main.main(new String[] {f.getAbsolutePath()});
			} catch (Exception e) {
				failed.add(new TestInfo(f, e));
			}
		}
		
		if(failed.size() > 0)
			throw new RuntimeException(failed.size() + " tests failed: " + failed);
		
	}
}
