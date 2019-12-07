package me.alexng.gns;

import me.alexng.gns.env.Script;

public class Main {

	public static void main(String[] args) throws Exception {
		Script script = new Script(String.join("\n", new String[]{
				"i = 0",
				"func test(i, j, k) {",
				"	if (i == 0) {",
				"		j = 1",
				"		i = 4",
				"	}",
				"	k = 2",
				"}",
				"test(10, 1, 2)",
				"test(1, 2, 3)",
				"m = (1 + 1)"
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
