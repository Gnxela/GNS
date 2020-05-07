package me.alexng.gns;

import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Script;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.util.ObjectToStringCrawler;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO: Remove most of the util classes.

		Environment scriptEnvironment = new Environment(new Options.Builder().setUsingSys(true).build());
		File scriptFile = new File("scripts/testScript7.gns");
		long start = System.nanoTime();
		scriptEnvironment.loadScript(new Script(scriptFile));
		long endParse = System.nanoTime();
		scriptEnvironment.runScripts();
		long endTime = System.nanoTime();
		System.out.println("Parse time: " + (endParse - start) / 1000000 + "ms");
		System.out.println("Run time: " + (endTime - endParse) / 1000000 + "ms");
		Scope scope = (Scope) scriptEnvironment.getGlobalScope().getValue(new IdentifierToken("o3", FileIndex.INTERNAL_INDEX)).getJavaValue();
		ObjectToStringCrawler crawler = new ObjectToStringCrawler();
		scope.crawl(crawler);
		System.out.println(crawler.destroy());
	}
}
