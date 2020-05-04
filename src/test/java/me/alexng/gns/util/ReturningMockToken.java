package me.alexng.gns.util;

import me.alexng.gns.tokens.value.Value;

public class ReturningMockToken extends ExecutableMockToken {

	public ReturningMockToken(Value value) {
		super(new ReturningExecutor(value));
	}
}
