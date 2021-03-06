package me.alexng.gns.util;

import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountingExecutor implements ExecutableMockToken.Executor {

	private int count = 0;

	@Override
	public Value execute() {
		count++;
		return NullValue.INTERNAL;
	}

	public void assertCount(int expectedCount) {
		assertEquals(expectedCount, count);
	}
}
