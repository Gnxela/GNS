package me.alexng.gns;

import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		Environment scriptEnvironment = new Environment(new Options());
		scriptEnvironment.setup();
		File scriptFile = new File("scripts/testScript.gns");
		long start = System.nanoTime();
		scriptEnvironment.loadScript(new Script(scriptFile));
		long endParse = System.nanoTime();
		scriptEnvironment.runScripts();
		long endTime = System.nanoTime();
		System.out.println("Parse time: " + (endParse - start) / 1000000 + "ms");
		System.out.println("Run time: " + (endTime - endParse) / 1000000 + "ms");
	}
}
