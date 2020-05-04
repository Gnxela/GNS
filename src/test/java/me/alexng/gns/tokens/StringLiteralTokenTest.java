package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Options;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.StringValue;
import me.alexng.gns.env.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringLiteralTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		String str = "Hello world!";
		StringLiteralToken stringLiteralToken = new StringLiteralToken(str, FileIndex.NULL_INDEX);
		Value value = stringLiteralToken.execute(Scope.createGlobalScope(new Environment(new Options.Builder().build())));
		assertTrue(value instanceof StringValue);
		assertEquals(str, value.getJavaValue());
	}
}
