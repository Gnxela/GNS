package me.alexng.gns;

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
	public String toString() {
		return null;
	}
}
