package me.alexng.gns.util;

import me.alexng.gns.env.value.Value;

public class ReturningExecutor implements ExecutableMockToken.Executor {

	private Value value;

	public ReturningExecutor(Value value) {
		this.value = value;
	}

	@Override
	public Value execute() {
		return value;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
