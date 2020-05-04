package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.tokens.value.NumberValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueTokenTest {

	@Test
	public void testExecute() {
		ValueToken valueToken = new ValueToken(new NumberValue(13), FileIndex.NULL_INDEX);
		assertEquals(13, valueToken.execute(null).getJavaValue());
	}
}
