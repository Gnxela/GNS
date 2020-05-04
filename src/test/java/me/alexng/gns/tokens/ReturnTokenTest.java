package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.ReturnedValue;
import me.alexng.gns.tokens.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReturnTokenTest {

	@Test
	public void testExecute() throws RuntimeException {
		ReturnToken returnToken = new ReturnToken(new ValueToken(new NumberValue(13), FileIndex.NULL_INDEX), FileIndex.NULL_INDEX);
		Value receivedValue = returnToken.execute(null);
		assertTrue(receivedValue instanceof ReturnedValue);
		ReturnedValue returnedValue = (ReturnedValue) receivedValue;
		assertEquals(13, returnedValue.getJavaValue().getJavaValue());
	}
}
