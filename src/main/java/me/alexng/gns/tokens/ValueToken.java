package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.Value;

public class ValueToken extends Token {

	public static final ValueToken NULL = new ValueToken(Value.NULL, FileIndex.INTERNAL_INDEX);

	private Value value;

	public ValueToken(Value value, FileIndex fileIndex) {
		super(fileIndex);
		this.value = value;
	}

	@Override
	public Value execute(Scope scope) {
		return value;
	}

	public Value getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "<" + value.getClass().getSimpleName() + " " + value.toString() + " >";
	}
}
