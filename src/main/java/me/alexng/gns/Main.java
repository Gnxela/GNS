package me.alexng.gns;

import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO: Remove most of the util classes.

		Environment scriptEnvironment = new Environment(new Options.Builder().setUsingSys(false).build());
		File scriptFile = new File("scripts/testScript6.gns");
		long start = System.nanoTime();
		scriptEnvironment.loadScript(new Script(scriptFile));
		long endParse = System.nanoTime();
		scriptEnvironment.runScripts();
		long endTime = System.nanoTime();
		System.out.println("Parse time: " + (endParse - start) / 1000000 + "ms");
		System.out.println("Run time: " + (endTime - endParse) / 1000000 + "ms");
		System.out.println(scriptEnvironment.getGlobalScope());
	}
}
