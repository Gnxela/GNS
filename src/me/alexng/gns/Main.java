package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script(String.join("\n", new String[]{
				"i = 0",
				"if (i = 1) {",
				"	if (i = 0) {",
				"		i = 1",
				"	}",
				"	i = 2",
				"}",
				"i = 1"
		}));
		script.parse();
		script.run();
	}
}
