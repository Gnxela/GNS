package me.alexng.gns;

import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		Environment scriptEnvironment = new Environment(new Options());
		scriptEnvironment.setup();
		File scriptFile = new File("scripts/testScript.gns");
		scriptEnvironment.loadScript(new Script(scriptFile));
		try {
			scriptEnvironment.runScripts();
		} catch (GNSException e) {
			e.printStackTrace();
		}
	}
}
