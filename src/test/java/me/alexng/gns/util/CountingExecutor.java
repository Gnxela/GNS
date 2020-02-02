package me.alexng.gns.util;

import me.alexng.gns.env.value.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountingExecutor extends ExecutableMockToken.Executor {

	private int count = 0;

	@Override
	public Value execute() {
		count++;
		return Value.NULL;
	}

	public void assertCount(int expectedCount) {
		assertEquals(expectedCount, count);
	}
}
