package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {

		// TODO: Create BinaryOperationToken etc. to generalise operators.

		Script script = new Script(String.join("\n", new String[]{
				"i = 0",
				"if (i == 1) {",
				"	if (i == 0) {",
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
