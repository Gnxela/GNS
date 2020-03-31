package me.alexng.gns.bridge;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.value.BooleanValue;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.StringValue;

public class MockBridge {

	public static Environment lastEnvironment;
	public static NumberValue lastNumberValue;
	public static NumberValue lastVariableValue;

	@Expose
	public NumberValue variable;
	public int constructorCalled = -1;

	@Expose
	public MockBridge() {
		this.constructorCalled = 1;
	}

	@Expose
	public MockBridge(NumberValue numberValue) {
		this.constructorCalled = 2;
	}

	@Expose
	public MockBridge(NumberValue numberValue, BooleanValue stringValue) {
		this.constructorCalled = 3;
	}

	@Expose
	public StringValue function(Environment environment, NumberValue numberValue) throws RuntimeException {
		lastEnvironment = environment;
		lastNumberValue = numberValue;
		lastVariableValue = variable;
		// TODO: It's not exactly clear why we must pass a calling scope here.
		return new StringValue(environment.incrementObjectId(), "Hello world!", environment.getGlobalScope());
	}
}