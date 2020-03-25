package me.alexng.gns.bridge;

import me.alexng.gns.env.Environment;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.StringValue;

public class MockBridge {

	@Expose
	public NumberValue variable;

	@Expose
	public StringValue function(Environment environment, NumberValue numberValue) {
		return null;
	}

}
