package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;

public class ValueToken extends Token {

	private Value value;

	public ValueToken(Value value, FileIndex fileIndex) {
		super(fileIndex);
		this.value = value;
	}

	@Override
	public Value execute(Scope scope) {
		return value;
	}

	@Override
	public String toString() {
		return "<Value " + value.toString() + " >";
	}
}
