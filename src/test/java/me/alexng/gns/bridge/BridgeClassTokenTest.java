package me.alexng.gns.bridge;

import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.value.BooleanValue;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BridgeClassTokenTest {

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
