package com.focusit.groovy.limiter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;

import groovy.lang.GroovyShell;
import groovy.transform.ConditionalInterrupt;

public class ScriptRunner {

	public static void main(String args[]) throws CompilationFailedException, IOException {
		System.out.println("Test");
		
		ClosureExpression expr = new ClosureExpression(Parameter.EMPTY_ARRAY, GeneralUtils.returnS(GeneralUtils.callX(new ClassNode(MemoryCheck.class), "check")));
		Map<String, Object> map = new HashMap<>();
		map.put("value", expr);		
		CompilerConfiguration conf = new CompilerConfiguration();
		conf.addCompilationCustomizers(new ASTTransformationCustomizer(map, ConditionalInterrupt.class));
		
		GroovyShell shell = new GroovyShell(conf);
		MemoryCheck.saveThreadMemoryUsage();
		System.out.println("Thread memory allocated: "+MemoryCheck.getThreadMemoryUsage(Thread.currentThread().getId()));
		
		shell.evaluate(new File("test01.groovy"));
		MemoryCheck.saveThreadMemoryUsage();
		System.out.println("Thread memory allocated: "+MemoryCheck.getThreadMemoryUsage(Thread.currentThread().getId()));

		System.out.println("Done");
	}
}
