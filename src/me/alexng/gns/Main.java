package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script(String.join("\n", new String[]{
				"i = 5",
				"func test(j) {",
				"	if (j == 5) {",
				"		i = 2",
				"	}",
				"}",
				"func foo(i, j) {",
				"	test(i)",
				"	test(j)",
				"}",
				"foo(i, 0)",
		}));
		try {
			script.parse();
			script.run();
		} catch (GNSException e) {
			e.printStackTrace();
		}
	}
}
