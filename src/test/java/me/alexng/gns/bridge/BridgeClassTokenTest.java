package me.alexng.gns.bridge;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.BooleanValue;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.env.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BridgeClassTokenTest {

	@Test
	public void createInstanceTest() throws ParsingException, RuntimeException {
		Environment environment = new Environment(Options.createDefault());
		assertEquals(1, environment.getCurrentObjectId());
		BridgeClassToken<MockBridge> bridgeBridgeClassToken = new BridgeClassToken<>(MockBridge.class);
		ObjectValue bridge = bridgeBridgeClassToken.createInstance(null, new Value[]{}, environment.getGlobalScope());
		assertEquals(2, environment.getCurrentObjectId());
		Scope objectScope = bridge.getObjectScope();
		// TODO: We should test that they are properly linked
		assertTrue(objectScope.variableProvider instanceof BridgeVariableProvider);
		assertTrue(objectScope.functionProvider instanceof BridgeFunctionProvider);
	}

	@Test
	public void createBridgeInstanceTest() throws ParsingException, RuntimeException {
		BridgeClassToken<MockBridge> bridgeClassToken = new BridgeClassToken<>(MockBridge.class);
		MockBridge mockBridge = bridgeClassToken.createBridgeInstance(new Value[]{});
		assertEquals(mockBridge.constructorCalled, 1);
	}

	@Test
	public void createBridgeInstanceTest_secondConstructor() throws ParsingException, RuntimeException {
		BridgeClassToken<MockBridge> bridgeClassToken = new BridgeClassToken<>(MockBridge.class);
		MockBridge mockBridge = bridgeClassToken.createBridgeInstance(new Value[]{new NumberValue(1)});
		assertEquals(mockBridge.constructorCalled, 2);
	}

	@Test
	public void createBridgeInstanceTest_thirdConstructor() throws ParsingException, RuntimeException {
		BridgeClassToken<MockBridge> bridgeClassToken = new BridgeClassToken<>(MockBridge.class);
		MockBridge mockBridge = bridgeClassToken.createBridgeInstance(new Value[]{new NumberValue(1), BooleanValue.FALSE});
		assertEquals(mockBridge.constructorCalled, 3);
	}
}
