package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script(String.join("\n", new String[]{
				"i = 0",
				"func test(i) {",
				"	if (i == 5) {",
				"		i = 10",
				"	}",
				"}",
				"func foo(i, j) {",
				"	test(i)",
				"	test(j)",
				"}",
				"foo(i, 0)",
		}));
		// TODO: This program should not fail. Need to figure out how I want to allow functions to call other functions on the same scope as them.
		try {
			script.parse();
			script.run();
		} catch (GNSException e) {
			e.printStackTrace();
		}
	}
}
