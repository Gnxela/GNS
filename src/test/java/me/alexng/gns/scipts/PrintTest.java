package me.alexng.gns.scipts;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;
import org.junit.jupiter.api.Test;

public class PrintTest {

	@Test
	public void testPrint() throws ParsingException {
		final String source = "";
		Environment environment = new Environment(new Options.Builder().build());
		environment.loadScript(new Script(source));

	}
}
