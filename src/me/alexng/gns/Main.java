package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script("i = 0\nif (i == (0 = 2)) {\n\ti = 1\n}\n");
		script.parse();
		script.run();
	}
}
