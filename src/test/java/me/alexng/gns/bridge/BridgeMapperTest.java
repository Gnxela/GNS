package me.alexng.gns.bridge;

import me.alexng.gns.ParsingException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeMapperTest {

	@Test
	public void testMapBridge() throws ParsingException {
		// TODO: We need to test field accessibility / visibility.
		final String variableName = "variable";
		BridgeClassToken bridgeClassToken = BridgeMapper.mapBridge(MockBridge.class);
		assertNotNull(bridgeClassToken.variables.get(variableName));
		Field variable = bridgeClassToken.variables.get(variableName);
		assertEquals(variableName, variable.getName());
		assertTrue(variable.isAnnotationPresent(Expose.class));
	}
}
