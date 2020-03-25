package me.alexng.gns.scipts;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SysTest {

	private Environment environment;
	private ByteArrayOutputStream outputStream;

	@BeforeEach
	public void setup() {
		outputStream = new ByteArrayOutputStream();
		Options.Builder optionsBuilder = new Options.Builder();
		optionsBuilder.setStdout(outputStream);
		optionsBuilder.setUsingSys(true);
		environment = new Environment(optionsBuilder.build());
	}

	@Test
	public void testPrint_Object() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.print(sys)";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("Sys#1", outputStream.toString());
	}

	@Test
	public void testPrint_Null() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.print(null)";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("null", outputStream.toString());
	}

	@Test
	public void testPrint_Boolean() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.print(true)";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("true", outputStream.toString());
	}

	@Test
	public void testPrint_Number() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.print(2.3)";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("2.3", outputStream.toString());
	}

	@Test
	public void testPrint_String() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.print(\"Hello world!\")";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("Hello world!", outputStream.toString());
	}

	@Test
	public void testPrintln() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.println(true)";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("true\n", outputStream.toString());
	}

	@Test
	public void testPrint_multiple_prints() throws RuntimeException, ParsingException {
		final String source = "sys = new Sys()\nsys.println(true)\nsys.print(true)\nsys.print(false)";
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals("true\ntruefalse", outputStream.toString());
	}
}
