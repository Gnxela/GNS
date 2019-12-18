package me.alexng.gns;

import me.alexng.gns.env.Script;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		File scriptFile = new File("scripts/testScript.gns");
		Script script = new Script(scriptFile);
		try {
			script.parse();
			script.run();
		} catch (GNSException e) {
			e.printStackTrace();
			e.printErrorSource(script.getSource());
		}
	}
}
