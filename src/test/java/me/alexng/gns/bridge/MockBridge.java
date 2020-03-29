package me.alexng.gns.bridge;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.StringValue;

public class MockBridge {

	public static Environment lastEnvironment;
	public static NumberValue lastNumberValue;
	public static NumberValue lastVariableValue;

	@Expose
	public NumberValue variable;

	@Expose
	public StringValue function(Environment environment, NumberValue numberValue) throws RuntimeException {
		lastEnvironment = environment;
		lastNumberValue = numberValue;
		lastVariableValue = variable;
		// TODO: It's not exactly clear why we must pass a calling scope here.
		return new StringValue(environment.incrementObjectId(), "Hello world!", environment.getGlobalScope());
	}
}