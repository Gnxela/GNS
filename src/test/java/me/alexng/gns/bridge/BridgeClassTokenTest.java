package me.alexng.gns.bridge;

import me.alexng.gns.Options;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.BooleanValue;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BridgeClassTokenTest {

	@Test
	public void createInstanceTest() throws ParsingException, RuntimeException {
		Environment environment = new Environment(Options.createDefault());
		BridgeClassToken<MockBridge> bridgeBridgeClassToken = BridgeMapper.mapBridge(MockBridge.class);
		ObjectValue bridge = bridgeBridgeClassToken.createInstance(null, new Value[]{}, environment.getGlobalScope());
		Scope objectScope = bridge.getObjectScope();
		// TODO: Implement
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
