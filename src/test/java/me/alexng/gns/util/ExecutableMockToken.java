package me.alexng.gns.util;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.Value;

public class ExecutableMockToken extends MockToken {

	private Executor executor;

	public ExecutableMockToken(Executor executor) {
		this.executor = executor;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return executor.execute();
	}

	public abstract static class Executor {
		public abstract Value execute();
	}
}
