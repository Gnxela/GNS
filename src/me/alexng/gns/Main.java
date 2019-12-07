package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script(String.join("\n", new String[]{
				"i = 0",
				"func test(i) {",
				"	test(i + 1)",
				"	if (i == 5) {",
				"		i = 10",
				"	}",
				"}",
				"test(0)",
		}));
		try {
			script.parse();
			script.run();
		} catch (GNSException e) {
			e.printStackTrace();
			e.printErrorSource(script.getSource());
		}
	}
}
