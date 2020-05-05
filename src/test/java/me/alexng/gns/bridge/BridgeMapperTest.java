package me.alexng.gns.bridge;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.StringValue;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeMapperTest {

	@Test
	public void testMapBridge() throws ParsingException {
		// TODO: We need to test field accessibility / visibility.
		final String variableName = "variable";
		final String functionName = "function";
		BridgeTemplateToken<MockBridge> bridgeClassToken = BridgeMapper.mapBridge(MockBridge.class);
		assertNotNull(bridgeClassToken.variables.get(variableName));
		Field variable = bridgeClassToken.variables.get(variableName);
		assertEquals(variableName, variable.getName());
		assertTrue(variable.isAnnotationPresent(Expose.class));
		assertEquals(1, bridgeClassToken.functions.size());
		Method method = bridgeClassToken.functions.get(0);
		assertEquals(functionName, method.getName());
	}

	@Test
	public void testMapBridge_privateMembers() throws NoSuchFieldException, RuntimeException, ParsingException {
		final String variableName = "variable";
		BridgeTemplateToken<MockBridgePrivateMembers> bridgeClassToken = BridgeMapper.mapBridge(MockBridgePrivateMembers.class);
		Field variable = bridgeClassToken.variables.get(variableName);
		assertNull(variable);
		assertEquals(0, bridgeClassToken.functions.size());
	}

	@Test
	public void testMapBridge_functionParameters() throws RuntimeException, ParsingException {
		final String source = "bridge = new MockBridge()\nbridge.function(7)";
		Environment environment = new Environment(new Options.Builder().build());
		environment.addBridge(MockBridge.class);
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertEquals(7, MockBridge.lastNumberValue.getJavaValue());
		assertEquals(environment, MockBridge.lastEnvironment);
	}

	@Test
	public void testMapBridge_setVariable() throws RuntimeException, ParsingException {
		final String source = "bridge = new MockBridge()\nbridge.variable = 5\nbridge.function(7)";
		Environment environment = new Environment(new Options.Builder().build());
		environment.addBridge(MockBridge.class);
		environment.loadScript(new Script(source));
		environment.runScripts();
		assertNotNull(MockBridge.lastVariableValue.getJavaValue());
		assertEquals(5, MockBridge.lastVariableValue.getJavaValue());
	}

	@Test
	public void testMapBridge_nonUniqueName() throws ParsingException {
		assertThrows(ParsingException.class, () -> BridgeMapper.mapBridge(MockBridgeNonUnique.class));
		assertThrows(ParsingException.class, () -> BridgeMapper.mapBridge(MockBridgeNonUnique2.class));
	}

	public static class MockBridgePrivateMembers {
		@Expose
		private NumberValue variable;

		@Expose
		private void function(Environment environment, StringValue stringValue) {

		}
	}

	public static class MockBridgeNonUnique {
		@Expose
		public NumberValue uniqueName;
		@Expose
		public NumberValue nonUniqueName;

		@Expose
		public void nonUniqueName(Environment environment, StringValue stringValue) {

		}
	}

	public static class MockBridgeNonUnique2 {
		@Expose
		public NumberValue uniqueName;

		@Expose
		public void uniqueName(Environment environment, StringValue stringValue) {

		}

		@Expose
		public void uniqueName(Environment environment) {

		}
	}
}
