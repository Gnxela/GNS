package me.alexng.gns.scipts;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintTest {

	@Test
	public void testPrint() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.print(\"Hello world!\")";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Options.Builder optionsBuilder = new Options.Builder();
		optionsBuilder.setStdout(outputStream);
		Environment environment = new Environment(optionsBuilder.build());
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("Hello world!", outputStream.toString());
	}
}
