package me.alexng.gns.bridge;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Script;
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
		BridgeClassToken bridgeClassToken = BridgeMapper.mapBridge(MockBridge.class);
		assertNotNull(bridgeClassToken.variables.get(variableName));
		Field variable = bridgeClassToken.variables.get(variableName);
		assertEquals(variableName, variable.getName());
		assertTrue(variable.isAnnotationPresent(Expose.class));
		assertEquals(1, bridgeClassToken.functions.size());
		Method method = bridgeClassToken.functions.get(0);
		assertEquals(functionName, method.getName());
	}

	/*@Test
	public void testMapBridge_privateField() throws NoSuchFieldException, RuntimeException, ParsingException{
		final String source = "bridge = new MockBridge()\nbridge.variable = 1\nbridge.function(2)";
		final String variableName = "variable";
		//MockBridge.class.getField(variableName).setAccessible(false);
		Environment environment = new Environment(new Options.Builder().build());
		environment.addBridge(MockBridge.class);
		environment.loadScript(new Script(source));
		environment.runScripts();
	}*/

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
}
