package me.alexng.gns.util;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.Token;

public class MockToken extends Token {

	public MockToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	public MockToken(String file, int start, int end) {
		this(new FileIndex(file, start, end));
	}

	public MockToken() {
		this(null);
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return super.execute(scope);
	}

	@Override
	public String toString() {
		return null;
	}
}
