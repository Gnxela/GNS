package me.alexng.gns.util;

import me.alexng.gns.env.value.Value;

public class ReturningMockToken extends ExecutableMockToken {

	public ReturningMockToken(Value value) {
		super(new ReturningExecutor(value));
	}
}
