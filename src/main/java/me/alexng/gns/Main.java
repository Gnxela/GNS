package me.alexng.gns;

import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.value.ObjectValue;
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

		ObjectValue object = (ObjectValue) scriptEnvironment.getGlobalScope().getValue(new IdentifierToken("o3", FileIndex.INTERNAL_INDEX));
		long length = 100000;
		long total = 0;
		for (int i = 0; i < length; i++) {
			long s = System.nanoTime();
			object.getObjectScope().crawl(new ObjectToStringCrawler());
			total += System.nanoTime() - s;
		}
		System.out.println("Stringify: " + (total / length) / 1000000 + "ms");
		System.out.println("Stringify: " + (total / length) / 1000 + "us");
		System.out.println("Stringify: " + (total / length) + "ns");
	}
}
