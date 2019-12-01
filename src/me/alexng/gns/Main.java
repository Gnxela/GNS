package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script(String.join("\n", new String[]{
				"i = 0",
				"if ((i == 0) == true) {",
				"	if (i == 1) {",
				"		j = 1",
				"	}",
				"	k = 2",
				"}",
				"m = 1"
		}));
		script.parse();
		script.run();
	}
}
