package me.alexng.gns.lexer;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public abstract class Token {

	private int startIndex, endIndex;

	protected Token(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public Value execute(Scope scope) throws RuntimeException {
		throw new RuntimeException(getStartIndex(), "This token (" + getClass().getSimpleName() + ") can not be run");
	}

	public boolean matches(Token token) {
		return false;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	@Override
	public abstract String toString();
}
